package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.WarehouseAllInOneBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by zll on 2018/5/26.
 */

public interface SupplyWareHouseRxAPI {
    /**
     * 获取补仓数据
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @GET("deliver/getStorageInfo")
    Observable<MBaseBean<List<WarehouseAllInOneBean>>> getStorageInfoList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp);

    /**
     * 发送补仓数据
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("deliver/sendStorageOrder")
    Observable<MBaseBean<String>> dispatchOrder(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);

}
