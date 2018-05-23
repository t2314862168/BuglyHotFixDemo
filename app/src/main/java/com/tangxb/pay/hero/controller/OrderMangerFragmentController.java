package com.tangxb.pay.hero.controller;

import android.text.TextUtils;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.OrderRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.OrderBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 用户管理界面的Controller<br>
 * Created by tangxuebing on 2018/5/14.
 */

public class OrderMangerFragmentController extends BaseControllerWithActivity {
    public OrderMangerFragmentController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取订单列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<List<OrderBean>>> getOrderList(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(OrderRxAPI.class)
                .getOrderList(token, signatrue, timestamp, data);
    }

    /**
     * 获取订单列表
     *
     * @param page
     * @param rows
     * @param status
     * @param key
     * @return
     */
    public Observable<MBaseBean<List<OrderBean>>> getOrderList(int status, int page, int rows, String key) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("status", status + "");
        data.put("page", page + "");
        data.put("rows", rows + "");
        if (!TextUtils.isEmpty(key)) {
            data.put("key", key);
        }
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getOrderList(token, signatrue, timestamp, data);
    }
}
