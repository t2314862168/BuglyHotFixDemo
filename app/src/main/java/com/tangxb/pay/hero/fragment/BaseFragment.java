package com.tangxb.pay.hero.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
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

public abstract class BaseFragment extends Fragment implements FragmentUserVisibleController.UserVisibleCallback {
    protected Activity mActivity;
    protected View mView;
    protected Fragment mFragment;
    protected MApplication mApplication;
    protected String mClassName;
    /**
     * 在<code>onCreateView</code>的时候注册,需要在<code>onDestroy</code>取消注册
     */
    private boolean isNeedOnCreateRegister = false;
    /**
     * 在<code>onStart</code>的时候注册,需要在<code>onStop</code>取消注册
     */
    private boolean isNeedOnStartRegister = false;
    protected Resources mResources;
    private FragmentUserVisibleController userVisibleController;
    private CompositeDisposable mCompositeDisposable;
    private DefaultConsumerThrowable mDefaultConsumerThrowable;

    /**
     * 请在{@link #initData()}里面调用
     */
    protected void setNeedOnCreateRegister() {
        isNeedOnCreateRegister = true;
    }

    /**
     * 请在{@link #initData()}里面调用
     */
    protected void setNeedOnStartRegister() {
        isNeedOnStartRegister = true;
    }

    public BaseFragment() {
        userVisibleController = new FragmentUserVisibleController(this, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getClass().getSimpleName();
        mActivity = getActivity();
        mResources = getResources();
        mApplication = (MApplication) mActivity.getApplication();
        Bundle arg = getArguments();
        if (arg != null) {
            receiveBundleFromActivity(arg);
        }
        mFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutResId(), container, false);
        bindButterKnife();
        init();
        return mView;
    }

    protected abstract int getLayoutResId();


    protected void bindButterKnife() {
        ButterKnife.bind(this, mView);
    }

    protected void init() {
        initAdapter();
        initData();
        initListener();
        if (isNeedOnCreateRegister) {
            mApplication.registerEventBus(this);
        }
        mDefaultConsumerThrowable = new DefaultConsumerThrowable();
    }

    protected void initData() {

    }

    protected void initListener() {

    }

    protected void initAdapter() {

    }

    protected void receiveBundleFromActivity(Bundle arg) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (isNeedOnStartRegister) {
            mApplication.registerEventBus(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isNeedOnStartRegister) {
            mApplication.unregisterEventBus(this);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userVisibleController.activityCreated();
    }

    @Override
    public void onResume() {
        super.onResume();
        userVisibleController.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        userVisibleController.pause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        userVisibleController.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void setWaitingShowToUser(boolean waitingShowToUser) {
        userVisibleController.setWaitingShowToUser(waitingShowToUser);
    }

    @Override
    public boolean isWaitingShowToUser() {
        return userVisibleController.isWaitingShowToUser();
    }

    @Override
    public boolean isVisibleToUser() {
        return userVisibleController.isVisibleToUser();
    }

    @Override
    public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {
        if (isVisibleToUser) {
            MobclickAgent.onPageStart(mClassName);
        } else {
            MobclickAgent.onPageEnd(mClassName);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isNeedOnCreateRegister) {
            mApplication.unregisterEventBus(this);
        }
        RefWatcher refWatcher = mApplication.getRefWatcher(mActivity);
        refWatcher.watch(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onUnSubscribe();
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
}
