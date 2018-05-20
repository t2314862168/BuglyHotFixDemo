package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.adapter.OrderManagerActivityFragmentAdapter;
import com.tangxb.pay.hero.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单管理界面<br>
 * Created by tangxuebing on 2018/5/14.
 */

public class OrderManagerActivity extends BaseActivityWithSearch {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    OrderManagerActivityFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_manager;
    }

    @Override
    protected void initData() {
        handleSearchTitle();
        setLeftBtnText(R.string.add_user);
        setMiddleText(R.string.order_manger);
        fragmentAdapter = new OrderManagerActivityFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//        if (manager.hasPermission(Permission.P_AddUser)) {
//            findViewById(R.id.add_user).setVisibility(View.VISIBLE);
//        }
        loadNeedData();
    }

    /**
     * 加载需要的数据
     */
    private void loadNeedData() {
//        if (mApplication.getUserLoginResultBean() == null) return;
        List<OrderBean> roleList = new ArrayList<>();
        OrderBean orderBean = new OrderBean();
        orderBean.setId(100);
        orderBean.setName("待付款");
        OrderBean orderBean2 = new OrderBean();
        orderBean2.setId(200);
        orderBean2.setName("已付款");
        roleList.add(orderBean);
        roleList.add(orderBean2);
        fragmentAdapter.setList(roleList);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void clickLeftBtn() {
        Intent intent = getIntentWithPublicParams(RegisterUserActivity.class);
        startActivity(intent);
    }
}
