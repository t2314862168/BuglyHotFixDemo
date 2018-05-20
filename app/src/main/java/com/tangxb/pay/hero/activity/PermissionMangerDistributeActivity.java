package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.PermissionBean;
import com.tangxb.pay.hero.controller.PermissionMangerDistributeController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.ToastUtils;
import com.tangxb.pay.hero.view.AlertProgressDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class PermissionMangerDistributeActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    /**
     * 全选按钮
     */
    @BindView(R.id.iv_check_all)
    ImageView mCheckAllIv;
    /**
     * 全选文字
     */
    @BindView(R.id.tv_check_tip)
    TextView mCheckAllTv;
    private RecyclerAdapterWithHF mAdapter;
    private List<PermissionBean> dataList = new ArrayList<>();
    AlertDialog mAlertDialog;
    PermissionMangerDistributeController permissionMangerDistributeController;
    long roleId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_permission_manger_distribute;
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
        CommonAdapter commonAdapter = new CommonAdapter<PermissionBean>(mActivity, R.layout.item_permission_manger_distribute, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, PermissionBean item, int position) {
                viewHolder.setText(R.id.tv_name, item.getName());
                int resId = item.getCheck().equals("true") ? R.drawable.ic_check_box_red_400_24dp : R.drawable.ic_check_box_outline_blank_grey_700_24dp;
                viewHolder.setImageResource(R.id.iv_check, resId);
            }
        };
        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                clickItem(position);
            }
        });
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
                changeCheckAllStatus();
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
        jsonObject1.put("id", dataList.get(0).getId());
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id", dataList.get(1).getId());

    }


    /**
     * 点击item
     *
     * @param position
     */
    public void clickItem(int position) {
        if (dataList.get(position).getCheck().equals("true")) {
            dataList.get(position).setCheck("false");
        } else {
            dataList.get(position).setCheck("true");
        }
        mAdapter.notifyItemChanged(position);
        changeCheckAllStatus();
    }

    /**
     * 点击全选
     *
     * @param view
     */
    @OnClick(R.id.iv_check_all)
    public void clickChooseAll(View view) {
        // 默认选中了全部
        boolean hasCheckAll = true;
        for (PermissionBean bean : dataList) {
            if (!bean.getCheck().equals("true")) {
                hasCheckAll = false;
                break;
            }
        }
        hasCheckAll = !hasCheckAll;
        int resId = hasCheckAll ? R.drawable.ic_check_box_red_400_24dp : R.drawable.ic_check_box_outline_blank_grey_700_24dp;
        mCheckAllIv.setImageResource(resId);
        mCheckAllTv.setText(hasCheckAll ? "全不选" : "全选");

        for (PermissionBean bean : dataList) {
            bean.setCheck(hasCheckAll ? "true" : "false");
        }
        mAdapter.notifyDataSetChangedHF();
    }

    /**
     * 改变全选状态
     *
     * @return
     */
    private void changeCheckAllStatus() {
        boolean hasCheckAll = true;
        for (PermissionBean bean : dataList) {
            if (!bean.getCheck().equals("true")) {
                hasCheckAll = false;
                break;
            }
        }
        int resId = hasCheckAll ? R.drawable.ic_check_box_red_400_24dp : R.drawable.ic_check_box_outline_blank_grey_700_24dp;
        mCheckAllIv.setImageResource(resId);
        mCheckAllTv.setText(hasCheckAll ? "全不选" : "全选");
    }

    /**
     * 显示进度框
     */
    private void showAlertDialog() {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertProgressDialog.Builder(mActivity)
                    .setView(R.layout.layout_alert_dialog)
                    .setCancelable(false)
                    .setMessage(R.string.commit_data_ing)
                    .show();
        }
        mAlertDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void hideAlertDialog(boolean success, String msg) {
        ToastUtils.t(mApplication, msg);
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
        if (success) {
            finish();
        }
    }

    /**
     * 点击提交
     *
     * @param view
     */
    @OnClick(R.id.tv_commit)
    public void clickCommit(View view) {
        JSONArray jsonArray = new JSONArray();
        JSONObject object;
        try {
            for (PermissionBean bean : dataList) {
                if (bean.getCheck().equals("true")) {
                    object = new JSONObject();
                    object.put("id", bean.getId());
                    jsonArray.put(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String toString = jsonArray.toString();
        showAlertDialog();
        addSubscription(permissionMangerDistributeController.updateRolePermissions(roleId, toString)
                , new Consumer<MBaseBean<String>>() {
                    @Override
                    public void accept(MBaseBean<String> baseBean) throws Exception {
                        hideAlertDialog(true, baseBean.getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        hideAlertDialog(false, throwable.getMessage());
                    }
                });
    }

    /**
     * 点击取消
     *
     * @param view
     */
    @OnClick(R.id.tv_cancel)
    public void clickCancel(View view) {
        finish();
    }
}
