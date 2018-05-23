package com.tangxb.pay.hero.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.GoodsBean;
import com.tangxb.pay.hero.controller.DispatchMangerController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by tangxuebing on 2018/5/23.
 */

public class DispatchMangerActivity extends BaseActivityWithTitle {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_bottom)
    View mBottomLL;
    @BindView(R.id.btn_item_1)
    Button mItemBtn1;
    @BindView(R.id.btn_item_2)
    Button mItemBtn2;
    List<GoodsBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    DispatchMangerController controller;

    @Override
    public void clickLeftBtn() {

    }

    @Override
    public void clickRightBtn() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_dispatch_manger;
    }

    @Override
    protected void initData() {
        handleTitle();
        setLeftText("补仓");
        setMiddleText(R.string.dispatch_manger);
        setRightText("收货");
        mItemBtn1.setText("开始配送");
        mItemBtn2.setText("配送管理");

        controller = new DispatchMangerController(this);
    }

    @OnClick(R.id.btn_item_1)
    public void item11Click(View view) {

    }

    @OnClick(R.id.btn_item_2)
    public void item12Click(View view) {

    }
}
