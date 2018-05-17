package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.PermissionBean;
import com.tangxb.pay.hero.controller.PermissionMangerDistributeController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class PermissionMangerDistributeActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerAdapterWithHF mAdapter;
    private List<PermissionBean> dataList = new ArrayList<>();

    PermissionMangerDistributeController permissionMangerDistributeController;
    long roleId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_permission_manger;
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        roleId = intent.getLongExtra("currentRoleId", 0);
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.distribute_permission);
        permissionMangerDistributeController = new PermissionMangerDistributeController(this);
        CommonAdapter commonAdapter = new CommonAdapter<PermissionBean>(mActivity, R.layout.item_permission_manger, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, PermissionBean item, int position) {
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
        ptrClassicFrameLayout.setEnabled(false);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandlerEx() {

            @Override
            public void onRefreshBegin(PtrFrameLayoutEx frame) {
                getDataByRefresh();
            }
        });

        // 下拉获取
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
            }
        });
    }


    /**
     * 下拉刷新获取数据
     */
    private void getDataByRefresh() {
        addSubscription(permissionMangerDistributeController.getMyPermissions(roleId), new Consumer<MBaseBean<List<PermissionBean>>>() {
            @Override
            public void accept(MBaseBean<List<PermissionBean>> baseBean) throws Exception {
                dataList.clear();
                if (baseBean.getData() != null) {
                    dataList.addAll(baseBean.getData());
                }
                mAdapter.notifyDataSetChangedHF();
                ptrClassicFrameLayout.refreshComplete();
                test();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mAdapter.notifyDataSetChangedHF();
                ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    public void test() throws JSONException {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id",dataList.get(0).getId());
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id",dataList.get(1).getId());
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);
        String toString = jsonArray.toString();
        addSubscription(permissionMangerDistributeController.updateRolePermissions(roleId, toString)
                , new Consumer<MBaseBean<String>>() {
                    @Override
                    public void accept(MBaseBean<String> baseBean) throws Exception {
                        System.out.println();
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
     * @param bean
     */
    private void handleItemClick(int position, PermissionBean bean) {
        Intent intent = ((BaseActivity) mActivity).getIntentWithPublicParams(EditUserInfoActivity.class);
        int headerViewsCount = mAdapter.getHeadSize();
        int realPosition = position - headerViewsCount;
        intent.putExtra("realPosition", realPosition);
//        intent.putExtra("passUser", userList.get(realPosition));
//        intent.putExtra("manager", ((BaseActivity) mActivity).user);
//        startActivityForResult(intent, TO_EDIT_USER_CODE);
    }
}
