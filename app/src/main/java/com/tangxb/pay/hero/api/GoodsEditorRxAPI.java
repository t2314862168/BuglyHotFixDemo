package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.BuyUnitBean;
import com.tangxb.pay.hero.bean.MBaseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface GoodsEditorRxAPI {

    /**
     * 上传商品和修改商品
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("product/uploadProduct")
    Observable<MBaseBean<String>> uploadGoods(@Header("token") String token, @Header("signatrue") String signatrue,
                                              @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);

    /**
     * 获取订购单位
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    @GET("product/getUnits")
    Observable<MBaseBean<List<BuyUnitBean>>> getUnits(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp);

    /**
     * 更改上下架状态
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("product/changeStatus")
    Observable<MBaseBean<String>> changeStatus(@Header("token") String token, @Header("signatrue") String signatrue,
                                               @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);

}
