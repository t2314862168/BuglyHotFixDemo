package com.tangxb.pay.hero.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.WarehouseAllInOneBean;
import com.tangxb.pay.hero.controller.WarehouseController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 总览库存界面<br>
 * Created by zll on 2018/5/26.
 */

public class WarehouseAllInOneActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_head)
    LinearLayout mHeadLL;
    List<WarehouseAllInOneBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    WarehouseController controller;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_warehouse_all_inone;
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText("总览库存");

        controller = new WarehouseController(this);
        TypedArray typedArray = mActivity.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        final Drawable divider = typedArray.getDrawable(0);
        typedArray.recycle();
        mHeadLL.setDividerDrawable(divider);
        CommonAdapter commonAdapter = new CommonAdapter<WarehouseAllInOneBean>(mActivity, R.layout.item_warehouse_all_inone, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, WarehouseAllInOneBean item, int position) {
                LinearLayout itemLL = viewHolder.getView(R.id.ll_item);
                itemLL.setDividerDrawable(divider);

                ImageView imageView = viewHolder.getView(R.id.iv_network);
                mApplication.getImageLoaderFactory().loadCommonImgByUrl(mActivity, item.getProduct_image(), imageView);
                viewHolder.setText(R.id.tv_name, item.getProduct_name());
                viewHolder.setText(R.id.tv_buy_num, item.getRequest_num() + "/" + item.getWait_num() + item.getUnit());
                viewHolder.setText(R.id.tv_storage_num, item.getNum() + "");
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
        addSubscription(controller.getStorageOrderAllInOne(), new Consumer<MBaseBean<List<WarehouseAllInOneBean>>>() {
            @Override
            public void accept(MBaseBean<List<WarehouseAllInOneBean>> baseBean) throws Exception {
                if (baseBean.getData() != null) {
                    dataList.addAll(baseBean.getData());
                    mAdapter.notifyDataSetChangedHF();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println();
            }
        });
    }

    /**
     * 点击item
     */
    private void handleItemClick(int position) {

    }
}
