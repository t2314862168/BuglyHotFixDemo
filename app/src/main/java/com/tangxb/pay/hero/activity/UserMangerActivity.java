package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.adapter.UserManagerActivityFragmentAdapter;
import com.tangxb.pay.hero.bean.RoleBean;

import java.util.List;

import butterknife.BindView;

/**
 * 用户管理界面<br>
 * Created by tangxuebing on 2018/5/14.
 */

public class UserMangerActivity extends BaseActivityWithSearch {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    UserManagerActivityFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_manger;
    }

    @Override
    protected void initData() {
        handleSearchTitle();
        setLeftBtnText(R.string.add_user);
        setMiddleText(R.string.user_manger);
        fragmentAdapter = new UserManagerActivityFragmentAdapter(getSupportFragmentManager());
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
        if (mApplication.getUserLoginResultBean() == null) return;
        List<RoleBean> roleList = mApplication.getUserLoginResultBean().getRoleList();
        fragmentAdapter.setList(roleList);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void clickLeftBtn() {
        Intent intent = getIntentWithPublicParams(RegisterUserActivity.class);
        startActivity(intent);
    }
}
