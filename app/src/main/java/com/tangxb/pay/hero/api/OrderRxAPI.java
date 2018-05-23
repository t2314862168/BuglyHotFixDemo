package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.OrderBean;
import com.tangxb.pay.hero.bean.OrderItemBean;
import com.tangxb.pay.hero.bean.OrderStatusBean;

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
 * Created by Administrator on 2018/5/20 0020.
 */

public interface OrderRxAPI {
    /**
     * 获取订单状态列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @GET("order/getOrderStatus")
    Observable<MBaseBean<List<OrderStatusBean>>> getOrderStatusList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp);


    /**
     * 获取订单列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("order/getOrderList")
    Observable<MBaseBean<List<OrderBean>>> getOrderList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);

    /**
     * 获取订单详情
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("order/getOrderDetail")
    Observable<MBaseBean<List<OrderItemBean>>> getOrderDetail(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);

    /**
     * 操作订单
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("order/operateOrder")
    Observable<MBaseBean<String>> operateOrder(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);
}
