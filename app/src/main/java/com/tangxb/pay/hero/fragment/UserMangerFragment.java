package com.tangxb.pay.hero.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.activity.BaseActivityWithSearch;
import com.tangxb.pay.hero.activity.EditUserInfoActivity;
import com.tangxb.pay.hero.bean.UserBean;
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

/**
 * 用户列表界面<br>
 * Created by zll on 2018/1/12.
 */
public class UserMangerFragment extends BaseFragment {
    private int page = 1, rows = ConstUtils.PAGE_SIZE;
    private List<UserBean> userList = new ArrayList<>();
    private static final String FRAGMENT_NAME = "fragmentName";
    private static final String ROLE_ID = "currentRoleId";
    private String fragmentName;
    private long currentRoleId;
    private CommonAdapter<UserBean> commonAdapter;
    private static final int TO_EDIT_USER_CODE = 666;

    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerAdapterWithHF mAdapter;
    private String searchKey;

    @Override
    protected int getLayoutResId() {
        return R.layout.customer_manager_fragment_layout;
    }

    public static UserMangerFragment getInstance(String name, long roleId) {
        UserMangerFragment fragment = new UserMangerFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_NAME, name);
        args.putLong(ROLE_ID, roleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void receiveBundleFromActivity(Bundle arg) {
        fragmentName = arg.getString(FRAGMENT_NAME);
        currentRoleId = arg.getLong(ROLE_ID);
    }

    @Override
    protected void initData() {
        setNeedOnCreateRegister();
        // 获取Activity界面里面的搜索关键字
        if (mActivity instanceof BaseActivityWithSearch) {
            String sk = ((BaseActivityWithSearch) mActivity).getSearchKeyword();
            searchKey = TextUtils.isEmpty(sk) ? null : sk;
        }
        for (int i = 0; i < 2; i++) {
            UserBean userBean = new UserBean();
            userBean.setNickname("Nickname-" + i);
            userList.add(userBean);
        }

        commonAdapter = new CommonAdapter<UserBean>(mActivity, R.layout.customer_manager_adapter_item, userList) {
            @Override
            protected void convert(ViewHolder viewHolder, UserBean item, int position) {
                viewHolder.setText(R.id.tv_nickname, item.getNickname());

            }
        };
        mAdapter = new RecyclerAdapterWithHF((MultiItemTypeAdapter) commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                handleItemClick(position, userList.get(position));
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
                getDataByLoadmore();
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
        userList.clear();
        for (int i = 0; i < 20; i++) {
            UserBean userBean = new UserBean();
            userBean.setNickname("Nickname-new-" + i);
            userList.add(userBean);
        }
        mAdapter.notifyDataSetChangedHF();
        ptrClassicFrameLayout.refreshComplete();
    }

    /**
     * 上拉加载更多获取数据
     */
    private void getDataByLoadmore() {
        // 开始网络请求
        page++;
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
        startActivityForResult(intent, TO_EDIT_USER_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TO_EDIT_USER_CODE && resultCode == Activity.RESULT_OK) {
            int realPosition = data.getIntExtra("realPosition", -1);
            UserBean userBean = data.getParcelableExtra("passUser");
            copyNeedInfo(realPosition, userBean);
            commonAdapter.notifyDataSetChanged();
        }
    }

    public void copyNeedInfo(int realPosition, UserBean userBean) {
        if (realPosition != -1 && userBean != null) {
            UserBean bean = userList.get(realPosition);
            bean.setId(userBean.getId());
            bean.setMobile(userBean.getMobile());
            bean.setRole_name(userBean.getRole_name());
//            bean.setCity(userBean.getCity());
//            bean.setAddress(userBean.getAddress());
//            bean.setEnable(userBean.isEnable());
//            bean.setNickname(userBean.getNickname());
//            bean.setAddress_id(userBean.getAddress_id());
//            bean.setParent_id(userBean.getParent_id());
//            bean.setParent_name(userBean.getParent_name());
//            bean.setPercent_price(userBean.getPercent_price());
//            bean.setFreight(userBean.getFreight());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 重置数据
        searchKey = null;
        page = 1;
        userList.clear();
    }


}
