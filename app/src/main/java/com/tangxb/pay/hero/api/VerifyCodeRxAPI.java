package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.MBaseBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by tangxuebing on 2018/5/16.
 */

public interface VerifyCodeRxAPI {
    /**
     * 获取验证码
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("common/getVerCode")
    Observable<MBaseBean<String>> getVerCode(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @Field("phone") String phone);
}
