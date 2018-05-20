package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.UserRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.UserLoginResultBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;
import com.tangxb.pay.hero.listener.SimpleResultErrorListener;
import com.tangxb.pay.hero.listener.SimpleResultListener;
import com.tangxb.pay.hero.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by tangxuebing on 2018/5/8.
 */

public class LoginController extends BaseControllerWithActivity {

    public LoginController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    Observable<MBaseBean<UserLoginResultBean>> loginUser(String token, String signatrue
            , String timestamp, String username, String password) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(UserRxAPI.class)
                .loginUser(token, signatrue, timestamp, username, password);
    }

    public void loginUser(String username, String password, final SimpleResultListener listener, final SimpleResultErrorListener errorListener) {
        String token = mApplication.getToken();
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        baseActivity.get().addSubscription(loginUser(token, signatrue, timestamp, username, password), new Consumer<MBaseBean<UserLoginResultBean>>() {
            @Override
            public void accept(MBaseBean<UserLoginResultBean> baseBean) throws Exception {
                ToastUtils.t(mApplication, baseBean.getMessage());
                UserLoginResultBean bean = baseBean.getData();
                if (bean == null || bean.getUser() == null) {
                    mApplication.setUserLoginResultBean(null);
                    mApplication.setToken(null);
                    if (baseActivity.get() == null) return;
                    if (errorListener != null) {
                        errorListener.doError(baseBean.getMessage());
                    }
                    return;
                }
                mApplication.setUserLoginResultBean(bean);
                mApplication.setToken(bean.getUser().getToken());
                if (baseActivity.get() == null) return;
                listener.doSuccess();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.t(mApplication, throwable.getMessage());
                if (baseActivity.get() == null) return;
                if (errorListener != null) {
                    errorListener.doError(throwable.getMessage());
                }
            }
        });
    }
}
