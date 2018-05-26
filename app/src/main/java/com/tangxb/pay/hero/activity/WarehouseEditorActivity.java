package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.UserBean;
import com.tangxb.pay.hero.bean.WarehouseBean;
import com.tangxb.pay.hero.controller.ChooseCityController;
import com.tangxb.pay.hero.controller.UserMangerFragmentController;
import com.tangxb.pay.hero.controller.WarehouseController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.ConstUtils;
import com.tangxb.pay.hero.util.MDialogUtils;
import com.tangxb.pay.hero.util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 仓库编辑界面<br>
 * Created by zll on 2018/5/26.
 */

public class WarehouseEditorActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_bottom)
    View mBottomLL;
    @BindView(R.id.btn_item)
    Button mItemBtn1;
    View mHeadLL;

    EditText et_goods_name;
    TextView tv_on_sale;
    TextView tv_city;
    EditText et_address;
    EditText et_jishu;

    List<UserBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    UserMangerFragmentController fragmentController;
    WarehouseController controller;
    ChooseCityController cityController;
    int status = 1;
    long id;
    String areaId;
    String areaName;
    WarehouseBean warehouseBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_warehouse_editor;
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        warehouseBean = intent.getParcelableExtra("warehouseBean");
        if (warehouseBean != null) {
            id = warehouseBean.getId();
        }
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText("仓库编辑");
        mItemBtn1.setText(R.string.save);
        // 输入法不改变布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        fragmentController = new UserMangerFragmentController(this);
        cityController = new ChooseCityController(this, new ChooseCityController.ChooseListener() {
            @Override
            public void chooseArea(String parentId, String parentName) {
                areaId = parentId;
                areaName = parentName;
                tv_city.setText(parentName);
            }
        });
        controller = new WarehouseController(this);
        TypedArray typedArray = mActivity.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        final Drawable divider = typedArray.getDrawable(0);
        typedArray.recycle();
        CommonAdapter commonAdapter = new CommonAdapter<UserBean>(mActivity, R.layout.item_warehouse_editor_person, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, UserBean item, final int position) {
                LinearLayout itemLL = viewHolder.getView(R.id.ll_item);
                itemLL.setDividerDrawable(divider);

                viewHolder.setText(R.id.tv_name, item.getRealName());
                viewHolder.setOnClickListener(R.id.iv_mobile, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleItemClick(position);
                    }
                });
            }
        };
        mHeadLL = View.inflate(mActivity, R.layout.layout_warehouse_editor_head, null);
        et_goods_name = findView(mHeadLL, R.id.et_goods_name);
        tv_on_sale = findView(mHeadLL, R.id.tv_on_sale);
        tv_city = findView(mHeadLL, R.id.tv_city);
        et_address = findView(mHeadLL, R.id.et_address);
        et_jishu = findView(mHeadLL, R.id.et_jishu);
        tv_on_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStatus();
            }
        });
        tv_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCity();
            }
        });

        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        mAdapter.addHeader(mHeadLL);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        showDataUi();
    }

    /**
     * 回显数据到界面上面
     */
    private void showDataUi() {
        if (warehouseBean == null) return;
        et_goods_name.setText(warehouseBean.getName());
        if (warehouseBean.getStatus() == 0) {
            tv_on_sale.setText(R.string.un_normal);
            tv_on_sale.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.border_red));
            tv_on_sale.setTextColor(ContextCompat.getColor(mActivity, R.color.color_red));
        } else if (warehouseBean.getStatus() == 1) {
            tv_on_sale.setText(R.string.normal);
            tv_on_sale.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.border_main));
            tv_on_sale.setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
        }
        tv_city.setText(warehouseBean.getCity());
        et_address.setText(warehouseBean.getAddress());
        et_jishu.setText(warehouseBean.getDistance() + "");

        addSubscription(controller.getStorageProxyer(id), new Consumer<MBaseBean<List<UserBean>>>() {
            @Override
            public void accept(MBaseBean<List<UserBean>> baseBean) throws Exception {
                if (baseBean.getData() != null) {
                    dataList.addAll(baseBean.getData());
                    mAdapter.notifyDataSetChangedHF();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println();
            }
        });
    }

    /**
     * 点击状态
     */
    public void clickStatus() {
        if (warehouseBean == null) return;
        int tempStatus = (status == 1 ? 0 : 1);
        addSubscription(controller.updateStorageStatus(id, tempStatus), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> baseBean) throws Exception {
                if (warehouseBean.getStatus() == 1) {
                    status = 0;
                    warehouseBean.setStatus(0);
                    tv_on_sale.setText(R.string.un_normal);
                    tv_on_sale.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.border_red));
                    tv_on_sale.setTextColor(ContextCompat.getColor(mActivity, R.color.color_red));
                } else {
                    status = 1;
                    warehouseBean.setStatus(1);
                    tv_on_sale.setText(R.string.normal);
                    tv_on_sale.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.border_main));
                    tv_on_sale.setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
                }
            }
        });
    }

    /**
     * 点击城市
     */
    public void clickCity() {
        cityController.chooseArea(null, null);
    }

    /**
     * 添加业务员
     *
     * @param view
     */
    @OnClick(R.id.btn_add_person)
    public void addPersonClick(View view) {
        addSubscription(fragmentController.getUserListByRoleId(1, 200, ConstUtils.RB_600.getId(), null, 1), new Consumer<MBaseBean<List<UserBean>>>() {
            @Override
            public void accept(MBaseBean<List<UserBean>> baseBean) throws Exception {
                if (baseBean.getData() != null && baseBean.getData().size() > 0) {
                    showChoosePersonDialog(baseBean.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.t(mApplication, throwable.getMessage());
            }
        });
    }

    /**
     * 添加业务员弹窗
     *
     * @param data
     */
    private void showChoosePersonDialog(final List<UserBean> data) {
        int size = data.size();
        final String[] items = new String[size];
        for (int i = 0; i < size; i++) {
            items[i] = data.get(i).getRealName() + "(" + data.get(i).getMobile() + ")";
        }
        MDialogUtils.showSingleChoiceDialog(mActivity, "选择业务员", items, new MDialogUtils.OnCheckListener() {
            @Override
            public void onSure(int which, AlertDialog dialog) {
                dialog.dismiss();
                int size1 = dataList.size();
                dataList.add(data.get(which));
                mAdapter.notifyItemInsertedHF(size1);
            }
        });
    }

    /**
     * 保存
     *
     * @param view
     */
    @OnClick(R.id.btn_item)
    public void itemClick(View view) {
        if (!isChecked()) return;
        String name = et_goods_name.getText().toString();
        String city = tv_city.getText().toString();
        String address = et_address.getText().toString();
        String jishu = et_jishu.getText().toString();
        StringBuilder sb = new StringBuilder();
        for (UserBean bean : dataList) {
            sb.append(bean.getId());
            sb.append(",");
        }
        if (sb.length() >= 2) {
            sb.deleteCharAt(sb.length() - 1);
        }
        String proxy_ids = sb.toString();
        showAlertDialog();
        addSubscription(controller.updateStorage(id, name, city, address, jishu, proxy_ids), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> baseBean) throws Exception {
                ToastUtils.t(mApplication, baseBean.getMessage());
                hideAlertDialog();
                finish();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.t(mApplication, throwable.getMessage());
                hideAlertDialog();
            }
        });
    }

    private boolean isChecked() {
        String name = et_goods_name.getText().toString();
        String city = tv_city.getText().toString();
        String address = et_address.getText().toString();
        String jishu = et_jishu.getText().toString();
        if (TextUtils.isEmpty(name.trim())) {
            ToastUtils.t(mApplication, "名字为空");
            return false;
        }
        if (TextUtils.isEmpty(city.trim())) {
            ToastUtils.t(mApplication, "城市为空");
            return false;
        }
        if (TextUtils.isEmpty(address.trim())) {
            ToastUtils.t(mApplication, "地址为空");
            return false;
        }
        if (TextUtils.isEmpty(jishu.trim())) {
            ToastUtils.t(mApplication, "基数为空");
            return false;
        }
        return true;
    }

    /**
     * 点击item
     */
    private void handleItemClick(int position) {
        dataList.remove(position - mAdapter.getHeadSize());
        mAdapter.notifyDataSetChangedHF();
    }
}
