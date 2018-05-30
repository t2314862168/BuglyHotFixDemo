package com.tangxb.pay.hero.fragment;

import android.app.DatePickerDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import com.tangxb.pay.hero.controller.DataStatisticsBySalesManFragmentController;
import com.tangxb.pay.hero.controller.GoodsCategoryController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 按业务员统计界面<br>
 * Created by tangxuebing on 2018/5/29.
 */

public class DataStatisticsBySalesManFragment extends BaseFragment {
    @BindView(R.id.tv_start_time)
    TextView mStartTimeTv;
    @BindView(R.id.tv_end_time)
    TextView mEndTimeTv;
    @BindView(R.id.spinner)
    MaterialSpinner materialSpinner;
    @BindView(R.id.cb_freight)
    CheckBox mCheckBox1;
    @BindView(R.id.cb_jian_shu)
    CheckBox mCheckBox2;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;

    DataStatisticsBySalesManFragmentController controller;
    int yearStart, monthStart, dayOfMonthStart;
    int yearEnd, monthEnd, dayOfMonthEnd;
    int yearCurrent, monthCurrent, dayOfMonthCurrent;
    GoodsCategoryController categoryController;
    /**
     * 产品分类集合
     */
    List<GoodsCategoryBean> categoryBeanList = new ArrayList<>();
    List<DataStatisticsBean> dataList = new ArrayList<>();
    RecyclerAdapterWithHF mAdapter;
    int level = 0;
    String id;
    String product_id;
    float maxData;
    Stack<DataStatisticsStackBean> mStack = new Stack<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_data_statistics_by_salesman;
    }

    public static DataStatisticsBySalesManFragment getInstance() {
        DataStatisticsBySalesManFragment fragment = new DataStatisticsBySalesManFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        controller = new DataStatisticsBySalesManFragmentController((BaseActivity) mActivity);
        categoryController = new GoodsCategoryController((BaseActivity) mActivity);
        getCurrentDate();
        String str = yearStart + "年" + (monthStart + 1) + "月" + dayOfMonthStart + "日";
        mStartTimeTv.setText(str);
        String str2 = yearEnd + "年" + (monthEnd + 1) + "月" + dayOfMonthEnd + "日";
        mEndTimeTv.setText(str2);

        CommonAdapter commonAdapter = new CommonAdapter<DataStatisticsBean>(mActivity, R.layout.item_data_statistics, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, DataStatisticsBean item, int position) {
                viewHolder.setText(R.id.tv_title, item.getTitle());
                viewHolder.setText(R.id.tv_data, item.getData());
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
        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
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
        getGoodsCategoryData();
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
     * 获取当前日期
     */
    private void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        yearCurrent = calendar.get(Calendar.YEAR);
        monthCurrent = calendar.get(Calendar.MONTH);
        dayOfMonthCurrent = calendar.get(Calendar.DAY_OF_MONTH);
        yearStart = yearEnd = yearCurrent;
        monthStart = monthEnd = monthCurrent;
        dayOfMonthStart = dayOfMonthEnd = dayOfMonthCurrent;
    }

    /**
     * 获取相关数据
     */
    private void getNeedData() {
        String startTime = yearStart + "-" + (monthStart + 1) + "-" + dayOfMonthStart;
        String endTime = yearEnd + "-" + (monthEnd + 1) + "-" + dayOfMonthEnd;
        boolean isFright = mCheckBox1.isChecked();
        boolean isUnit = mCheckBox2.isChecked();
        addSubscription(controller.getBusinessData(level, id, product_id, startTime, endTime, isFright, isUnit), new Consumer<MBaseBean<List<DataStatisticsBean>>>() {
            @Override
            public void accept(MBaseBean<List<DataStatisticsBean>> baseBean) throws Exception {
                dataList.clear();
                if (baseBean.getData() != null) {
                    dataList.addAll(baseBean.getData());
                }
                cacuclateMaxData();
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
    private void cacuclateMaxData() {
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
                materialSpinner.setAdapter(new MaterialSpinnerAdapter<>(mActivity, items));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
            }
        });
    }

    /**
     * 检查开始时间是否合法
     */
    private void checkStartTime(int year, int month, int dayOfMonth) {
        if (!checkStartTimeIsRight(year, month, dayOfMonth)) {
            ToastUtils.t(mApplication, "结束时间不能小于开始时间");
            return;
        }
        yearStart = year;
        monthStart = month;
        dayOfMonthStart = dayOfMonth;
        String str = yearStart + "年" + (monthStart + 1) + "月" + dayOfMonthStart + "日";
        mStartTimeTv.setText(str);
        getNeedData();
    }

    /**
     * 检查结束时间是否合法
     */
    private void checkEndTime(int year, int month, int dayOfMonth) {
        if (!checkEndTimeIsRight(year, month, dayOfMonth)) {
            ToastUtils.t(mApplication, "结束时间不能小于开始时间");
            return;
        }
        yearEnd = year;
        monthEnd = month;
        dayOfMonthEnd = dayOfMonth;
        String str = yearEnd + "年" + (monthEnd + 1) + "月" + dayOfMonthEnd + "日";
        mEndTimeTv.setText(str);
        getNeedData();
    }

    /**
     * 检查开始时间是否合法
     */
    private boolean checkStartTimeIsRight(int year, int month, int dayOfMonth) {
        boolean flag = false;
        if (yearEnd >= year && monthEnd >= month && dayOfMonthEnd >= dayOfMonth) {
            flag = true;
        }
        return flag;
    }

    /**
     * 检查结束时间是否合法
     */
    private boolean checkEndTimeIsRight(int year, int month, int dayOfMonth) {
        boolean flag = false;
        if (year >= yearStart && month >= monthStart && dayOfMonth >= dayOfMonthStart) {
            flag = true;
        }
        return flag;
    }

    @OnClick(R.id.tv_start_time)
    public void startTimeClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                checkStartTime(year, month, dayOfMonth);
            }
        }, yearStart, monthStart, dayOfMonthStart);
        datePickerDialog.show();
    }

    @OnClick(R.id.tv_end_time)
    public void endTimeClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                checkEndTime(year, month, dayOfMonth);
            }
        }, yearEnd, monthEnd, dayOfMonthEnd);
        datePickerDialog.show();
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
