package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.DeliverPersonBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.ReceiveGoodsBean;
import com.tangxb.pay.hero.controller.DispatchGoodsMangerController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.ConstUtils;
import com.tangxb.pay.hero.util.DateUtils;
import com.tangxb.pay.hero.util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 收货界面<br>
 * Created by tangxuebing on 2018/5/23.
 */

public class ReceiveGoodsActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_head)
    LinearLayout mHeadLL;

    List<ReceiveGoodsBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    DispatchGoodsMangerController controller;
    int page = 1, rows = ConstUtils.PAGE_SIZE;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_receive_goods;
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText("开始配送");
        // 输入法不改变布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        controller = new DispatchGoodsMangerController(this);
        TypedArray typedArray = mActivity.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        final Drawable divider = typedArray.getDrawable(0);
        typedArray.recycle();
        mHeadLL.setDividerDrawable(divider);
        CommonAdapter commonAdapter = new CommonAdapter<ReceiveGoodsBean>(mActivity, R.layout.item_receive_goods, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, ReceiveGoodsBean item, final int position) {
                LinearLayout itemLL = viewHolder.getView(R.id.ll_item);
                itemLL.setDividerDrawable(divider);

                viewHolder.setText(R.id.tv_car_number, item.getCart_no());
                viewHolder.setText(R.id.tv_date, DateUtils.formatDate(new Date(item.getCreate_time())));
                viewHolder.setText(R.id.tv_send_goods_man, item.getSend_name());
                viewHolder.setText(R.id.tv_receive_goods_man, item.getRequest_name());
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
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandlerEx() {
            @Override
            public void onRefreshBegin(PtrFrameLayoutEx frame) {
                getDataByRefresh();
            }
        });
        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                getDataByLoadMore();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNeedData();
    }

    /**
     * 下拉刷新获取数据
     */
    private void getDataByRefresh() {
        // 开始网络请求
        page = 1;
        addSubscription(controller.receiveList(page, rows, null), new Consumer<MBaseBean<List<ReceiveGoodsBean>>>() {
            @Override
            public void accept(MBaseBean<List<ReceiveGoodsBean>> baseBean) throws Exception {
                dataList.clear();
                if (baseBean.getData() != null && baseBean.getData().size() > 0) {
                    dataList.addAll(baseBean.getData());
                }
                mAdapter.notifyDataSetChanged();
                ptrClassicFrameLayout.refreshComplete();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.t(mApplication, throwable.getMessage());
                ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    /**
     * 上拉加载更多获取数据
     */
    private void getDataByLoadMore() {
        // 开始网络请求
        page++;
        addSubscription(controller.receiveList(page, rows, null), new Consumer<MBaseBean<List<ReceiveGoodsBean>>>() {
            @Override
            public void accept(MBaseBean<List<ReceiveGoodsBean>> baseBean) throws Exception {
                dataList.clear();
                if (baseBean.getData() != null && baseBean.getData().size() > 0) {
                    dataList.addAll(baseBean.getData());
                }
                mAdapter.notifyDataSetChanged();
                ptrClassicFrameLayout.loadMoreComplete(true);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.t(mApplication, throwable.getMessage());
                ptrClassicFrameLayout.loadMoreComplete(true);
            }
        });
    }

    /**
     * 网络获取数据
     */
    private void getNeedData() {
        addSubscription(controller.receiveList(page, rows, null), new Consumer<MBaseBean<List<ReceiveGoodsBean>>>() {
            @Override
            public void accept(MBaseBean<List<ReceiveGoodsBean>> baseBean) throws Exception {
                dataList.clear();
                if (baseBean.getData() != null && baseBean.getData().size() > 0) {
                    dataList.addAll(baseBean.getData());
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.t(mApplication, throwable.getMessage());
            }
        });
    }

    /**
     * 点击item
     */
    private void handleItemClick(int position) {
        Intent intent = getIntentWithPublicParams(DispatchPersonOrderListActivity.class);
        intent.putExtra("orderId", dataList.get(position).getId());
        intent.putExtra("status", dataList.get(position).getStatus());
        startActivity(intent);
    }
}
