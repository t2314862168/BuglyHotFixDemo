package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.GoodsCategoryBean;
import com.tangxb.pay.hero.bean.MBaseBean;

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
 * Created by Taxngb on 2017/12/22.
 */

public interface GoodsCategoryRxAPI {
    /**
     * 获取商品种类列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @GET("product/getCategories")
    Observable<MBaseBean<List<GoodsCategoryBean>>> getCategoryList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @QueryMap Map<String, String> data);

    /**
     * 更改种类显示状态
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("product/updateCategoryStatus")
    Observable<MBaseBean<String>> updateCategoryStatus(@Header("token") String token, @Header("signatrue") String signatrue,
                                                       @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);

    /**
     * 添加种类
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("product/addCategory")
    Observable<MBaseBean<String>> addCategory(@Header("token") String token, @Header("signatrue") String signatrue,
                                              @Header("timestamp") String timestamp, @FieldMap Map<String, String> data);

}
