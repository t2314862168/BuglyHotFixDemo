package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.OrderRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.OrderStatusBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * 订单管理界面的Controller<br>
 * Created by tangxuebing on 2018/5/14.
 */

public class OrderMangerController extends BaseControllerWithActivity {
    public OrderMangerController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取订单状态列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    Observable<MBaseBean<List<OrderStatusBean>>> getOrderStatusList(String token, String signatrue, String timestamp) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(OrderRxAPI.class)
                .getOrderStatusList(token, signatrue, timestamp);
    }

    /**
     * 获取订单状态列表
     *
     * @return
     */
    public Observable<MBaseBean<List<OrderStatusBean>>> getOrderStatusList() {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(null, token, timestamp);
        return getOrderStatusList(token, signatrue, timestamp);
    }
}
