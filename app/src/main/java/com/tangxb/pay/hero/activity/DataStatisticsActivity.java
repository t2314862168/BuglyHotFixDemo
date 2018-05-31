package com.tangxb.pay.hero.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.fragment.DataStatisticsByBusinessFragment;
import com.tangxb.pay.hero.fragment.DataStatisticsByTimeFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 数据统计界面<br>
 * Created by zll on 2018/5/26.
 */

public class DataStatisticsActivity extends BaseActivity {
    @BindView(R.id.fl_container)
    FrameLayout mContainerFl;
    @BindView(R.id.btn_item_1)
    Button mItemBtn1;
    @BindView(R.id.btn_item_2)
    Button mItemBtn2;

    FragmentManager fragmentManager;
    DataStatisticsByTimeFragment timeFragment;
    DataStatisticsByBusinessFragment businessFragment;
    String timeFragmentTag = DataStatisticsByTimeFragment.class.getSimpleName();
    String businessFragmentTag = DataStatisticsByBusinessFragment.class.getSimpleName();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_data_statistics;
    }

    @Override
    protected void initData() {
//        handleTitle();
//        setMiddleText(R.string.data_statistics);

        mItemBtn1.setText("按时间统计");
        mItemBtn2.setText("按业务统计");

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(timeFragmentTag);
        Fragment fragmentByTag2 = fragmentManager.findFragmentByTag(businessFragmentTag);
        if (fragmentByTag2 != null) {
            businessFragment = (DataStatisticsByBusinessFragment) fragmentByTag2;
            transaction.remove(businessFragment);
        }
        if (fragmentByTag == null) {
            timeFragment = DataStatisticsByTimeFragment.getInstance();
            transaction.add(R.id.fl_container, timeFragment, timeFragmentTag);
        } else {
            timeFragment = (DataStatisticsByTimeFragment) fragmentByTag;
        }
        transaction.show(timeFragment);
        transaction.commit();
    }

    @OnClick(R.id.btn_item_1)
    public void itemBtn1Click(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(timeFragmentTag);
        if (fragmentByTag == null) {
            timeFragment = DataStatisticsByTimeFragment.getInstance();
            transaction.add(R.id.fl_container, timeFragment, timeFragmentTag);
        } else {
            timeFragment = (DataStatisticsByTimeFragment) fragmentByTag;
        }
        if (businessFragment != null) {
            transaction.hide(businessFragment);
        }
        transaction.show(timeFragment);
        transaction.commit();
    }

    @OnClick(R.id.btn_item_2)
    public void itemBtn2Click(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(businessFragmentTag);
        if (fragmentByTag == null) {
            businessFragment = DataStatisticsByBusinessFragment.getInstance();
            transaction.add(R.id.fl_container, businessFragment, businessFragmentTag);
        } else {
            businessFragment = (DataStatisticsByBusinessFragment) fragmentByTag;
        }
        if (timeFragment != null) {
            transaction.hide(timeFragment);
        }
        transaction.show(businessFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (timeFragment.isVisible()) {
            if (timeFragment.hasLevel()) {
                return;
            }
        } else {
            if (businessFragment.hasLevel()) {
                return;
            }
        }
        super.onBackPressed();
    }
}
