package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.adapter.OrderManagerActivityFragmentAdapter;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.OrderStatusBean;
import com.tangxb.pay.hero.controller.OrderMangerController;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

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
    OrderMangerController controller;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_manager;
    }

    @Override
    protected void initData() {
        handleSearchTitle();
//        setLeftBtnText(R.string.add_user);
        setLeftBtnTextVisible(false);
        setMiddleText(R.string.order_manger);
        fragmentAdapter = new OrderManagerActivityFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//        if (manager.hasPermission(Permission.P_AddUser)) {
//            findViewById(R.id.add_user).setVisibility(View.VISIBLE);
//        }
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        controller = new OrderMangerController(this);
        loadNeedData();
    }

    /**
     * 加载需要的数据
     */
    private void loadNeedData() {
        addSubscription(controller.getOrderStatusList(), new Consumer<MBaseBean<List<OrderStatusBean>>>() {
            @Override
            public void accept(MBaseBean<List<OrderStatusBean>> baseBean) throws Exception {
                fragmentAdapter.setList(baseBean.getData());
                mTabLayout.setupWithViewPager(mViewPager);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println();
            }
        });
    }

    @Override
    public void clickLeftBtn() {
        Intent intent = getIntentWithPublicParams(RegisterUserActivity.class);
        startActivity(intent);
    }
}
