package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.SendsGoodsBean;
import com.tangxb.pay.hero.bean.WarehouseBean;
import com.tangxb.pay.hero.controller.WarehouseController;
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
 * 发货界面->仓库列表界面->仓库订单列表界面<br>
 * Created by tangxuebing on 2018/5/23.
 */

public class SendGoodsWarehouseOrderListActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_head)
    LinearLayout mHeadLL;
    @BindView(R.id.btn_item)
    Button mItemBtn1;

    List<SendsGoodsBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    WarehouseController controller;
    long id;
    WarehouseBean warehouseBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_send_goods_warehouse_order_list;
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
        if (warehouseBean != null) {
            setMiddleText(warehouseBean.getName() + "库房");
        } else {
            setMiddleText("unknown库房");
        }
        mItemBtn1.setText("保存分配");
        // 输入法不改变布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        controller = new WarehouseController(this);
        TypedArray typedArray = mActivity.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        final Drawable divider = typedArray.getDrawable(0);
        typedArray.recycle();
        mHeadLL.setDividerDrawable(divider);
        CommonAdapter commonAdapter = new CommonAdapter<SendsGoodsBean>(mActivity, R.layout.item_send_goods_warehouse_order_list, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, SendsGoodsBean item, final int position) {
                LinearLayout itemLL = viewHolder.getView(R.id.ll_item);
                itemLL.setDividerDrawable(divider);
                ImageView imageView = viewHolder.getView(R.id.iv_network);
                mApplication.getImageLoaderFactory().loadCommonImgByUrl(mActivity, item.getProductImage(), imageView);
                viewHolder.setText(R.id.tv_name, item.getProductName());
                viewHolder.setText(R.id.tv_buy_num, item.getRequest_num() + item.getProductUnit());
                viewHolder.setText(R.id.tv_storage_num, item.getWait_num() + item.getProductUnit());
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
        addSubscription(controller.getOrderSingleStorage(id), new Consumer<MBaseBean<List<SendsGoodsBean>>>() {
            @Override
            public void accept(MBaseBean<List<SendsGoodsBean>> baseBean) throws Exception {
                dataList.clear();
                if (baseBean.getData() != null && baseBean.getData().size() > 0) {
                    dataList.addAll(baseBean.getData());
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.t(mApplication, throwable.getMessage());
            }
        });
    }


    /**
     * 点击item
     */
    private void handleItemClick(final int position) {
        int orginalNum = dataList.get(position).getWait_num();
        int minNum = 0;
        int maxNum = Integer.MAX_VALUE;
        MDialogUtils.showNumberDialog(mActivity, "分配数量", orginalNum, minNum, maxNum, true, new MDialogUtils.OnSureListener() {
            @Override
            public void onSure(int number, int offset, AlertDialog dialog) {
                dialog.dismiss();
                dataList.get(position).setWait_num(number);
                mAdapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    public void onBackPressed() {
        MDialogUtils.showMessage(mActivity, "保存配送信息", "确定", "取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item11Click(null);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 配送完成
     *
     * @param view
     */
    @OnClick(R.id.btn_item)
    public void item11Click(View view) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (SendsGoodsBean bean : dataList) {
                JSONObject json = new JSONObject();
                json.put("order_id", bean.getOrder_id());
                json.put("product_id", bean.getProductId());
                json.put("wait_num", bean.getWait_num());
                jsonArray.put(json);
            }
        } catch (JSONException e) {
        }
        showAlertDialog();
        addSubscription(controller.saveProductDispatch(jsonArray.toString()), new Consumer<MBaseBean<String>>() {
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
}
