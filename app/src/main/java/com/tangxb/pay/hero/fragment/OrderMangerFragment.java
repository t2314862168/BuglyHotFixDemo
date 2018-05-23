package com.tangxb.pay.hero.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.activity.BaseActivityWithSearch;
import com.tangxb.pay.hero.activity.OrderInfoActivity;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.OrderBean;
import com.tangxb.pay.hero.controller.OrderMangerFragmentController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.event.SearchKeyEvent;
import com.tangxb.pay.hero.util.ConstUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 用户列表界面<br>
 * Created by zll on 2018/1/12.
 */
public class OrderMangerFragment extends BaseFragment {
    private int page = 1, rows = ConstUtils.PAGE_SIZE;
    private List<OrderBean> dataList = new ArrayList<>();
    private static final String FRAGMENT_NAME = "fragmentName";
    private static final String ROLE_ID = "currentRoleId";
    private String fragmentName;
    private int currentRoleId;
    private CommonAdapter<OrderBean> commonAdapter;
    private static final int TO_EDIT_USER_CODE = 666;

    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerAdapterWithHF mAdapter;
    private String searchKey;
    OrderMangerFragmentController fragmentController;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order_manager;
    }

    public static OrderMangerFragment getInstance(String name, int roleId) {
        OrderMangerFragment fragment = new OrderMangerFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_NAME, name);
        args.putInt(ROLE_ID, roleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void receiveBundleFromActivity(Bundle arg) {
        fragmentName = arg.getString(FRAGMENT_NAME);
        currentRoleId = arg.getInt(ROLE_ID);
    }

    @Override
    protected void initData() {
        setNeedOnCreateRegister();
        fragmentController = new OrderMangerFragmentController((BaseActivity) mActivity);
        // 获取Activity界面里面的搜索关键字
        if (mActivity instanceof BaseActivityWithSearch) {
            String sk = ((BaseActivityWithSearch) mActivity).getSearchKeyword();
            searchKey = TextUtils.isEmpty(sk) ? null : sk;
        }
        commonAdapter = new CommonAdapter<OrderBean>(mActivity, R.layout.item_fragment_order_manager, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, OrderBean item, int position) {
                viewHolder.setText(R.id.tv_name, item.getRealname());
                viewHolder.setText(R.id.tv_time, item.getCreateTime());
                viewHolder.setText(R.id.tv_total_freight, "¥" + item.getProductTotalPrice() + "¥" + item.getTotalFreight());
                viewHolder.setText(R.id.tv_buy_num, item.getBuyNum() + "");
            }
        };
        mAdapter = new RecyclerAdapterWithHF((MultiItemTypeAdapter) commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                handleItemClick(position, dataList.get(position));
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
        mActivity.getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
            }
        });
    }

    /**
     * 搜索事件
     *
     * @param event
     */
    @Subscribe
    public void onSearchKeyEvent(SearchKeyEvent event) {
        searchKey = event.getSearchKey();
        ptrClassicFrameLayout.autoRefresh();
    }

    /**
     * 下拉刷新获取数据
     */
    private void getDataByRefresh() {
        // 开始网络请求
        page = 1;
        addSubscription(fragmentController.getOrderList(currentRoleId, page, rows, searchKey), new Consumer<MBaseBean<List<OrderBean>>>() {
            @Override
            public void accept(MBaseBean<List<OrderBean>> baseBean) throws Exception {
                dataList.clear();
                if (baseBean.getData() != null) {
                    dataList.addAll(baseBean.getData());
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
        addSubscription(fragmentController.getOrderList(currentRoleId, page, rows, searchKey), new Consumer<MBaseBean<List<OrderBean>>>() {
            @Override
            public void accept(MBaseBean<List<OrderBean>> baseBean) throws Exception {
                if (baseBean.getData() != null) {
                    dataList.addAll(baseBean.getData());
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
     * 点击item
     *
     * @param orderBean
     */
    private void handleItemClick(int position, OrderBean orderBean) {
        Intent intent = ((BaseActivity) mActivity).getIntentWithPublicParams(OrderInfoActivity.class);
        int headerViewsCount = mAdapter.getHeadSize();
        int realPosition = position - headerViewsCount;
        intent.putExtra("realPosition", realPosition);
        intent.putExtra("orderBean", orderBean);
        startActivityForResult(intent, TO_EDIT_USER_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TO_EDIT_USER_CODE && resultCode == Activity.RESULT_OK) {
            int realPosition = data.getIntExtra("realPosition", -1);
            OrderBean userBean = data.getParcelableExtra("orderBean");
            copyNeedInfo(realPosition, userBean);
            commonAdapter.notifyDataSetChanged();
        }
    }

    public void copyNeedInfo(int realPosition, OrderBean tempBean) {
        if (realPosition != -1 && tempBean != null) {
            OrderBean bean = dataList.get(realPosition);
            int headerViewsCount = mAdapter.getHeadSize();
            int position = realPosition + headerViewsCount;
//            CopyBeanController.copyUserBean(tempBean, bean);
//            mAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 重置数据
        searchKey = null;
        page = 1;
        dataList.clear();
    }


}
