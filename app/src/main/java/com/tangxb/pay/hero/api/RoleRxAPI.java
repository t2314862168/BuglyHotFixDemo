package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.RoleBean;
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

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface RoleRxAPI {

    /**
     * 获取角色列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @GET("common/getRoles")
    Observable<MBaseBean<List<RoleBean>>> getRoleList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp);

    /**
     * 更改用户所属于的角色
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("user/updateRole")
    Observable<MBaseBean<String>> updateRoleToUser(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);


}
