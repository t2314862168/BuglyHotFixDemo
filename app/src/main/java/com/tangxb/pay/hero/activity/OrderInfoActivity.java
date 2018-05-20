package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.OrderBean;
import com.tangxb.pay.hero.bean.OrderItemBean;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/20 0020.
 */

public class OrderInfoActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    OrderBean orderBean;
    List<OrderItemBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;

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
        dataList.add(new OrderItemBean());
        dataList.add(new OrderItemBean());
        dataList.add(new OrderItemBean());

        CommonAdapter commonAdapter = new CommonAdapter<OrderItemBean>(mActivity, R.layout.item_order_info, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, OrderItemBean item, int position) {

            }
        };
        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        View header = View.inflate(mActivity, R.layout.layout_order_info_header, null);
        mAdapter.addHeader(header);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        getOrderItemData();
    }

    private void getOrderItemData() {
        if (orderBean == null) return;
    }
}
