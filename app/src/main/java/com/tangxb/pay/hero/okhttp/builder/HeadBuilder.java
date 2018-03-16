package com.tangxb.pay.hero.okhttp.builder;


import com.tangxb.pay.hero.okhttp.OkHttpUtils;
import com.tangxb.pay.hero.okhttp.request.OtherRequest;
import com.tangxb.pay.hero.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
