package com.tangxb.pay.hero.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.tangxb.pay.hero.MApplication;
import com.tangxb.pay.hero.api.DefaultConsumerThrowable;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tangxb on 2016/12/13.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 在<code>onCreateView</code>的时候注册,需要在<code>onDestroy</code>取消注册
     */
    private boolean isNeedOnCreateRegister = false;
    /**
     * 在<code>OnResume</code>的时候注册,需要在<code>OnPause</code>取消注册
     */
    private boolean isNeedOnResumeRegister = false;
    protected MApplication mApplication;
    protected Activity mActivity;
    protected String mClassName;
    protected Resources mResources;
    private CompositeDisposable mCompositeDisposable;
    private DefaultConsumerThrowable mDefaultConsumerThrowable;

    protected abstract int getLayoutResId();

    /**
     * 请在{@link #initData()}里面调用
     */
    protected void setNeedOnCreateRegister() {
        isNeedOnCreateRegister = true;
    }

    /**
     * 请在{@link #initData()}里面调用
     */
    protected void setNeedOnResumeRegister() {
        isNeedOnResumeRegister = true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        init();
    }

    protected void bindButterKnife() {
        ButterKnife.bind(this);
    }

    protected void init() {
        mActivity = this;
        mApplication = (MApplication) getApplication();
        mClassName = getClass().getSimpleName();
        mResources = getResources();
        bindButterKnife();
        receivePassDataIfNeed(getIntent());
        initData();
        initListener();
        if (isNeedOnCreateRegister) {
            mApplication.registerEventBus(this);
        }
        mDefaultConsumerThrowable = new DefaultConsumerThrowable();
        mApplication.pushActivity(this);
    }

    protected void initData() {

    }

    protected void initListener() {

    }

    protected void receivePassDataIfNeed(Intent intent) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mClassName);
        MobclickAgent.onResume(this);
        if (isNeedOnResumeRegister) {
            mApplication.registerEventBus(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mClassName);
        MobclickAgent.onPause(this);
        if (isNeedOnResumeRegister) {
            mApplication.registerEventBus(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onUnSubscribe();
        if (isNeedOnCreateRegister) {
            mApplication.unregisterEventBus(this);
        }
        mApplication.removeActivity(this);
    }

    /**
     * 取消注册，以避免内存泄露
     */
    public void onUnSubscribe() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
            mCompositeDisposable = null;
        }
    }

    public <T> void addSubscription(Observable<T> observable, Consumer<T> consumer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        try {
            mCompositeDisposable.add(observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumer, mDefaultConsumerThrowable));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void addSubscription(Observable<T> observable, Consumer<T> consumer, Consumer<Throwable> onError) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        try {
            mCompositeDisposable.add(observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumer, onError));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 封装一些公共参数
     *
     * @param cls
     * @return
     */
    public Intent getIntentWithPublicParams(Class<?> cls) {
        Intent intent = new Intent(mActivity, cls);
        // 放入一些公共参数
        return intent;
    }

    public void startCustomActivity(Intent intent) {
        startActivity(intent);
    }

    public void startCustomActivity(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    public void startCustomActivity(Intent intent, int requestCode, Bundle options) {
        startActivityForResult(intent, requestCode, options);
    }

    public <T extends View> T findView(int resId) {
        return (T) findViewById(resId);
    }

    public <T extends View> T findView(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param editText
     */
    public void showSoftInput(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            editText.requestFocus();
            imm.showSoftInput(editText, 0);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    public void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && softInputIsOpen(context)) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * @param context
     * @return 若返回true，则表示输入法打开
     */
    public boolean softInputIsOpen(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }
}
