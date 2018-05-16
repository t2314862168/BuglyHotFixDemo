package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.UserLoginResultBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface UserRxAPI {
    /**
     * 用户登录
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<MBaseBean<UserLoginResultBean>> loginUser(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @Field("username") String username, @Field("password") String password);

    /**
     * 注册用户
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<MBaseBean<String>> registerUser(@Header("token") String token, @Header("signatrue") String signatrue,
                                               @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);

    /**
     * 获取用户列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("user/getUserList")
    Observable<MBaseBean<String>> getUserList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);

}
