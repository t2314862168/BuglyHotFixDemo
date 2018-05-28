package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.DeliverPersonOrderBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.UserBean;
import com.tangxb.pay.hero.bean.WarehouseAllInOneBean;
import com.tangxb.pay.hero.bean.WarehouseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by zll on 2018/5/26.
 */

public interface WarehouseRxAPI {
    /**
     * 获取库房列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @GET("storage/getStorageList")
    Observable<MBaseBean<List<WarehouseBean>>> getStorageList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp);

    /**
     * 保存/创建 库房信息
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("storage/updateStorage")
    Observable<MBaseBean<String>> updateStorage(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);

    /**
     * 获取单个库房的业务员列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("storage/getStorageProxyer")
    Observable<MBaseBean<List<UserBean>>> getStorageProxyer(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);

    /**
     * 获取总览库存
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @GET("storage/getStorageAllInOne")
    Observable<MBaseBean<List<WarehouseAllInOneBean>>> getStorageOrderAllInOne(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp);

    /**
     * 设置库房状态
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @FormUrlEncoded
    @POST("storage/updateStorageStatus")
    Observable<MBaseBean<String>> updateStorageStatus(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);

    /**
     * 获取单个库房订单列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @GET("storage/getStorageOrderInfo")
    Observable<MBaseBean<List<DeliverPersonOrderBean>>> getStorageOrderInfo(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);

    /**
     * 分配完毕
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @FormUrlEncoded
    @POST("storage/dispatchOrderProductOk")
    Observable<MBaseBean<String>> dispatchOrderProductOk(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);


    /**
     * 发车
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @FormUrlEncoded
    @POST("storage/sendOutCart")
    Observable<MBaseBean<String>> sendOutCart(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);

}
