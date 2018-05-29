package com.tangxb.pay.hero.fragment;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.UserBean;
import com.tangxb.pay.hero.controller.UserMangerFragmentController;
import com.tangxb.pay.hero.util.ConstUtils;
import com.tangxb.pay.hero.util.ToastUtils;

import java.util.ArrayList;
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
    UserMangerFragmentController controller;
    int yearStart, monthStart, dayOfMonthStart;
    int yearEnd, monthEnd, dayOfMonthEnd;

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
        controller = new UserMangerFragmentController((BaseActivity) mActivity);

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
     * 获取业务员数据
     */
    private void getNeedSalesManData() {
        addSubscription(controller.getUserListByRoleId(1, 500, ConstUtils.RB_600.getId(), null, 1), new Consumer<MBaseBean<List<UserBean>>>() {
            @Override
            public void accept(MBaseBean<List<UserBean>> baseBean) throws Exception {
                if (baseBean.getData() == null) return;
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

    @OnClick(R.id.tv_start_time)
    public void startTimeClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yearStart = year;
                monthStart = month;
                dayOfMonthStart = dayOfMonth;
            }
        }, yearStart, monthStart, dayOfMonthStart);
        datePickerDialog.show();
    }

    @OnClick(R.id.tv_end_time)
    public void endTimeClick(View view) {

    }
}
