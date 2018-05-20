package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.UserBean;
import com.tangxb.pay.hero.bean.UserLoginResultBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

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
     * 通过角色id来获取用户列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("user/getUserList")
    Observable<MBaseBean<List<UserBean>>> getUserListByRoleId(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);

    /**
     * 更改多设备登录
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("user/updateMultiLogin")
    Observable<MBaseBean<String>> updateMultiLogin(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);
}
