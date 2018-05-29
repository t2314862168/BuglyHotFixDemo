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
import android.widget.TextView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.SendsGoodsBean;
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
 * 发货管理界面->单个商品订单列表界面<br>
 * Created by tangxuebing on 2018/5/23.
 */

public class SendGoodsSingleProductActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_bottom)
    View mBottomLL;
    @BindView(R.id.btn_item)
    Button mItemBtn;
    @BindView(R.id.ll_head)
    LinearLayout mHeadLL;
    @BindView(R.id.tv_name)
    TextView tv_name;

    List<SendsGoodsBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    WarehouseController controller;
    long productId, orderId;
    String productName;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_deliver_goods;
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        productId = intent.getLongExtra("product_id", -1L);
        orderId = intent.getLongExtra("order_id", -1L);
        productName = intent.getStringExtra("product_name");
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(productName);
        mItemBtn.setText("保存分配");

        // 输入法不改变布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        tv_name.setText("地区");
        controller = new WarehouseController(this);
        TypedArray typedArray = mActivity.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        final Drawable divider = typedArray.getDrawable(0);
        typedArray.recycle();
        mHeadLL.setDividerDrawable(divider);
        CommonAdapter commonAdapter = new CommonAdapter<SendsGoodsBean>(mActivity, R.layout.item_dispatch_manger, dataList) {
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
                    public void onClick(View v) {
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
        addSubscription(controller.getOrderSingleProduct(productId), new Consumer<MBaseBean<List<SendsGoodsBean>>>() {
            @Override
            public void accept(MBaseBean<List<SendsGoodsBean>> baseBean) throws Exception {
                if (baseBean.getCode() != 1) {
                    ToastUtils.t(mApplication, baseBean.getMessage());
                    return;
                }
                if (baseBean.getData() != null) {
                    dataList.addAll(baseBean.getData());
                    mAdapter.notifyDataSetChanged();
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
            for (SendsGoodsBean bean : dataList) {
                JSONObject json = new JSONObject();
                json.put("product_id", bean.getProductId());
                json.put("order_id", bean.getOrder_id());
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

}
