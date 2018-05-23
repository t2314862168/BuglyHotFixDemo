package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.OrderBean;
import com.tangxb.pay.hero.bean.OrderItemBean;
import com.tangxb.pay.hero.controller.OrderInfoController;
import com.tangxb.pay.hero.controller.TextFontStyleController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.event.ChangeOrderStatusEvent;
import com.tangxb.pay.hero.util.PriceCalculator;
import com.tangxb.pay.hero.util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/5/20 0020.
 */

public class OrderInfoActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    OrderBean orderBean;
    List<OrderItemBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    OrderInfoController controller;
    TextView tv_user_info;
    TextView tv_address;
    TextView tv_total_freight;
    TextView tv_mark;
    String total = "0";
    String freight = "0";
    int totalBuyNum;
    int totalGiveNum;
    @BindView(R.id.total_price)
    TextView total_price;
    @BindView(R.id.total_num)
    TextView total_num;
    @BindView(R.id.btn_status)
    Button btn_status;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        orderBean = intent.getParcelableExtra("orderBean");
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText("订单详情");
        // 输入法不改变布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        controller = new OrderInfoController(this);
        CommonAdapter commonAdapter = new CommonAdapter<OrderItemBean>(mActivity, R.layout.item_order_info, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, OrderItemBean item, int position) {
                ImageView imageView = viewHolder.getView(R.id.iv);
                mApplication.getImageLoaderFactory().loadCommonImgByUrl(mActivity, item.getProduct_image(), imageView);
                viewHolder.setText(R.id.tv_name, item.getProduct_name());

                TextView tv_buy_num = viewHolder.getView(R.id.tv_buy_num);
                String str = "订购：" + item.getBuy_num() + item.getProduct_unit();
                int leaveNum = item.getBuy_num() - item.getGive_num();
                if (leaveNum > 0) {
                    String str2 = "(差" + leaveNum + item.getProduct_unit() + ")";
                    SpannableString greenSmallRedSmall = TextFontStyleController.greenSmallRedSmall(mActivity, str, str2);
                    tv_buy_num.setText(greenSmallRedSmall, TextView.BufferType.SPANNABLE);
                } else {
                    tv_buy_num.setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
                    tv_buy_num.setText(str);
                }
                viewHolder.setText(R.id.tv_price, "¥" + item.getPiece_price());
                TextView tv_total_price = viewHolder.getView(R.id.tv_total_price);
                SpannableString blackSmallBig = TextFontStyleController.blackSmallBig(mActivity, "小计：¥", item.getSub_price());
                tv_total_price.setText(blackSmallBig, TextView.BufferType.SPANNABLE);
            }
        };
        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        View header = View.inflate(mActivity, R.layout.layout_order_info_header, null);
        mAdapter.addHeader(header);
        tv_user_info = findView(header, R.id.tv_user_info);
        tv_address = findView(header, R.id.tv_address);
        tv_total_freight = findView(header, R.id.tv_total_freight);
        tv_mark = findView(header, R.id.tv_mark);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        showDataUi();
        getOrderItemData();
    }

    /**
     * 显示数据
     */
    private void showDataUi() {
        if (orderBean == null) return;
        tv_user_info.setText("客户: " + orderBean.getRealname() + "       电话:" + orderBean.getMobile());
        tv_address.setText("地址: " + orderBean.getAddress());
        if (TextUtils.isEmpty(orderBean.getRemark())) {
            tv_mark.setText(orderBean.getRemark());
        }
        if (!TextUtils.isEmpty(orderBean.getOperStatus()) && orderBean.getOperStatus().equals("true")) {
            btn_status.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.color_red));
        } else {
            btn_status.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main_color));
        }
    }

    /**
     * 获取到数据之后,改变总价等
     *
     * @param total
     * @param freight
     */
    private void changeTotalText(String total, String freight) {
        tv_total_freight.setText("商品总价: ¥" + total + "       运费:¥" + freight);
        total_price.setText("合计：¥" + PriceCalculator.add(total, freight));
        total_num.setText("共计：" + totalBuyNum + "件商品");
        btn_status.setText(orderBean.getStatusName());
    }

    /**
     * 点击状态按钮
     *
     * @param view
     */
    @OnClick(R.id.btn_status)
    public void clickStatusBtn(View view) {
        if (TextUtils.isEmpty(orderBean.getOperStatus()) || !orderBean.getOperStatus().equals("true"))
            return;
        addSubscription(controller.operateOrder(orderBean.getId(), orderBean.getStatus()), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> baseBean) throws Exception {
                ToastUtils.t(mApplication, baseBean.getMessage());
                EventBus.getDefault().post(new ChangeOrderStatusEvent());
                finish();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.t(mApplication, throwable.getMessage());
            }
        });
    }

    /**
     * 网络获取订单详情数据
     */
    private void getOrderItemData() {
        if (orderBean == null) return;
        addSubscription(controller.getOrderDetail(orderBean.getId(), orderBean.getStatus()), new Consumer<MBaseBean<List<OrderItemBean>>>() {
            @Override
            public void accept(MBaseBean<List<OrderItemBean>> baseBean) throws Exception {
                if (baseBean.getData() != null) {
                    dataList.addAll(baseBean.getData());
                }
                for (OrderItemBean item : dataList) {
                    total = PriceCalculator.add(total, item.getSub_price());
                    freight = PriceCalculator.add(freight, item.getSub_freight());
                    totalBuyNum += item.getBuy_num();
                    totalGiveNum += item.getGive_num();
                }
                changeTotalText(total, freight);
                mAdapter.notifyDataSetChangedHF();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println();
            }
        });
    }
}
