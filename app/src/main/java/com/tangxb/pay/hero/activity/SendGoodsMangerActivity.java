package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.SendsGoodsBean;
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

public class SendGoodsMangerActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_bottom)
    View mBottomLL;
    @BindView(R.id.btn_item)
    Button mItemBtn;
    @BindView(R.id.ll_head)
    LinearLayout mHeadLL;

    List<SendsGoodsBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    SendGoodsMangerController controller;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_deliver_goods;
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.deliver_goods_manger);
        mItemBtn.setText("开始分配");

        controller = new SendGoodsMangerController(this);
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
//                viewHolder.setText(R.id.tv_buy_num, leaveNum + "/" + waitNum + item.getUnit());
//                viewHolder.setText(R.id.tv_storage_num, requestNum + "");
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
        getNeedData();
    }

    /**
     * 网络获取数据
     */
    private void getNeedData() {
        addSubscription(controller.getStorageOrderAllInOne(), new Consumer<MBaseBean<List<SendsGoodsBean>>>() {
            @Override
            public void accept(MBaseBean<List<SendsGoodsBean>> baseBean) throws Exception {
                System.out.println();
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
        Intent intent = getIntentWithPublicParams(SendGoodsWarehouseActivity.class);
        startActivity(intent);
    }

    /**
     * 点击item
     */
    private void handleItemClick(int position) {

    }

}
