package com.tangxb.pay.hero.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.fragment.DataStatisticsByCategoryFragment;
import com.tangxb.pay.hero.fragment.DataStatisticsBySalesManFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 数据统计界面<br>
 * Created by zll on 2018/5/26.
 */

public class DataStatisticsActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.fl_container)
    FrameLayout mContainerFl;
    @BindView(R.id.btn_item_1)
    Button mItemBtn1;
    @BindView(R.id.btn_item_2)
    Button mItemBtn2;

    FragmentManager fragmentManager;
    DataStatisticsByCategoryFragment timeFragment;
    DataStatisticsBySalesManFragment salesManFragment;
    String timeFragmentTag = DataStatisticsByCategoryFragment.class.getSimpleName();
    String salesManFragmentTag = DataStatisticsBySalesManFragment.class.getSimpleName();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_data_statistics;
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.data_statistics);

        mItemBtn1.setText("按产品统计");
        mItemBtn2.setText("按业务统计");

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(timeFragmentTag);
        Fragment fragmentByTag2 = fragmentManager.findFragmentByTag(salesManFragmentTag);
        if (fragmentByTag2 != null) {
            salesManFragment = (DataStatisticsBySalesManFragment) fragmentByTag2;
            transaction.remove(salesManFragment);
        }
        if (fragmentByTag == null) {
            timeFragment = DataStatisticsByCategoryFragment.getInstance();
            transaction.add(R.id.fl_container, timeFragment, timeFragmentTag);
        } else {
            timeFragment = (DataStatisticsByCategoryFragment) fragmentByTag;
        }
        transaction.show(timeFragment);
        transaction.commit();
    }

    @OnClick(R.id.btn_item_1)
    public void itemBtn1Click(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(timeFragmentTag);
        if (fragmentByTag == null) {
            timeFragment = DataStatisticsByCategoryFragment.getInstance();
            transaction.add(R.id.fl_container, timeFragment, timeFragmentTag);
        } else {
            timeFragment = (DataStatisticsByCategoryFragment) fragmentByTag;
        }
        if (salesManFragment != null) {
            transaction.hide(salesManFragment);
        }
        transaction.show(timeFragment);
        transaction.commit();
    }

    @OnClick(R.id.btn_item_2)
    public void itemBtn2Click(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(salesManFragmentTag);
        if (fragmentByTag == null) {
            salesManFragment = DataStatisticsBySalesManFragment.getInstance();
            transaction.add(R.id.fl_container, salesManFragment, salesManFragmentTag);
        } else {
            salesManFragment = (DataStatisticsBySalesManFragment) fragmentByTag;
        }
        if (timeFragment != null) {
            transaction.hide(timeFragment);
        }
        transaction.show(salesManFragment);
        transaction.commit();
    }
}
