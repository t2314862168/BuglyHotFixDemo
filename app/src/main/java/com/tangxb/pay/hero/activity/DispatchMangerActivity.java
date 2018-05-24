package com.tangxb.pay.hero.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.DeliverGoodsBean;
import com.tangxb.pay.hero.bean.GoodsBean;
import com.tangxb.pay.hero.controller.DispatchMangerController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 配送管理界面<br>
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
    @BindView(R.id.ll_head)
    LinearLayout mHeadLL;

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

        dataList.add(new GoodsBean());
        dataList.add(new GoodsBean());
        dataList.add(new GoodsBean());
        dataList.add(new GoodsBean());

        controller = new DispatchMangerController(this);
        TypedArray typedArray = mActivity.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        final Drawable divider = typedArray.getDrawable(0);
        typedArray.recycle();
        mHeadLL.setDividerDrawable(divider);
        CommonAdapter commonAdapter = new CommonAdapter<GoodsBean>(mActivity, R.layout.item_deliver_goods, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, GoodsBean item, int position) {
                LinearLayout itemLL = viewHolder.getView(R.id.ll_item);
                itemLL.setDividerDrawable(divider);
            }
        };
        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                handleItemClick(position);
            }
        });
    }

    @OnClick(R.id.btn_item_1)
    public void item11Click(View view) {

    }

    @OnClick(R.id.btn_item_2)
    public void item12Click(View view) {

    }

    /**
     * 点击item
     */
    private void handleItemClick(int position) {

    }
}
