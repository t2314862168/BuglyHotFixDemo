package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.PermissionBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/17 0017.
 */

public interface PermissionRxAPI {
    /**
     * 获取我能看见的权限列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @GET("common/getMyPermissions")
    Observable<MBaseBean<List<PermissionBean>>> getMyPermissions(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @Query("role_id") long roleId);

    /**
     * 更新用户权限
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("common/updatePermissions")
    Observable<MBaseBean<String>> updateRolePermissions(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);


}
