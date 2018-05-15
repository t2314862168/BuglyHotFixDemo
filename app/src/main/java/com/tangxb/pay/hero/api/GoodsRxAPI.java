package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.GoodsBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface GoodsRxAPI {

    /**
     * 获取商品列表
     *
     * @param userId
     * @param searchKeyword
     * @param page
     * @param rows
     * @return
     */
    @GET("common/checkVersion")
    Observable<GoodsBean> getGoodsList(@Query("user_id") long userId, @Query("keyword") String searchKeyword
            , @Query("page") int page, @Query("rows") int rows);
}
