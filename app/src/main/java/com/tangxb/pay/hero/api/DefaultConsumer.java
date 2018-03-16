package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.MApplication;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.util.MLogUtils;
import com.tangxb.pay.hero.util.ToastUtils;

import io.reactivex.functions.Consumer;

/**
 * 默认的请求返回处理<br>
 * Created by Taxngb on 2017/12/22.
 */

public abstract class DefaultConsumer<T> implements Consumer<MBaseBean<T>> {
    private static final String TAG = DefaultConsumer.class.getSimpleName();
    /**
     * 服务器操作成功的状态码
     */
    private final int successCode = 1;
    /**
     * token错误的状态码
     */
    private final int tokenErrorCode = 5;
    private MApplication mApplication;

    public DefaultConsumer(MApplication mApplication) {
        this.mApplication = mApplication;
    }

    /**
     * 后台返回操作成功调用
     *
     * @param baseBean
     */
    public abstract void operateSuccess(MBaseBean<T> baseBean);

    /**
     * token错误的时候使用,token的优先级高于{@link #operateError(String)}
     */
    public void tokenError(String message) {
        if (mApplication == null) return;
        ToastUtils.t(mApplication, message);
    }

    /**
     * 请在需要的时候打印数据
     *
     * @param message
     */
    public void operateError(String message) {
        if (mApplication == null) return;
        ToastUtils.t(mApplication, message);
    }

    @Override
    public void accept(MBaseBean<T> baseBean) throws Exception {
        int code = baseBean.getCode();
        String message = baseBean.getMessage();
        if (code == successCode) {
            operateSuccess(baseBean);
        } else if (code == tokenErrorCode) {
            tokenError(message);
            MLogUtils.d(TAG, message);
        } else {
            operateError(message);
            MLogUtils.d(TAG, message);
        }
    }
}
