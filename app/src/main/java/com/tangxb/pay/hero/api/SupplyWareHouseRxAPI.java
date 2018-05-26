package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.StorageOrderBean;
import com.tangxb.pay.hero.bean.StorageOrderItemBean;

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

public interface SupplyWareHouseRxAPI {
    /**
     * 获取各地库房订单列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @GET("storage/getStorageOrderList")
    Observable<MBaseBean<List<StorageOrderBean>>> getStorageOrderList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp);

    /**
     * 获取新订单
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @GET("storage/getNewStorageOrder")
    Observable<MBaseBean<List<StorageOrderBean>>> getNewStorageOrderList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp);

    /**
     * 获取单个库房详细订单
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("storage/getStorageOrderInfo")
    Observable<MBaseBean<List<StorageOrderItemBean>>> getStorageOrderInfoList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);

    /**
     * 分配完毕
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("storage/dispatchOrder")
    Observable<MBaseBean<String>> dispatchOrder(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);

    /**
     * 发车
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("storage/deliverOk")
    Observable<MBaseBean<String>> deliverOk(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);
}
