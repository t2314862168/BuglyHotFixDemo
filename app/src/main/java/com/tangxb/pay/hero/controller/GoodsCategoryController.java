package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.GoodsCategoryRxAPI;
import com.tangxb.pay.hero.bean.GoodsCategoryBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by zll on 2018/5/19.
 */

public class GoodsCategoryController extends BaseControllerWithActivity {
    public GoodsCategoryController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取商品列表数据
     *
     * @return
     */
    Observable<MBaseBean<List<GoodsCategoryBean>>> getCategoryList(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(GoodsCategoryRxAPI.class)
                .getCategoryList(token, signatrue, timestamp, data);
    }

    /**
     * 获取商品列表数据
     *
     * @param status 1 显示  0 隐藏
     * @return
     */
    public Observable<MBaseBean<List<GoodsCategoryBean>>> getCategoryList(int status) {
        String token = mApplication.getToken();
        Map<String, String> data = new HashMap<>();
        data.put("status", status + "");
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getCategoryList(token, signatrue, timestamp, data);
    }
}
