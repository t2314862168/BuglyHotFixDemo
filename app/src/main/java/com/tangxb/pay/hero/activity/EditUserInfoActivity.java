package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.KeyValueBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.RoleBean;
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
    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;

    private List<KeyValueBean> mData = new ArrayList<>();
    private RecyclerAdapterWithHF mAdapter;
    EditUserInfoController editUserInfoController;
    long currentUserId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_edit_user_info;
    }

    private void mockData() {
        KeyValueBean bean1 = new KeyValueBean();
        bean1.setKey("姓名：");
        bean1.setValue("tangxb");
        KeyValueBean bean2 = new KeyValueBean();
        bean2.setKey("电话：");
        bean2.setValue("1789828282");
        KeyValueBean bean3 = new KeyValueBean();
        bean3.setKey("密码：");
        bean3.setValue("点击修改");
        mData.add(bean1);
        mData.add(bean2);
        mData.add(bean3);
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        currentUserId = intent.getLongExtra("currentUserId", 0);
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.personal_info);
        editUserInfoController = new EditUserInfoController(this);
        mockData();

        CommonAdapter commonAdapter = new CommonAdapter<KeyValueBean>(mActivity, R.layout.item_edit_user_info, mData) {
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
        ptrClassicFrameLayout.setEnabled(false);
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
    public void getRoleList() {
        addSubscription(editUserInfoController.getRoleList(), new Consumer<MBaseBean<List<RoleBean>>>() {
            @Override
            public void accept(MBaseBean<List<RoleBean>> baseBean) throws Exception {
                showRoleListDialog(baseBean.getData());
            }
        });
    }

    /**
     * 显示角色列表对话框
     *
     * @param roleBeanList
     */
    public void showRoleListDialog(final List<RoleBean> roleBeanList) {
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
                updateRoleToUser(currentUserId, roleIds[which]);
            }
        });
    }

    public void updateRoleToUser(long userId, long roleId) {
        addSubscription(editUserInfoController.updateRoleToUser(userId, roleId), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> baseBean) throws Exception {
                ToastUtils.t(mApplication, baseBean.getMessage());
            }
        });
    }


    /**
     * 点击item
     *
     * @param position
     */
    private void handleItemClick(int position) {
        switch (position) {
            case 0:
                getRoleList();
                break;
            case 1:
                MDialogUtils.showEditTextDialog(mActivity, "Title", "Teext", new MDialogUtils.OnSureTextListener() {
                    @Override
                    public void onSure(String text, AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                break;
            case 2:
                MDialogUtils.showSingleChoiceDialog(mActivity, "Title", new String[]{"nomral", "ddd"}, new MDialogUtils.OnCheckListener() {
                    @Override
                    public void onSure(int which, AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }
}
