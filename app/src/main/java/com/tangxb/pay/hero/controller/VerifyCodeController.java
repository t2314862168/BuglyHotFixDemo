package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.VerifyCodeRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;
import com.tangxb.pay.hero.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by tangxuebing on 2018/5/16.
 */

public class VerifyCodeController extends BaseControllerWithActivity {
    public VerifyCodeController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取验证码
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param phone
     * @return
     */
    Observable<MBaseBean<String>> getVerCode(String token, String signatrue, String timestamp, String phone) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(VerifyCodeRxAPI.class)
                .getVerCode(token, signatrue, timestamp, phone);
    }

    public void getVerCode(String phone) {
        String token = mApplication.getToken();
        Map<String, String> data = new HashMap<>();
        data.put("phone", phone);
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        baseActivity.get().addSubscription(getVerCode(token, signatrue, timestamp, phone), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> bean) throws Exception {
                ToastUtils.t(mApplication, bean.getMessage());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.t(mApplication, throwable.getMessage());
            }
        });
    }
}
