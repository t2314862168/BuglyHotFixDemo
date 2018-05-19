package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.GoodsBean;
import com.tangxb.pay.hero.bean.MBaseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface GoodsRxAPI {
    /**
     * 获取商品列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("product/getProducts")
    Observable<MBaseBean<List<GoodsBean>>> getGoodsList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);
}
