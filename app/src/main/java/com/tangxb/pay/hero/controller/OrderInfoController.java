package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.OrderRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.OrderBean;
import com.tangxb.pay.hero.bean.OrderItemBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by tangxuebing on 2018/5/14.
 */

public class OrderInfoController extends BaseControllerWithActivity {
    public OrderInfoController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取订单详情
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<List<OrderItemBean>>> getOrderDetail(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(OrderRxAPI.class)
                .getOrderDetail(token, signatrue, timestamp, data);
    }

    /**
     * 操作订单
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> operateOrder(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(OrderRxAPI.class)
                .operateOrder(token, signatrue, timestamp, data);
    }

    /**
     * 操作订单
     *
     * @return
     */
    public Observable<MBaseBean<List<OrderItemBean>>> getOrderDetail(long orderId, int status) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("order_id", orderId + "");
        data.put("status", status + "");
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getOrderDetail(token, signatrue, timestamp, data);
    }

    /**
     * 获取订单详情
     *
     * @return
     */
    public Observable<MBaseBean<String>> operateOrder(long orderId, int status) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("order_id", orderId + "");
        data.put("status", status + "");
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return operateOrder(token, signatrue, timestamp, data);
    }
}
