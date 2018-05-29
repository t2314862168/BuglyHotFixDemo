package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.DeliverPersonOrderBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.controller.DispatchGoodsMangerController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.MDialogUtils;
import com.tangxb.pay.hero.util.ToastUtils;
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
 * 配送人订单列表界面<br>
 * Created by tangxuebing on 2018/5/23.
 */

public class DispatchPersonOrderListActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_head)
    LinearLayout mHeadLL;
    @BindView(R.id.btn_item)
    Button mItemBtn1;

    List<DeliverPersonOrderBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    DispatchGoodsMangerController controller;
    long orderId;
    int status;
    boolean canOperate;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_dispatch_person_order_list;
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        orderId = intent.getLongExtra("orderId", 0);
        status = intent.getIntExtra("status", 0);
        if (status == 1) {
            canOperate = true;
        }
    }

    @Override
    protected void initData() {
        handleTitle();
        mItemBtn1.setText("完成配送");
        if (!canOperate) {
            findView(R.id.ll_bottom).setVisibility(View.GONE);
        }
        setMiddleText("开始配送");
        // 输入法不改变布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        controller = new DispatchGoodsMangerController(this);
        TypedArray typedArray = mActivity.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        final Drawable divider = typedArray.getDrawable(0);
        typedArray.recycle();
        mHeadLL.setDividerDrawable(divider);
        CommonAdapter commonAdapter = new CommonAdapter<DeliverPersonOrderBean>(mActivity, R.layout.item_dispatch_person_order_list, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, DeliverPersonOrderBean item, final int position) {
                LinearLayout itemLL = viewHolder.getView(R.id.ll_item);
                itemLL.setDividerDrawable(divider);
                ImageView imageView = viewHolder.getView(R.id.iv_network);
                mApplication.getImageLoaderFactory().loadCommonImgByUrl(mActivity, item.getProduct_image(), imageView);
                viewHolder.setText(R.id.tv_name, item.getProduct_name());
                viewHolder.setText(R.id.tv_buy_num, item.getSend_num() + item.getProduct_unit());
                if (item.getReceive_num() == null) {
                    viewHolder.setText(R.id.tv_storage_num, "");
                } else {
                    viewHolder.setText(R.id.tv_storage_num, item.getReceive_num() + item.getProduct_unit());
                }
                viewHolder.setOnClickListener(R.id.tv_storage_num, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleItemClick(position);
                    }
                });
            }
        };
        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        getNeedData();
    }


    /**
     * 网络获取数据
     */
    private void getNeedData() {
        addSubscription(controller.getReceiveOrderInfo(orderId), new Consumer<MBaseBean<List<DeliverPersonOrderBean>>>() {
            @Override
            public void accept(MBaseBean<List<DeliverPersonOrderBean>> baseBean) throws Exception {
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
    private void handleItemClick(final int position) {
        if (!canOperate) return;
        int orginalNum = 0;
        if (dataList.get(position).getReceive_num() != null) {
            orginalNum = dataList.get(position).getReceive_num().intValue();
        }
        int minNum = 0;
        int maxNum = dataList.get(position).getSend_num();
        MDialogUtils.showNumberDialog(mActivity, "实际收到数量", orginalNum, minNum, maxNum, true, new MDialogUtils.OnSureListener() {
            @Override
            public void onSure(int number, int offset, AlertDialog dialog) {
                dialog.dismiss();
                dataList.get(position).setReceive_num(number);
                mAdapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!canOperate) {
            finish();
            return;
        }
        MDialogUtils.showMessage(mActivity, "保存配送信息", "确定", "取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item11Click(null);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 配送完成
     *
     * @param view
     */
    @OnClick(R.id.btn_item)
    public void item11Click(View view) {
        if (!canOperate) return;
        boolean flag = false;
        for (DeliverPersonOrderBean bean : dataList) {
            if (bean.getReceive_num() == null) {
                flag = true;
                break;
            }
        }
        if (flag) {
            ToastUtils.t(mApplication, "请填写所有实际收到的数量,然后再提交数据");
            return;
        }
        boolean needLog = false;
        // 如果收货数量不等于发货数量需要说明
        for (DeliverPersonOrderBean bean : dataList) {
            if (bean.getReceive_num() != bean.getSend_num()) {
                needLog = true;
                break;
            }
        }
        if (needLog) {
            showLogDialog();
        } else {
            doOperate(null);
        }
    }

    /**
     * 上传数据
     *
     * @param log
     */
    private void doOperate(String log) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (DeliverPersonOrderBean bean : dataList) {
                JSONObject json = new JSONObject();
                json.put("product_id", bean.getProduct_id());
                json.put("num", bean.getReceive_num());
                jsonArray.put(json);
            }
        } catch (JSONException e) {
        }
        showAlertDialog();
        addSubscription(controller.confirmReceive(orderId, jsonArray.toString(), log), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> baseBean) throws Exception {
                ToastUtils.t(mApplication, baseBean.getMessage());
                hideAlertDialog();
                finish();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.t(mApplication, throwable.getMessage());
                hideAlertDialog();
            }
        });
    }

    /**
     * 弹出输入说明弹窗
     */
    private void showLogDialog() {
        MDialogUtils.showEditTextDialog(mActivity, "请输入说明", "", new MDialogUtils.OnSureTextListener() {
            @Override
            public void onSure(String text, AlertDialog dialog) {
                if (TextUtils.isEmpty(text.trim())) {
                    ToastUtils.t(mApplication, "请输入有效说明");
                    return;
                }
                dialog.dismiss();
                doOperate(text);
            }
        });
    }
}
