package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.GoodsCategoryBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.controller.GoodsCategoryController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 商品分类界面<br>
 * Created by zll on 2018/5/19.
 */

public class GoodsCategoryActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerAdapterWithHF mAdapter;
    private List<GoodsCategoryBean> dataList = new ArrayList<>();
    GoodsCategoryController categoryController;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_category;
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.category);
        categoryController = new GoodsCategoryController(this);

        CommonAdapter commonAdapter = new CommonAdapter<GoodsCategoryBean>(mActivity, R.layout.item_goods_category, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, GoodsCategoryBean item, final int position) {
                if (item.getId() == 0) {
                    viewHolder.setText(R.id.tv_name, mResources.getString(R.string.promotion));
                } else {
                    viewHolder.setText(R.id.tv_name, item.getName());
                }
            }
        };
        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                handleItemClick(position, dataList.get(position));
            }
        });
        getDataByRefresh();
    }

    private void getDataByRefresh() {
        addSubscription(categoryController.getCategoryList(1), new Consumer<MBaseBean<List<GoodsCategoryBean>>>() {
            @Override
            public void accept(MBaseBean<List<GoodsCategoryBean>> baseBean) throws Exception {
                // 添加一个促销
                dataList.add(new GoodsCategoryBean());
                dataList.addAll(baseBean.getData());
                mAdapter.notifyDataSetChangedHF();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println();
            }
        });
    }

    private void handleItemClick(int position, GoodsCategoryBean bean) {
        Intent intent = getIntentWithPublicParams(GoodsMangerActivity.class);
        intent.putExtra("categoryId", bean.getId());
        intent.putExtra("categoryName", bean.getId() == 0 ? mResources.getString(R.string.promotion) : bean.getName());
        intent.putExtra("promotion", bean.getId() == 0 ? 1 : bean.getId());
        startActivity(intent);
    }
}
