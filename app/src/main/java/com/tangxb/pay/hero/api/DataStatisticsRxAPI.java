package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.DataStatisticsBean;
import com.tangxb.pay.hero.bean.MBaseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public interface DataStatisticsRxAPI {
    /**
     * 按时间统计获取列表数据
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("statistic/getTimeData")
    Observable<MBaseBean<List<DataStatisticsBean>>> getTimeData(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);

    /**
     * 按业务统计获取列表数据
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("statistic/getBusinessData")
    Observable<MBaseBean<List<DataStatisticsBean>>> getBusinessData(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);
}
