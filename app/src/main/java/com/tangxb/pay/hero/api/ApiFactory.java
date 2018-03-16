package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.RetrofitClient;

/**
 * Created by Tangxb on 2016/11/4.
 */

public enum ApiFactory {

    INSTANCE;

    private static WelfareAPI welfareAPI;
    private static WelfareRxAPI welfareRxAPI;

    ApiFactory() {
    }

    public static WelfareAPI getWelfareAPI() {
        if (welfareAPI == null) {
            welfareAPI = RetrofitClient.INSTANCE.getRetrofit().create(WelfareAPI.class);
        }
        return welfareAPI;
    }

    public static WelfareRxAPI getWelfareRxAPI() {
        if (welfareAPI == null) {
            welfareRxAPI = RetrofitClient.INSTANCE.getRetrofit().create(WelfareRxAPI.class);
        }
        return welfareRxAPI;
    }
}
