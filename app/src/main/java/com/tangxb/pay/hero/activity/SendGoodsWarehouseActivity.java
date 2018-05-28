package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.WarehouseBean;
import com.tangxb.pay.hero.controller.WarehouseController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 发货界面->仓库列表界面<br>
 * Created by zll on 2018/5/26.
 */

public class SendGoodsWarehouseActivity extends BaseActivityWithTitle {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_head)
    LinearLayout mHeadLL;

    List<WarehouseBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    WarehouseController controller;

    @Override
    public void clickLeftBtn() {
        Intent intent = getIntentWithPublicParams(WarehouseAllInOneActivity.class);
        startActivity(intent);
    }

    @Override
    public void clickRightBtn() {
        Intent intent = getIntentWithPublicParams(WarehouseEditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_send_goods_warehouse;
    }

    @Override
    protected void initData() {
        handleTitle();
        setLeftText("总览库存");
        setMiddleText("仓库管理");
        setRightText("创建库房");

        controller = new WarehouseController(this);
        TypedArray typedArray = mActivity.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        final Drawable divider = typedArray.getDrawable(0);
        typedArray.recycle();
        mHeadLL.setDividerDrawable(divider);
        CommonAdapter commonAdapter = new CommonAdapter<WarehouseBean>(mActivity, R.layout.item_send_goods_warehouse, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, WarehouseBean item, final int position) {
                LinearLayout itemLL = viewHolder.getView(R.id.ll_item);
                itemLL.setDividerDrawable(divider);

                viewHolder.setText(R.id.tv_name, item.getName());
                viewHolder.setText(R.id.tv_address, item.getCity() + item.getAddress());
            }
        };
        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                handleItem(position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNeedData();
    }

    /**
     * 网络获取数据
     */
    private void getNeedData() {
        addSubscription(controller.getStorageList(), new Consumer<MBaseBean<List<WarehouseBean>>>() {
            @Override
            public void accept(MBaseBean<List<WarehouseBean>> baseBean) throws Exception {
                if (baseBean.getCode() != 1) {
                    ToastUtils.t(mApplication, baseBean.getMessage());
                    return;
                }
                dataList.clear();
                if (baseBean.getData() != null) {
                    dataList.addAll(baseBean.getData());
                }
                mAdapter.notifyDataSetChanged();
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
     *
     * @param position
     */
    private void handleItem(int position) {
        Intent intent = getIntentWithPublicParams(SendGoodsWarehouseOrderListActivity.class);
        intent.putExtra("warehouseBean", dataList.get(position));
        startActivity(intent);
    }
}