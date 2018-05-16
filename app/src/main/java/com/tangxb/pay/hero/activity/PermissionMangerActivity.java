package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.UserBean;
import com.tangxb.pay.hero.controller.PermissionMangerController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.event.SearchKeyEvent;
import com.tangxb.pay.hero.util.ConstUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class PermissionMangerActivity extends BaseActivityWithSearch {
    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerAdapterWithHF mAdapter;
    private List<UserBean> dataList = new ArrayList<>();

    int page = 1, rows = ConstUtils.PAGE_SIZE;
    PermissionMangerController permissionMangerController;
    long roleId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_permission_manger;
    }

    @Override
    protected void initData() {
        handleSearchTitle();
        setNeedOnCreateRegister();
        setMiddleText(R.string.permission_manger);
        setLeftBtnTextVisible(false);
        permissionMangerController = new PermissionMangerController(this);
        try {
            roleId = mApplication.getUserLoginResultBean().getUser().getRoleId();
        } catch (Exception e) {
        }

        CommonAdapter commonAdapter = new CommonAdapter<UserBean>(mActivity, R.layout.item_permission_manger, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, UserBean item, int position) {
                viewHolder.setText(R.id.tv_name, item.getNickname());

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
     * 搜索事件
     *
     * @param event
     */
    @Subscribe
    public void onSearchKeyEvent(SearchKeyEvent event) {
        String searchKey = event.getSearchKey();
        ptrClassicFrameLayout.autoRefresh();
    }

    /**
     * 下拉刷新获取数据
     */
    private void getDataByRefresh() {
        // 开始网络请求
        page = 1;
        addSubscription(permissionMangerController.getUserList(page, rows, roleId, null, 1), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> stringMBaseBean) throws Exception {
                dataList.clear();
                for (int i = 0; i < 20; i++) {
                    UserBean userBean = new UserBean();
                    userBean.setNickname("Nickname-" + i);
                    dataList.add(userBean);
                }
                mAdapter.notifyDataSetChangedHF();
                ptrClassicFrameLayout.refreshComplete();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                dataList.clear();
                for (int i = 0; i < 20; i++) {
                    UserBean userBean = new UserBean();
                    userBean.setNickname("Nickname-" + i);
                    dataList.add(userBean);
                }
                mAdapter.notifyDataSetChangedHF();
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
        addSubscription(permissionMangerController.getUserList(page, rows, roleId, null, 1), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> stringMBaseBean) throws Exception {
                for (int i = 0; i < 20; i++) {
                    UserBean userBean = new UserBean();
                    userBean.setNickname("Nickname-new-" + i);
                    dataList.add(userBean);
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
     * @param userBean
     */
    private void handleItemClick(int position, UserBean userBean) {
        Intent intent = ((BaseActivity) mActivity).getIntentWithPublicParams(EditUserInfoActivity.class);
        int headerViewsCount = mAdapter.getHeadSize();
        int realPosition = position - headerViewsCount;
        intent.putExtra("realPosition", realPosition);
//        intent.putExtra("passUser", userList.get(realPosition));
//        intent.putExtra("manager", ((BaseActivity) mActivity).user);
//        startActivityForResult(intent, TO_EDIT_USER_CODE);
    }

    @Override
    public void clickLeftBtn() {

    }
}
