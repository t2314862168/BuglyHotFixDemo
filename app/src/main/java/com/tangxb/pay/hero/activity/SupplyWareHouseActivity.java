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
import com.tangxb.pay.hero.bean.StorageOrderBean;
import com.tangxb.pay.hero.controller.SupplyWareHouseController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

    List<StorageOrderBean> dataList = new ArrayList<>();
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
        CommonAdapter commonAdapter = new CommonAdapter<StorageOrderBean>(mActivity, R.layout.item_supply_warehouse, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, StorageOrderBean item, int position) {
                LinearLayout itemLL = viewHolder.getView(R.id.ll_item);
                itemLL.setDividerDrawable(divider);

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

    }

    @OnClick(R.id.btn_item)
    public void item11Click(View view) {

    }

    /**
     * 点击item
     */
    private void handleItemClick(int position) {

    }
}
