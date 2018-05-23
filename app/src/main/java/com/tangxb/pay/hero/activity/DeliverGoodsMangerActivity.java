package com.tangxb.pay.hero.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.GoodsBean;
import com.tangxb.pay.hero.controller.DeliverGoodsMangerController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by tangxuebing on 2018/5/23.
 */

public class DeliverGoodsMangerActivity extends BaseActivityWithTitle {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_bottom)
    View mBottomLL;
    @BindView(R.id.btn_item)
    Button mItemBtn;
    List<GoodsBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    DeliverGoodsMangerController controller;

    @Override
    public void clickLeftBtn() {

    }

    @Override
    public void clickRightBtn() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_deliver_goods;
    }

    @Override
    protected void initData() {
        handleTitle();
        setLeftText("获取新订单");
        setMiddleText(R.string.deliver_goods_manger);
        setRightText("收货");
        mItemBtn.setText("开始分配");

        controller = new DeliverGoodsMangerController(this);
    }

    @OnClick(R.id.btn_item)
    public void item11Click(View view) {

    }

}
