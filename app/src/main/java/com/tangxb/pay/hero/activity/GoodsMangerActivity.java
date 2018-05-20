package com.tangxb.pay.hero.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.GoodsBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.controller.CopyBeanController;
import com.tangxb.pay.hero.controller.GoodsMangerController;
import com.tangxb.pay.hero.controller.TextFontStyleController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.ConstUtils;
import com.tangxb.pay.hero.util.PriceCalculator;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by tangxuebing on 2018/5/15.
 */

public class GoodsMangerActivity extends BaseActivityWithSearch {
    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_switch_goods)
    Button mSwitchBtn;
    private List<GoodsBean> mDataList = new ArrayList<>();
    private RecyclerAdapterWithHF mAdapter;
    private int page = 1, rows = ConstUtils.PAGE_SIZE;
    /**
     * 当前种类id
     */
    long categoryId;
    /**
     * 当前种类名字
     */
    String categoryName;
    /**
     * 1 在售，2 已下架 3 废除
     */
    int goodsStatus = ConstUtils.GOOD_ON_SALE;
    /**
     * 默认为0表示非促销  1 表示促销
     */
    int promotion = ConstUtils.GOOD_UN_PROMOTION;
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

    /**
     * 点击左边按钮
     */
    @Override
    public void clickLeftBtn() {
        Intent intent = getIntentWithPublicParams(GoodsCategoryActivity.class);
        startActivity(intent);
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        categoryId = intent.getLongExtra("categoryId", 0);
        categoryName = intent.getStringExtra("categoryName");
        promotion = intent.getIntExtra("promotion", ConstUtils.GOOD_PROMOTION);
    }

    @Override
    protected void initData() {
        handleSearchTitle();
        setLeftBtnText(R.string.all);
        if (!TextUtils.isEmpty(categoryName)) {
            setMiddleText(categoryName);
        } else {
            setMiddleText(R.string.goods_manger);
        }
        // 输入法不改变布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        controller = new GoodsMangerController(this);
        if (goodsStatus == ConstUtils.GOOD_ON_SALE) {
            mSwitchBtn.setText(String.format(mResources.getString(R.string.now_stats_is), mResources.getString(R.string.on_sale)));
        } else if (goodsStatus == ConstUtils.GOOD_NOT_SALE) {
            mSwitchBtn.setText(String.format(mResources.getString(R.string.now_stats_is), mResources.getString(R.string.not_on_sale)));
        }
    }

    @Override
    protected void initListener() {
        CommonAdapter commonAdapter = new CommonAdapter<GoodsBean>(mActivity, R.layout.item_goods_manger, mDataList) {
            @Override
            protected void convert(ViewHolder viewHolder, GoodsBean bean, final int position) {
                ImageView imageView = viewHolder.getView(R.id.iv_network);
                mApplication.getImageLoaderFactory().loadCommonImgByUrl(mActivity, bean.getMainImage(), imageView);
                viewHolder.setText(R.id.tv_index_num, "" + (position + 1));
                SpannableString blackMiddleRedSmall = TextFontStyleController.blackMiddleRedSmall(mActivity, bean.getName(), bean.getSubtitle());
                SpannableString redBigBlackSmall = TextFontStyleController.redBigBlackSmall(mActivity, PriceCalculator.leaveTwoNumPrice(bean.getPrice()), "/" + bean.getUnit());
                TextView titleTv = viewHolder.getView(R.id.tv_name);
                TextView priceTv = viewHolder.getView(R.id.tv_price);
                titleTv.setText(blackMiddleRedSmall, TextView.BufferType.SPANNABLE);
                priceTv.setText(redBigBlackSmall, TextView.BufferType.SPANNABLE);
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
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
            }
        });
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
        intent.putExtra("position", position);
        intent.putExtra("goodsBean", mDataList.get(position));
        startActivityForResult(intent, EDIT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == EDIT_REQUEST_CODE) {
            GoodsBean tempBean = data.getParcelableExtra("goodsBean");
            int position = data.getIntExtra("position", -1);
            if (tempBean != null && position != -1) {
                GoodsBean bean = mDataList.get(position);
                CopyBeanController.copyGoodsBean(tempBean, bean);
                mAdapter.notifyItemChanged(position);
            }
        }
    }


    /**
     * 下拉刷新获取数据
     */
    private void getDataByRefresh() {
        // 开始网络请求
        page = 1;
        addSubscription(controller.getGoodsList(page, rows, categoryId, mSearchKeyword, goodsStatus, promotion)
                , new Consumer<MBaseBean<List<GoodsBean>>>() {
                    @Override
                    public void accept(MBaseBean<List<GoodsBean>> baseBean) throws Exception {
                        mDataList.clear();
                        if (baseBean.getData() != null) {
                            mDataList.addAll(baseBean.getData());
                        }
                        mAdapter.notifyDataSetChangedHF();
                        ptrClassicFrameLayout.refreshComplete();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
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
        addSubscription(controller.getGoodsList(page, rows, categoryId, mSearchKeyword, goodsStatus, promotion)
                , new Consumer<MBaseBean<List<GoodsBean>>>() {
                    @Override
                    public void accept(MBaseBean<List<GoodsBean>> baseBean) throws Exception {
                        mDataList.clear();
                        if (baseBean.getData() != null) {
                            mDataList.addAll(baseBean.getData());
                        }
                        mAdapter.notifyDataSetChangedHF();
                        ptrClassicFrameLayout.loadMoreComplete(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ptrClassicFrameLayout.loadMoreComplete(true);
                    }
                });
    }

    /**
     * 上下架切换
     *
     * @param view
     */
    @OnClick(R.id.btn_switch_goods)
    public void switchGoods(View view) {
        if (goodsStatus == ConstUtils.GOOD_ON_SALE) {
            goodsStatus = ConstUtils.GOOD_NOT_SALE;
            mSwitchBtn.setText(String.format(mResources.getString(R.string.now_stats_is), mResources.getString(R.string.not_on_sale)));
        } else if (goodsStatus == ConstUtils.GOOD_NOT_SALE) {
            goodsStatus = ConstUtils.GOOD_ON_SALE;
            mSwitchBtn.setText(String.format(mResources.getString(R.string.now_stats_is), mResources.getString(R.string.on_sale)));
        }
        ptrClassicFrameLayout.autoRefresh();
    }

    /**
     * 分类管理
     *
     * @param view
     */
    @OnClick(R.id.btn_category_manger)
    public void categoryManger(View view) {
        Intent intent = getIntentWithPublicParams(GoodsCategoryMangerActivity.class);
        startActivity(intent);
    }

    /**
     * 上传商品
     *
     * @param view
     */
    @OnClick(R.id.btn_create_goods)
    public void createGoods(View view) {
        Intent intent = getIntentWithPublicParams(GoodsEditorActivity.class);
        startActivity(intent);
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
