package com.tangxb.pay.hero.controller;

import android.text.TextUtils;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.GoodsRxAPI;
import com.tangxb.pay.hero.bean.GoodsBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 商品管理的Controller<br>
 * Created by tangxuebing on 2018/5/15.
 */

public class GoodsMangerController extends BaseControllerWithActivity {

    public GoodsMangerController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取商品列表数据
     *
     * @return
     */
    Observable<MBaseBean<List<GoodsBean>>> getGoodsList(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(GoodsRxAPI.class)
                .getGoodsList(token, signatrue, timestamp, data);
    }

    /**
     * 获取商品列表数据
     *
     * @param page
     * @param rows           从1 开始
     * @param categoryId
     * @param searchKeyword
     * @param status        1 在售，2 已下架 3 废除
     * @param promotion     默认为0表示非促销  1 表示促销
     * @return
     */
    public Observable<MBaseBean<List<GoodsBean>>> getGoodsList(int page, int rows, long categoryId
            , String searchKeyword, int status, int promotion) {
        String token = mApplication.getToken();
        Map<String, String> data = new HashMap<>();
        data.put("page", page + "");
        data.put("rows", rows + "");
        if (categoryId != 0) {
            data.put("categoryId", categoryId + "");
        }
        if (!TextUtils.isEmpty(searchKeyword)) {
            data.put("searchKeyword", searchKeyword);
        }
        data.put("status", status + "");
        data.put("promotion", promotion + "");
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getGoodsList(token, signatrue, timestamp, data);
    }
}
