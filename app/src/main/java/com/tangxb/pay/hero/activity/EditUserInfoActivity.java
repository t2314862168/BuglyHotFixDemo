package com.tangxb.pay.hero.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.KeyValueBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.RoleBean;
import com.tangxb.pay.hero.bean.UserBean;
import com.tangxb.pay.hero.controller.ChooseCityController;
import com.tangxb.pay.hero.controller.EditUserInfoController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.MDialogUtils;
import com.tangxb.pay.hero.util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/5/14 0014.
 */

public class EditUserInfoActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;

    private List<KeyValueBean> dataList = new ArrayList<>();
    private RecyclerAdapterWithHF mAdapter;
    ChooseCityController cityController;
    EditUserInfoController editUserInfoController;
    UserBean userBean;
    final String nameKey = "姓名：";
    final String mobileKey = "电话：";
    final String pwdKey = "密码：";
    final String cityKey = "城市：";
    final String addressKey = "地址：";
    final String roleKey = "角色：";
    final String statusKey = "状态：";
    int realPosition;
    long currentUserId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_edit_user_info;
    }

    private void showDataUI() {
        dataList.add(new KeyValueBean(nameKey, userBean.getRealName()));
        dataList.add(new KeyValueBean(mobileKey, userBean.getMobile()));
        dataList.add(new KeyValueBean(pwdKey, "点击修改"));
        dataList.add(new KeyValueBean(cityKey, userBean.getCity()));
        dataList.add(new KeyValueBean(addressKey, userBean.getAddress()));
        dataList.add(new KeyValueBean(roleKey, userBean.getRoleName()));
        dataList.add(new KeyValueBean(statusKey, userBean.getStatus() == 1 ? "正常" : "冻结"));
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        realPosition = intent.getIntExtra("realPosition", -1);
        userBean = intent.getParcelableExtra("userBean");
        if (userBean != null) {
            currentUserId = userBean.getId();
        }
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.personal_info);
        cityController = new ChooseCityController(this, new ChooseCityController.ChoosePositionListener() {
            @Override
            public void chooseArea(final int position, final String parentId, final String parentName) {
                addSubscription(editUserInfoController.updateUserCity(currentUserId, parentName), new Consumer<MBaseBean<String>>() {
                    @Override
                    public void accept(MBaseBean<String> baseBean) throws Exception {
                        ToastUtils.t(mApplication, baseBean.getMessage());
                        try {
                            userBean.setAddressId(Long.parseLong(parentId));
                            userBean.setCity(parentName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dataList.get(position).setValue(parentName);
                        mAdapter.notifyItemChangedHF(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.t(mApplication, throwable.getMessage());
                    }
                });

            }
        });
        editUserInfoController = new EditUserInfoController(this);
        showDataUI();

        CommonAdapter commonAdapter = new CommonAdapter<KeyValueBean>(mActivity, R.layout.item_edit_user_info, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, KeyValueBean item, int position) {
                viewHolder.setText(R.id.tv_key, item.getKey());
                viewHolder.setText(R.id.tv_value, item.getValue());
            }
        };
        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                handleItemClick(position);
            }
        });
    }


    /**
     * 获取角色列表
     */
    public void getRoleList(final int position) {
        addSubscription(editUserInfoController.getRoleList(), new Consumer<MBaseBean<List<RoleBean>>>() {
            @Override
            public void accept(MBaseBean<List<RoleBean>> baseBean) throws Exception {
                showRoleListDialog(baseBean.getData(), position);
            }
        });
    }

    /**
     * 显示角色列表对话框
     *
     * @param roleBeanList
     */
    public void showRoleListDialog(final List<RoleBean> roleBeanList, final int position) {
        if (roleBeanList == null || roleBeanList.size() == 0) return;
        String title = mResources.getString(R.string.choose_role_to_user);
        int size = roleBeanList.size();
        final long[] roleIds = new long[size];
        final String[] items = new String[size];
        for (int i = 0; i < size; i++) {
            roleIds[i] = roleBeanList.get(i).getId();
            items[i] = roleBeanList.get(i).getName();
        }
        MDialogUtils.showSingleChoiceDialog(mActivity, title, items, new MDialogUtils.OnCheckListener() {
            @Override
            public void onSure(int which, AlertDialog dialog) {
                dialog.dismiss();
                updateRoleToUser(position, currentUserId, roleIds[which], items[which]);
            }
        });
    }

    /**
     * 更新数据,显示到ui
     *
     * @param position
     * @param userId
     * @param roleId
     * @param roleName
     */
    public void updateRoleToUser(final int position, long userId, final long roleId, final String roleName) {
        addSubscription(editUserInfoController.updateRoleToUser(userId, roleId), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> baseBean) throws Exception {
                ToastUtils.t(mApplication, baseBean.getMessage());
                userBean.setRoleId(roleId);
                userBean.setRoleName(roleName);
                dataList.get(position).setValue(roleName);
                mAdapter.notifyItemChangedHF(position);
            }
        });
    }

    /**
     * 修改用户地址
     *
     * @param position
     */
    private void updateAddress(final int position) {
        MDialogUtils.showEditTextDialog(mActivity, "修改地址", TextUtils.isEmpty(userBean.getAddress()) ? "" : userBean.getAddress()
                , new MDialogUtils.OnSureTextListener() {
                    @Override
                    public void onSure(final String text, AlertDialog dialog) {
                        dialog.dismiss();
                        addSubscription(editUserInfoController.updateUserAddress(currentUserId, text), new Consumer<MBaseBean<String>>() {
                            @Override
                            public void accept(MBaseBean<String> baseBean) throws Exception {
                                ToastUtils.t(mApplication, baseBean.getMessage());
                                userBean.setAddress(text);
                                dataList.get(position).setValue(text);
                                mAdapter.notifyItemChangedHF(position);
                            }
                        });
                    }
                });
    }

    /**
     * 更改用户状态
     */
    private void updateUserStatus(final int position) {
        final String[] items = {mResources.getString(R.string.un_normal), mResources.getString(R.string.normal)};
        MDialogUtils.showSingleChoiceDialog(mActivity, "选择角色状态", items, new MDialogUtils.OnCheckListener() {
            @Override
            public void onSure(final int which, AlertDialog dialog) {
                dialog.dismiss();
                addSubscription(editUserInfoController.updateUserState(currentUserId, which), new Consumer<MBaseBean<String>>() {
                    @Override
                    public void accept(MBaseBean<String> baseBean) throws Exception {
                        ToastUtils.t(mApplication, baseBean.getMessage());
                        userBean.setStatus(which);
                        dataList.get(position).setValue(items[which]);
                        mAdapter.notifyItemChangedHF(position);
                    }
                });
            }
        });
    }

    /**
     * 更改城市
     *
     * @param position
     */
    private void updateCity(int position) {
        cityController.setPosition(position);
        cityController.chooseArea(null, null);
    }

    /**
     * 点击item
     *
     * @param position
     */
    private void handleItemClick(int position) {
        if (dataList.get(position).getKey().equals(nameKey)) {

        } else if (dataList.get(position).getKey().equals(mobileKey)) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userBean.getMobile()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (dataList.get(position).getKey().equals(pwdKey)) {
            Intent intent = getIntentWithPublicParams(EditUserPwdActivity.class);
            intent.putExtra("currentUserId", currentUserId);
            intent.putExtra("phone", userBean.getMobile());
            startActivity(intent);
        } else if (dataList.get(position).getKey().equals(cityKey)) {
            updateCity(position);
        } else if (dataList.get(position).getKey().equals(addressKey)) {
            updateAddress(position);
        } else if (dataList.get(position).getKey().equals(roleKey)) {
            getRoleList(position);
        } else if (dataList.get(position).getKey().equals(statusKey)) {
            updateUserStatus(position);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("realPosition", realPosition);
        intent.putExtra("userBean", userBean);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
