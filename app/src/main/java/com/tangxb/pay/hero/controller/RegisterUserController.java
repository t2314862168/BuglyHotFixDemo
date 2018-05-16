package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.UserRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;
import com.tangxb.pay.hero.listener.SimpleResultListener;
import com.tangxb.pay.hero.util.ToastUtils;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by tangxuebing on 2018/5/16.
 */

public class RegisterUserController extends BaseControllerWithActivity {
    public RegisterUserController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 注册用户
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> registerUser(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(UserRxAPI.class)
                .registerUser(token, signatrue, timestamp, data);
    }

    /**
     * 注册用户
     *
     * @param data
     */
    public void registerUser(Map<String, String> data, final SimpleResultListener listener) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        if (baseActivity.get() == null) return;
        baseActivity.get().addSubscription(registerUser(token, signatrue, timestamp, data), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> baseBean) throws Exception {
                ToastUtils.t(mApplication, baseBean.getMessage());
                if (baseActivity.get() == null) return;
                listener.doSuccess();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (baseActivity.get() == null) return;
                ToastUtils.t(mApplication, throwable.getMessage());
            }
        });
    }
}
