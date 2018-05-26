package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    long userId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_dispatch_person_order_list;
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        userId = intent.getLongExtra("userId", 0);
    }

    @Override
    protected void initData() {
        handleTitle();
        mItemBtn1.setText("完成配送");
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
                viewHolder.setText(R.id.tv_buy_num, item.getBuy_num() + item.getProduct_unit());
                viewHolder.setText(R.id.tv_storage_num, item.getGive_num() + item.getProduct_unit());
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
        addSubscription(controller.getUserOrderInfo(userId), new Consumer<MBaseBean<List<DeliverPersonOrderBean>>>() {
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
        int orginalNum = dataList.get(position).getBuy_num();
        int minNum = 0;
        int maxNum = dataList.get(position).getBuy_num();
        MDialogUtils.showNumberDialog(mActivity, "实际配送数量", orginalNum, minNum, maxNum, true, new MDialogUtils.OnSureListener() {
            @Override
            public void onSure(int number, int offset, AlertDialog dialog) {
                dialog.dismiss();
                dataList.get(position).setGive_num(number);
                mAdapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    public void onBackPressed() {
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
        JSONArray jsonArray = new JSONArray();
        try {
            for (DeliverPersonOrderBean bean : dataList) {
                JSONObject json = new JSONObject();
                json.put("product_id", bean.getProductId());
                json.put("buy_num", bean.getBuy_num());
                json.put("deliver_num", bean.getGive_num());
                jsonArray.put(json);
            }
        } catch (JSONException e) {
        }
        showAlertDialog();
        addSubscription(controller.deliverOk(userId, jsonArray.toString()), new Consumer<MBaseBean<String>>() {
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
}
