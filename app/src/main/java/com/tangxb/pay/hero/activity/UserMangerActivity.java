package com.tangxb.pay.hero.activity;

import com.tangxb.pay.hero.R;

/**
 * 用户管理界面<br>
 * Created by tangxuebing on 2018/5/14.
 */

public class UserMangerActivity extends BaseActivityWithSearch {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_manger;
    }

    @Override
    protected void initData() {
        handleSearchTitle();
        setLeftBtnText(R.string.add_user);
        setMiddleText(R.string.user_manger);
    }

    @Override
    public void clickLeftBtn() {

    }
}
