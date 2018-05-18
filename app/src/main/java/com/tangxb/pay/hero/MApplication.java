package com.tangxb.pay.hero;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.bean.UserLoginResultBean;
import com.tangxb.pay.hero.imageloader.GlideLoaderFactory;
import com.tangxb.pay.hero.imageloader.ImageLoaderFactory;
import com.tangxb.pay.hero.okhttp.OkHttpUtils;
import com.tangxb.pay.hero.util.ConstUtils;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author ChayChan
 * @description: TODO
 * @date 2017/11/17  21:36
 */

public class MApplication extends TinkerApplication {
    private RefWatcher mRefWatcher;
    private ImageLoaderFactory imageLoaderFactory;
    private UserLoginResultBean mUserLoginResultBean;
    protected Stack<WeakReference<BaseActivity>> mActivityStack;
    private String token;
    private long currentLoginUserId;
    private long currentLoginRoleId;
    private OSSClient ossClient;

    public UserLoginResultBean getUserLoginResultBean() {
        return mUserLoginResultBean;
    }

    public void setUserLoginResultBean(UserLoginResultBean mUserLoginResultBean) {
        this.mUserLoginResultBean = mUserLoginResultBean;
        currentLoginUserId = mUserLoginResultBean == null ? 0 : mUserLoginResultBean.getUser().getId();
        currentLoginRoleId = mUserLoginResultBean == null ? 0 : mUserLoginResultBean.getUser().getRoleId();
    }

    public OSSClient getOssClient() {
        return ossClient;
    }

    /**
     * 获取当前登录用户的用户id
     *
     * @return
     */
    public long getUserId() {
        return currentLoginUserId;
    }

    /**
     * 获取当前登录用户的角色id
     *
     * @return
     */
    public long getRoleId() {
        return currentLoginRoleId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RefWatcher getmRefWatcher() {
        return mRefWatcher;
    }

    public MApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, MyApplicationLike.class.getCanonicalName(),
                "com.tencent.tinker.loader.TinkerLoader", false);

        init();
    }

    private void init() {
        initImageLoaderFactory();
        initActivityStack();
        initOkHttpUtils();
        initUMeng();
        initImageLoaderFactory();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    /**
     * 注意请不要使用Application
     *
     * @param context
     */
    public void initOSS(Context context) {
        synchronized (this) {
            if (ossClient == null) {
                OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(ConstUtils.ACCESS_ID, ConstUtils.ACCESS_KEY);
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                conf.setMaxConcurrentRequest(8); // 最大并发请求数，默认5个
                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                ossClient = new OSSClient(context, ConstUtils.OSS_ENDPOINT, credentialProvider, conf);
            }
        }
    }

    private void initOkHttpUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10 * 1000L, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000L, TimeUnit.MILLISECONDS)
                .writeTimeout(60 * 1000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private void initUMeng() {
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setCatchUncaughtExceptions(true);
    }

    private void initImageLoaderFactory() {
        imageLoaderFactory = new GlideLoaderFactory();
    }

    public ImageLoaderFactory getImageLoaderFactory() {
        return imageLoaderFactory;
    }

    public void initRefWatcher(Application application) {
        mRefWatcher = LeakCanary.install(application);
    }

    public RefWatcher getRefWatcher(Context context) {
        MApplication application = (MApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    private void initActivityStack() {
        mActivityStack = new Stack<>();
    }

    public void registerEventBus(Object object) {
        try {
            EventBus.getDefault().register(object);
        } catch (Exception e) {
        }
    }

    public void unregisterEventBus(Object object) {
        try {
            EventBus.getDefault().unregister(object);
        } catch (Exception e) {
        }
    }

    /**
     * 添加Activity到ActivityStack里面
     *
     * @param activity
     */
    public void pushActivity(BaseActivity activity) {
        mActivityStack.push(new WeakReference<>(activity));
    }

    public void removeActivity(BaseActivity baseActivity) {
        WeakReference<BaseActivity> weak = null;
        for (WeakReference<BaseActivity> reference : mActivityStack) {
            if (reference != null && reference.get() != null) {
                if (reference.get().getClass().getName().equals(baseActivity.getClass().getName())) {
                    weak = reference;
                    break;
                }
            }
        }
        if (weak != null) {
            mActivityStack.remove(weak);
        }
    }
}
