package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.MApplication;
import com.tangxb.pay.hero.activity.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * Created by tangxuebing on 2018/5/16.
 */

public class BaseControllerWithActivity {
    protected WeakReference<BaseActivity> baseActivity;
    protected MApplication mApplication;

    public BaseControllerWithActivity(BaseActivity baseActivity) {
        this.baseActivity = new WeakReference<>(baseActivity);
        this.mApplication = (MApplication) baseActivity.getApplication();
    }
}
