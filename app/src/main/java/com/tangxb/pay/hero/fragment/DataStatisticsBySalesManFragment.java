package com.tangxb.pay.hero.fragment;

import android.app.DatePickerDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.UserBean;
import com.tangxb.pay.hero.controller.DataStatisticsBySalesManFragmentController;
import com.tangxb.pay.hero.controller.UserMangerFragmentController;
import com.tangxb.pay.hero.decoration.MDividerItemDecoration;
import com.tangxb.pay.hero.util.ConstUtils;
import com.tangxb.pay.hero.util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
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
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;

    DataStatisticsBySalesManFragmentController controller;
    UserMangerFragmentController userMangerFragmentController;
    int yearStart, monthStart, dayOfMonthStart;
    int yearEnd, monthEnd, dayOfMonthEnd;
    int yearCurrent, monthCurrent, dayOfMonthCurrent;
    /**
     * 业务员集合
     */
    List<UserBean> userBeanList = new ArrayList<>();
    List<String> dataList = new ArrayList<>();
    private RecyclerAdapterWithHF mAdapter;

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
        userMangerFragmentController = new UserMangerFragmentController((BaseActivity) mActivity);
        getCurrentDate();
        String str = yearStart + "年" + (monthStart + 1) + "月" + dayOfMonthStart + "日";
        mStartTimeTv.setText(str);
        String str2 = yearEnd + "年" + (monthEnd + 1) + "月" + dayOfMonthEnd + "日";
        mEndTimeTv.setText(str2);

        CommonAdapter commonAdapter = new CommonAdapter<String>(mActivity, R.layout.customer_manager_adapter_item, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

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
                ToastUtils.t(mApplication, "position===" + position);
            }
        });
        getNeedSalesManData();
    }

    /**
     * 点击item
     *
     * @param position
     */
    private void handleItemClick(int position) {

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
     * 获取业务员数据
     */
    private void getNeedSalesManData() {
        addSubscription(userMangerFragmentController.getUserListByRoleId(1, 500, ConstUtils.RB_600.getId(), null, 1), new Consumer<MBaseBean<List<UserBean>>>() {
            @Override
            public void accept(MBaseBean<List<UserBean>> baseBean) throws Exception {
                if (baseBean.getData() == null) return;
                userBeanList.addAll(baseBean.getData());
                List<String> items = new ArrayList<>();
                for (UserBean bean : baseBean.getData()) {
                    items.add(bean.getRealName());
                }
                materialSpinner.setAdapter(new MaterialSpinnerAdapter<>(mActivity, items));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println();
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
}