package com.tangxb.pay.hero.fragment;

import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.bean.DataStatisticsBean;
import com.tangxb.pay.hero.bean.DataStatisticsStackBean;
import com.tangxb.pay.hero.bean.GoodsCategoryBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.UserBean;
import com.tangxb.pay.hero.controller.DataStatisticsByTimeFragmentController;
import com.tangxb.pay.hero.controller.GoodsCategoryController;
import com.tangxb.pay.hero.controller.UserMangerFragmentController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.ConstUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 按时间统计界面<br>
 * Created by tangxuebing on 2018/5/29.
 */

public class DataStatisticsByTimeFragment extends BaseFragment {
    @BindView(R.id.tv_choose_year)
    TextView mChooseYearTv;
    @BindView(R.id.spinner_salesman)
    MaterialSpinner mSpinner1;
    @BindView(R.id.spinner_goods)
    MaterialSpinner mSpinner2;
    @BindView(R.id.cb_freight)
    CheckBox mCheckBox1;
    @BindView(R.id.cb_jian_shu)
    CheckBox mCheckBox2;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;

    DataStatisticsByTimeFragmentController controller;
    UserMangerFragmentController userMangerFragmentController;
    GoodsCategoryController categoryController;
    /**
     * 产品分类集合
     */
    List<GoodsCategoryBean> categoryBeanList = new ArrayList<>();
    /**
     * 业务员集合
     */
    List<UserBean> userBeanList = new ArrayList<>();
    List<DataStatisticsBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    int level = 0;
    String id;
    String proxy_id;
    String product_id;
    Stack<DataStatisticsStackBean> mStack = new Stack<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_data_statistics_by_time;
    }

    public static DataStatisticsByTimeFragment getInstance() {
        DataStatisticsByTimeFragment fragment = new DataStatisticsByTimeFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        controller = new DataStatisticsByTimeFragmentController((BaseActivity) mActivity);
        categoryController = new GoodsCategoryController((BaseActivity) mActivity);
        userMangerFragmentController = new UserMangerFragmentController((BaseActivity) mActivity);
        CommonAdapter commonAdapter = new CommonAdapter<DataStatisticsBean>(mActivity, R.layout.item_data_statistics, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, DataStatisticsBean item, int position) {
                viewHolder.setText(R.id.tv_title, item.getTitle());
                String unit = item.getUnit();
                if (TextUtils.isEmpty(unit)) {
                    unit = "元";
                }
                viewHolder.setText(R.id.tv_data, item.getData() + unit);
                ProgressBar progressBar = viewHolder.getView(R.id.pb);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar.setProgress(item.getProgress(), true);
                } else {
                    progressBar.setProgress(item.getProgress());
                }
            }
        };
        mAdapter = new RecyclerAdapterWithHF(commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                handleItemClick(position);
            }
        });
    }

    @Override
    protected void initListener() {
        mSpinner1.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position == 0) {
                    product_id = null;
                } else {
                    product_id = categoryBeanList.get(position - 1).getId() + "";
                }
                getNeedData();
            }
        });
        mSpinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position == 0) {
                    proxy_id = null;
                } else {
                    proxy_id = userBeanList.get(position - 1).getId() + "";
                }
                getNeedData();
            }
        });
        getGoodsCategoryData();
        getNeedSalesManData();
        getNeedData();
    }

    /**
     * 点击item
     *
     * @param position
     */
    private void handleItemClick(int position) {
        if (dataList.get(position).getId() == null) return;
        mStack.push(new DataStatisticsStackBean(level, id));
        level += 1;
        id = dataList.get(position).getId() + "";
        getNeedData();
    }

    /**
     * 返回键判断是否还有level
     *
     * @return
     */
    public boolean hasLevel() {
        if (mStack.size() == 0) return false;
        DataStatisticsStackBean bean = mStack.pop();
        level = bean.getLevel();
        id = bean.getId();
        getNeedData();
        return true;
    }

    /**
     * 获取相关数据
     */
    private void getNeedData() {
        boolean isFright = mCheckBox1.isChecked();
        boolean isUnit = mCheckBox2.isChecked();
        addSubscription(controller.getTimeData(level, id, proxy_id, product_id, isFright, isUnit), new Consumer<MBaseBean<List<DataStatisticsBean>>>() {
            @Override
            public void accept(MBaseBean<List<DataStatisticsBean>> baseBean) throws Exception {
                dataList.clear();
                if (baseBean.getData() != null) {
                    dataList.addAll(baseBean.getData());
                }
                calculateMaxData();
                mAdapter.notifyDataSetChanged();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println();
            }
        });
    }

    /**
     * 计算最大值
     */
    private void calculateMaxData() {
        float maxData = 0f;
        for (DataStatisticsBean bean : dataList) {
            String data = bean.getData();
            try {
                float v = Float.valueOf(data);
                if (v > maxData) {
                    maxData = v;
                }
            } catch (NumberFormatException e) {
            }
        }
        if (maxData == 0f) return;
        for (DataStatisticsBean bean : dataList) {
            String data = bean.getData();
            try {
                float v = Float.valueOf(data);
                int progress = (int) ((v / maxData) * 100);
                bean.setProgress(progress);
            } catch (NumberFormatException e) {
            }
        }
    }

    /**
     * 获取产品分类数据
     */
    private void getGoodsCategoryData() {
        addSubscription(categoryController.getCategoryList(1), new Consumer<MBaseBean<List<GoodsCategoryBean>>>() {
            @Override
            public void accept(MBaseBean<List<GoodsCategoryBean>> baseBean) throws Exception {
                if (baseBean.getData() == null) return;
                categoryBeanList.addAll(baseBean.getData());
                List<String> items = new ArrayList<>();
                items.add("所有产品");
                for (GoodsCategoryBean bean : baseBean.getData()) {
                    items.add(bean.getName());
                }
                mSpinner1.setAdapter(new MaterialSpinnerAdapter<>(mActivity, items));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
            }
        });
    }

    /**
     * 获取业务员数据
     */
    private void getNeedSalesManData() {
        addSubscription(userMangerFragmentController.getUserListByRoleId(1, 500, ConstUtils.RB_600.getId(), null, 1), new Consumer<MBaseBean<List<UserBean>>>() {
            @Override
            public void accept(MBaseBean<List<UserBean>> baseBean) throws Exception {
                if (baseBean.getData() == null) return;
                userBeanList.addAll(baseBean.getData());
                List<String> items = new ArrayList<>();
                items.add("所有业务员");
                for (UserBean bean : baseBean.getData()) {
                    items.add(bean.getRealName());
                }
                mSpinner2.setAdapter(new MaterialSpinnerAdapter<>(mActivity, items));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println();
            }
        });
    }

    @OnClick(R.id.tv_choose_year)
    public void chooseYearClick() {

    }

    @OnCheckedChanged(R.id.cb_freight)
    public void freightOnChecked(boolean checked) {
        if (!checked) {
            getNeedData();
            return;
        }
        if (mCheckBox2.isChecked()) {
            mCheckBox2.setChecked(false);
        }
        getNeedData();
    }

    @OnCheckedChanged(R.id.cb_jian_shu)
    public void jianShuOnChecked(boolean checked) {
        if (!checked) {
            getNeedData();
            return;
        }
        if (mCheckBox1.isChecked()) {
            mCheckBox1.setChecked(false);
        }
        getNeedData();
    }
}
