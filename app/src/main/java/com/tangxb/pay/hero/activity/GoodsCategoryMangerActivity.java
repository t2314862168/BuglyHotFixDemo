package com.tangxb.pay.hero.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.GoodsCategoryBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.controller.GoodsCategoryMangerController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.MDialogUtils;
import com.tangxb.pay.hero.util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 分类管理界面<br>
 * Created by zll on 2018/5/19.
 */

public class GoodsCategoryMangerActivity extends BaseActivityWithTitleRight {
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerAdapterWithHF mAdapter;
    private List<GoodsCategoryBean> dataList = new ArrayList<>();
    GoodsCategoryMangerController categoryController;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_category_manger;
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.category);
        setRightText(R.string.increase);
        categoryController = new GoodsCategoryMangerController(this);

        CommonAdapter commonAdapter = new CommonAdapter<GoodsCategoryBean>(mActivity, R.layout.item_goods_category_manager, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, GoodsCategoryBean item, final int position) {
                viewHolder.setText(R.id.tv_name, item.getName());
                viewHolder.setText(R.id.tv_parent_name, item.getName());
                viewHolder.setText(R.id.tv_show, item.getStatus() == 1 ? "显示" : "隐藏");
                viewHolder.setOnClickListener(R.id.tv_show, new View.OnClickListener() {
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
        getDataByRefresh();
    }

    private void getDataByRefresh() {
        addSubscription(categoryController.getCategoryList(), new Consumer<MBaseBean<List<GoodsCategoryBean>>>() {
            @Override
            public void accept(MBaseBean<List<GoodsCategoryBean>> baseBean) throws Exception {
                dataList.clear();
                dataList.addAll(baseBean.getData());
                mAdapter.notifyDataSetChangedHF();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println();
            }
        });
    }

    private void handleItemClick(final int position) {
        long id = dataList.get(position).getId();
        int status = dataList.get(position).getStatus();
        if (status == 0) {
            status = 1;
        } else {
            status = 0;
        }
        addSubscription(categoryController.updateCategoryStatus(id, status), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> baseBean) throws Exception {
                int status = dataList.get(position).getStatus();
                dataList.get(position).setStatus(status == 0 ? 1 : 0);
                mAdapter.notifyItemChangedHF(position);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    @Override
    public void clickRightBtn() {
        MDialogUtils.showEditTextDialog(mActivity, "添加类型", "", new MDialogUtils.OnSureTextListener() {
            @Override
            public void onSure(String text, AlertDialog dialog) {
                dialog.dismiss();
                addCategory(text);
            }
        });
    }

    /**
     * 添加种类
     *
     * @param text
     */
    private void addCategory(String text) {
        addSubscription(categoryController.addCategory(0, text), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> baseBean) throws Exception {
                getDataByRefresh();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.t(mApplication, throwable.getMessage());
            }
        });
    }
}
