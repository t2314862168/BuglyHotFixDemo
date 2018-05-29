package com.tangxb.pay.hero.fragment;

import android.view.View;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tangxb.pay.hero.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 按时间统计界面<br>
 * Created by tangxuebing on 2018/5/29.
 */

public class DataStatisticsByTimeFragment extends BaseFragment {
    @BindView(R.id.tv_start_time)
    TextView mStartTimeTv;
    @BindView(R.id.tv_end_time)
    TextView mEndTimeTv;
    @BindView(R.id.spinner)
    MaterialSpinner materialSpinner;

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

    }

    @OnClick(R.id.tv_start_time)
    public void startTimeClick(View view) {

    }

    @OnClick(R.id.tv_end_time)
    public void endTimeClick(View view) {

    }
}
