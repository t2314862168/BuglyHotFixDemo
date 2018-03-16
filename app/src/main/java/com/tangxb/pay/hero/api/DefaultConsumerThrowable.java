package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.util.MLogUtils;

import io.reactivex.functions.Consumer;

/**
 * 默认的异常处理<br>
 * Created by Taxngb on 2017/12/22.
 */

public class DefaultConsumerThrowable implements Consumer<Throwable> {
    private static final String TAG = DefaultConsumerThrowable.class.getSimpleName();

    @Override
    public void accept(Throwable throwable) throws Exception {
        MLogUtils.d(TAG, throwable.toString());
    }
}
