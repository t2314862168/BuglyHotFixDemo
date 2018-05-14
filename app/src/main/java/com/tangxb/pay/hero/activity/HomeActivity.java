package com.tangxb.pay.hero.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.fragment.Tab_0_Fragment;
import com.tangxb.pay.hero.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by tangxuebing on 2018/5/8.
 */

public class HomeActivity extends BaseActivity {
    @BindView(R.id.fragment_container)
    FrameLayout mFrameLayout;
    @BindView(R.id.tv_tab_0)
    TextView mTabTv0;
    @BindView(R.id.tv_tab_1)
    TextView mTabTv1;
    @BindView(R.id.tv_tab_2)
    TextView mTabTv2;

    private FragmentManager supportFragmentManager;
    private Tab_0_Fragment tab_0_fragment;
    private String tag_tab_0_fragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        supportFragmentManager = getSupportFragmentManager();
        tag_tab_0_fragment = Tab_0_Fragment.class.getSimpleName();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (supportFragmentManager.findFragmentByTag(tag_tab_0_fragment) == null) {
            tab_0_fragment = Tab_0_Fragment.getInstance(tag_tab_0_fragment);
            fragmentTransaction.add(R.id.fragment_container, tab_0_fragment, tag_tab_0_fragment);
        } else {
            tab_0_fragment = (Tab_0_Fragment) supportFragmentManager.findFragmentByTag(tag_tab_0_fragment);
        }
        fragmentTransaction.show(tab_0_fragment);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.tv_tab_0)
    public void clickTab0(View view) {

    }

    @OnClick(R.id.tv_tab_1)
    public void clickTab1(View view) {
        ToastUtils.t(mApplication, "暂时还不支持微信收款");
    }

    @OnClick(R.id.tv_tab_2)
    public void clickTab2(View view) {
        ToastUtils.t(mApplication, "待定功能还未确定,敬请期待");
    }
}
