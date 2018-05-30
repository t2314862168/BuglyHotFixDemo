package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.DeliverPersonBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.controller.DispatchGoodsMangerController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
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
 * 配送人列表界面<br>
 * Created by tangxuebing on 2018/5/23.
 */

public class DispatchPersonListActivity extends BaseActivityWithSearch {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_head)
    LinearLayout mHeadLL;

    List<DeliverPersonBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    DispatchGoodsMangerController controller;

    @Override
    public void clickLeftBtn() {

    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_dispatch_person_list;
    }

    @Override
    protected void initData() {
        handleSearchTitle();
        setLeftBtnTextVisible(false);
        setMiddleText("配送人列表");
        // 输入法不改变布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        controller = new DispatchGoodsMangerController(this);
        TypedArray typedArray = mActivity.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        final Drawable divider = typedArray.getDrawable(0);
        typedArray.recycle();
        mHeadLL.setDividerDrawable(divider);
        CommonAdapter commonAdapter = new CommonAdapter<DeliverPersonBean>(mActivity, R.layout.item_dispatch_person_list, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, DeliverPersonBean item, final int position) {
                LinearLayout itemLL = viewHolder.getView(R.id.ll_item);
                itemLL.setDividerDrawable(divider);
                viewHolder.setText(R.id.tv_name, item.getName());
                viewHolder.setText(R.id.tv_address, item.getCity() + item.getAddress());
                viewHolder.setText(R.id.tv_date, DateUtils.formatDate(new Date(item.getCreate_time())));
                long timeDecrease = System.currentTimeMillis() - item.getUpdate_time();
                // 小于24hour
                if (timeDecrease < 24 * 60 * 60 * 1000L) {
                    itemLL.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.gradient_green));
                } else {
                    itemLL.setBackgroundColor(ContextCompat.getColor(mActivity, android.R.color.white));
                }
                viewHolder.setOnClickListener(R.id.iv_mobile, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mobile = dataList.get(position).getMobile();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));
                        startActivity(intent);
                    }
                });
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
     * 处理搜索事件
     *
     * @param searchKeyword
     */
    @Override
    public void handleSearchKeyword(String searchKeyword) {
        getNeedData();
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
        addSubscription(controller.getDeliverOrderList(mSearchKeyword), new Consumer<MBaseBean<List<DeliverPersonBean>>>() {
            @Override
            public void accept(MBaseBean<List<DeliverPersonBean>> baseBean) throws Exception {
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
        intent.putExtra("status", 1);
        intent.putExtra("titleName", dataList.get(position).getName());
        startActivity(intent);
    }
}
