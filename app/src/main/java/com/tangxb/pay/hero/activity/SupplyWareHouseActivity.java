package com.tangxb.pay.hero.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.WarehouseAllInOneBean;
import com.tangxb.pay.hero.controller.SupplyWareHouseController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.MDialogUtils;
import com.tangxb.pay.hero.util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 补仓界面<br>
 * Created by zll on 2018/5/26.
 */

public class SupplyWareHouseActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_bottom)
    View mBottomLL;
    @BindView(R.id.btn_item)
    Button mItemBtn;
    @BindView(R.id.ll_head)
    LinearLayout mHeadLL;

    List<WarehouseAllInOneBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    SupplyWareHouseController controller;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_supply_warehouse;
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText("补仓");
        mItemBtn.setText("发送订单");

        controller = new SupplyWareHouseController(this);
        TypedArray typedArray = mActivity.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        final Drawable divider = typedArray.getDrawable(0);
        typedArray.recycle();
        mHeadLL.setDividerDrawable(divider);
        CommonAdapter commonAdapter = new CommonAdapter<WarehouseAllInOneBean>(mActivity, R.layout.item_supply_warehouse, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, WarehouseAllInOneBean item, final int position) {
                LinearLayout itemLL = viewHolder.getView(R.id.ll_item);
                itemLL.setDividerDrawable(divider);

                ImageView imageView = viewHolder.getView(R.id.iv_network);
                mApplication.getImageLoaderFactory().loadCommonImgByUrl(mActivity, item.getProduct_image(), imageView);
                viewHolder.setText(R.id.tv_name, item.getProduct_name());
                // 剩余
                int leaveNum = item.getNum() - item.getOrder_num();
                // 待收
                int waitNum = item.getWait_num();
                // 补仓
                int requestNum = item.getRequest_num();
                viewHolder.setText(R.id.tv_buy_num, leaveNum + "/" + waitNum + item.getUnit());
                viewHolder.setText(R.id.tv_storage_num, requestNum + "");
                viewHolder.setOnClickListener(R.id.tv_storage_num, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleItemClick(position);
                    }
                });
            }
        };
        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        getNeedData();
    }

    /**
     * 网络获取数据
     */
    private void getNeedData() {
        addSubscription(controller.getStorageInfoList(), new Consumer<MBaseBean<List<WarehouseAllInOneBean>>>() {
            @Override
            public void accept(MBaseBean<List<WarehouseAllInOneBean>> baseBean) throws Exception {
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

    @OnClick(R.id.btn_item)
    public void item11Click(View view) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (WarehouseAllInOneBean bean : dataList) {
                JSONObject json = new JSONObject();
                json.put("product_id", bean.getProduct_id());
                json.put("num", bean.getRequest_num());
                jsonArray.put(json);
            }
        } catch (JSONException e) {
        }
        showAlertDialog();
        addSubscription(controller.dispatchOrder(jsonArray.toString()), new Consumer<MBaseBean<String>>() {
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

    /**
     * 点击item
     */
    private void handleItemClick(final int position) {
        String productName = dataList.get(position).getProduct_name();
        int orginalNum = dataList.get(position).getRequest_num();
        int minNum = 0;
        int maxNum = Integer.MAX_VALUE;
        MDialogUtils.showNumberDialog(mActivity, productName, orginalNum, minNum, maxNum, true, new MDialogUtils.OnSureListener() {
            @Override
            public void onSure(int number, int offset, AlertDialog dialog) {
                dialog.dismiss();
                dataList.get(position).setRequest_num(number);
                mAdapter.notifyItemChanged(position);
            }
        });
    }
}
