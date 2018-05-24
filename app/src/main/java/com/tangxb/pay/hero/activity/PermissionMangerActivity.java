package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.RoleBean;
import com.tangxb.pay.hero.controller.PermissionMangerController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.ConstUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class PermissionMangerActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerAdapterWithHF mAdapter;
    private List<RoleBean> dataList = new ArrayList<>();

    int page = 1, rows = ConstUtils.PAGE_SIZE;
    PermissionMangerController permissionMangerController;
    long roleId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_permission_manger;
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.permission_manger);
        permissionMangerController = new PermissionMangerController(this);
        try {
            roleId = mApplication.getUserLoginResultBean().getUser().getRoleId();
        } catch (Exception e) {
        }
        try {
            List<RoleBean> roleList = mApplication.getUserLoginResultBean().getRoleList();
            dataList.addAll(roleList);
        } catch (Exception e) {
        }
        CommonAdapter commonAdapter = new CommonAdapter<RoleBean>(mActivity, R.layout.item_permission_manger, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, RoleBean item, int position) {
                viewHolder.setText(R.id.tv_name, item.getName());
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
    }


    /**
     * 下拉刷新获取数据
     */
    private void getDataByRefresh() {
        // 开始网络请求
        page = 1;
        addSubscription(permissionMangerController.getRoleList(), new Consumer<MBaseBean<List<RoleBean>>>() {
            @Override
            public void accept(MBaseBean<List<RoleBean>> baseBean) throws Exception {
                dataList.clear();
                if (baseBean.getData() != null) {
                    dataList.addAll(baseBean.getData());
                }
                mAdapter.notifyDataSetChangedHF();
//                ptrClassicFrameLayout.refreshComplete();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mAdapter.notifyDataSetChangedHF();
//                ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    /**
     * 上拉加载更多获取数据
     */
    private void getDataByLoadMore() {
        // 开始网络请求
        page++;
        addSubscription(permissionMangerController.getRoleList(), new Consumer<MBaseBean<List<RoleBean>>>() {
            @Override
            public void accept(MBaseBean<List<RoleBean>> baseBean) throws Exception {
                dataList.clear();
                if (baseBean.getData() != null) {
                    dataList.addAll(baseBean.getData());
                }
                mAdapter.notifyDataSetChangedHF();
//                ptrClassicFrameLayout.loadMoreComplete(true);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
//                ptrClassicFrameLayout.loadMoreComplete(true);
            }
        });
    }

    /**
     * 点击item
     *
     * @param bean
     */
    private void handleItemClick(int position, RoleBean bean) {
        Intent intent = ((BaseActivity) mActivity).getIntentWithPublicParams(PermissionMangerDistributeActivity.class);
        intent.putExtra("currentRoleId", bean.getId());
        startActivity(intent);
    }
}
