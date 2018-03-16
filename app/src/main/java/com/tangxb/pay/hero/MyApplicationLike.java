package com.tangxb.pay.hero;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.bean.UserLoginResultBean;
import com.tangxb.pay.hero.imageloader.GlideLoaderFactory;
import com.tangxb.pay.hero.imageloader.ImageLoaderFactory;
import com.tangxb.pay.hero.okhttp.CacheUtils;
import com.tangxb.pay.hero.okhttp.OkHttpUtils;
import com.tangxb.pay.hero.util.NetworkUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author ChayChan
 * @description: TODO
 * @date 2017/11/17  21:41
 */

public class MyApplicationLike extends DefaultApplicationLike {

    public MyApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(getApplication(), "5659c6166f", true);

        initDebugSth();
        init();
    }

    public void initDebugSth() {
        if (getApplication() instanceof MDebugApplication) {
            Stetho.initializeWithDefaults(getApplication());
        }
    }

    private void init() {
        NetworkUtils.setContext(getApplication());
        CacheUtils.setContext(getApplication());
        initRefWatcher();
    }

    private void initRefWatcher() {
        ((MApplication)getApplication()).initRefWatcher(getApplication());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

}
