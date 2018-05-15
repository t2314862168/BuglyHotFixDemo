package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.GoodsRxAPI;
import com.tangxb.pay.hero.bean.GoodsBean;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * 商品管理的Controller<br>
 * Created by tangxuebing on 2018/5/15.
 */

public class GoodsMangerController {
    private BaseActivity baseActivity;

    public GoodsMangerController(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    /**
     * 获取商品列表数据
     *
     * @return
     */
    public Observable<GoodsBean> getGoodsList(long userId, String searchKeyword, int page, int rows) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(GoodsRxAPI.class)
                .getGoodsList(userId, searchKeyword, page, rows);
    }

    /**
     * 获取商品列表数据
     *
     * @param userId
     * @param searchKeyword
     * @param page
     * @param rows
     * @param consumer
     */
    public void getGoodsList(long userId, String searchKeyword, int page, int rows, Consumer<GoodsBean> consumer) {
        baseActivity.addSubscription(getGoodsList(userId, searchKeyword, page, rows)
                , consumer);
    }
}
