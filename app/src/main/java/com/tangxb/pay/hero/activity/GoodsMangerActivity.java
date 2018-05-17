package com.tangxb.pay.hero.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.GoodsBean;
import com.tangxb.pay.hero.controller.GoodsMangerController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.ConstUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by tangxuebing on 2018/5/15.
 */

public class GoodsMangerActivity extends BaseActivityWithSearch {
    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    private List<GoodsBean> mDataList = new ArrayList<>();
    private RecyclerAdapterWithHF mAdapter;
    private int page = 1, pageSize = ConstUtils.PAGE_SIZE;
    long userId;
    GoodsMangerController controller;
    /**
     * 完全编辑请求码
     */
    private final int EDIT_REQUEST_CODE = 220;
    /**
     * 简单编辑请求码
     */
    private final int EDIT_SIMPLE_REQUEST_CODE = 221;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_manger;
    }

    @Override
    public void clickLeftBtn() {

    }

    @Override
    protected void initData() {
        handleSearchTitle();
        setLeftBtnTextVisible(false);
        setMiddleText(R.string.goods_manger);
        // 输入法不改变布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        controller = new GoodsMangerController(this);
    }

    @Override
    protected void initListener() {
        CommonAdapter commonAdapter = new CommonAdapter<GoodsBean>(mActivity, R.layout.item_goods_manger, mDataList) {
            @Override
            protected void convert(ViewHolder viewHolder, GoodsBean bean, final int position) {
                ImageView imageView = viewHolder.getView(R.id.iv_network);
                mApplication.getImageLoaderFactory().loadCommonImgByUrl(mActivity, bean.getGoodsHeadImgs(), imageView);
                viewHolder.setText(R.id.tv_index_num, "" + (position + 1));
                viewHolder.setText(R.id.tv_name, bean.getGoodsName());
                viewHolder.setOnClickListener(R.id.iv_red_edit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleItemEditClick(position);
                    }
                });
            }
        };
        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL, 1.5f));
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
        ptrClassicFrameLayout.autoRefresh();
    }

    /**
     * 点击item
     *
     * @param position
     */
    private void handleItemClick(int position) {
        handleItemEditClick(position);
    }

    /**
     * 点击item编辑
     *
     * @param position
     */
    private void handleItemEditClick(int position) {
        Intent intent = getIntentWithPublicParams(GoodsEditorActivity.class);
        startActivityForResult(intent, EDIT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == EDIT_REQUEST_CODE) {

        }
    }

    /**
     * 下拉刷新获取数据
     */
    private void getDataByRefresh() {
        // 开始网络请求
        page = 1;
        controller.getGoodsList(userId, mSearchKeyword, page, pageSize, new Consumer<GoodsBean>() {
            @Override
            public void accept(GoodsBean goodsBean) throws Exception {

            }
        });
        mDataList.clear();
        String testUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1526355339&di=59d3c87ee79c1dc93d539f65424ab1ab&src=http://img3.duitang.com/uploads/item/201506/14/20150614154319_is5Rd.jpeg";
        for (int i = 0; i < 20; i++) {
            GoodsBean bean = new GoodsBean();
            bean.setGoodsName("GoodsName-" + i);
            bean.setGoodsHeadImgs(testUrl);
            mDataList.add(bean);
        }
        mAdapter.notifyDataSetChangedHF();
        ptrClassicFrameLayout.refreshComplete();
    }

    /**
     * 上拉加载更多获取数据
     */
    private void getDataByLoadMore() {
        // 开始网络请求
        page++;
        mAdapter.notifyDataSetChangedHF();
        ptrClassicFrameLayout.loadMoreComplete(true);
    }

    /**
     * 处理搜索事件
     *
     * @param searchKeyword
     */
    @Override
    public void handleSearchKeyword(String searchKeyword) {
        ptrClassicFrameLayout.autoRefresh();
    }


}
