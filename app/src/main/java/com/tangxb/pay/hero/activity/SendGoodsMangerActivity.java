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
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.StorageOrderBean;
import com.tangxb.pay.hero.controller.SendGoodsMangerController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 发货管理界面<br>
 * Created by tangxuebing on 2018/5/23.
 */

public class SendGoodsMangerActivity extends BaseActivityWithTitle {
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
    SendGoodsMangerController controller;

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

        dataList.add(new StorageOrderBean());
        dataList.add(new StorageOrderBean());
        dataList.add(new StorageOrderBean());

        TypedArray typedArray = mActivity.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        final Drawable divider = typedArray.getDrawable(0);
        typedArray.recycle();
        mHeadLL.setDividerDrawable(divider);
        CommonAdapter commonAdapter = new CommonAdapter<StorageOrderBean>(mActivity, R.layout.item_dispatch_manger, dataList) {
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
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                handleItemClick(position);
            }
        });
    }

    /**
     * 网络获取数据
     */
    private void getNeedData() {
        addSubscription(controller.getStorageOrderList(), new Consumer<MBaseBean<List<StorageOrderBean>>>() {
            @Override
            public void accept(MBaseBean<List<StorageOrderBean>> baseBean) throws Exception {

            }
        });
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
