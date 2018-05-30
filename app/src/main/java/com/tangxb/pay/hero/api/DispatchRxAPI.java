package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.DeliverPersonBean;
import com.tangxb.pay.hero.bean.DeliverPersonOrderBean;
import com.tangxb.pay.hero.bean.DeliverProductBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.ReceiveGoodsBean;
import com.tangxb.pay.hero.bean.ReceiveOrderBean;

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
 * Created by Administrator on 2018/5/24 0024.
 */

public interface DispatchRxAPI {
    /**
     * 获取配送产品列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @GET("deliver/getDeliverProduct")
    Observable<MBaseBean<List<DeliverProductBean>>> getDeliverProductList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp);

    /**
     * 获取配送人列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("deliver/getDeliverOrderList")
    Observable<MBaseBean<List<DeliverPersonBean>>> getDeliverOrderList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);

    /**
     * 获取配送订单详情
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("deliver/getUserOrderInfo")
    Observable<MBaseBean<List<DeliverPersonOrderBean>>> getUserOrderInfo(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);

    /**
     * 完成配送
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("deliver/deliverOk")
    Observable<MBaseBean<String>> deliverOk(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);

    /**
     * 获取收货列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("deliver/getReceiveList")
    Observable<MBaseBean<List<ReceiveGoodsBean>>> receiveList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);

    /**
     * 收货： 收货详情
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("deliver/getReceiveOrderInfo")
    Observable<MBaseBean<List<ReceiveOrderBean>>> getReceiveOrderInfo(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);

    /**
     * 收货详情  点击确认
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("deliver/confirmReceive")
    Observable<MBaseBean<String>> confirmReceive(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);
}
