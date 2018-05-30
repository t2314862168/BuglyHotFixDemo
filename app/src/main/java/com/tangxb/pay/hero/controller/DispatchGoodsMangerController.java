package com.tangxb.pay.hero.controller;

import android.text.TextUtils;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.DispatchRxAPI;
import com.tangxb.pay.hero.bean.DeliverPersonBean;
import com.tangxb.pay.hero.bean.DeliverPersonOrderBean;
import com.tangxb.pay.hero.bean.DeliverProductBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.ReceiveGoodsBean;
import com.tangxb.pay.hero.bean.ReceiveOrderBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 配送管理的Controller<br>
 * Created by tangxuebing on 2018/5/23.
 */

public class DispatchGoodsMangerController extends BaseControllerWithActivity {
    public DispatchGoodsMangerController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取配送产品列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    Observable<MBaseBean<List<DeliverProductBean>>> getDeliverProductList(String token, String signatrue
            , String timestamp) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(DispatchRxAPI.class)
                .getDeliverProductList(token, signatrue, timestamp);
    }

    /**
     * 获取配送人列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<List<DeliverPersonBean>>> getDeliverOrderList(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(DispatchRxAPI.class)
                .getDeliverOrderList(token, signatrue, timestamp, data);
    }

    /**
     * 获取配送订单详情
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<List<DeliverPersonOrderBean>>> getUserOrderInfo(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(DispatchRxAPI.class)
                .getUserOrderInfo(token, signatrue, timestamp, data);
    }

    /**
     * 完成配送
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> deliverOk(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(DispatchRxAPI.class)
                .deliverOk(token, signatrue, timestamp, data);
    }

    /**
     * 获取收货列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<List<ReceiveGoodsBean>>> receiveList(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(DispatchRxAPI.class)
                .receiveList(token, signatrue, timestamp, data);
    }

    /**
     * 收货： 收货详情
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<List<ReceiveOrderBean>>> getReceiveOrderInfo(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(DispatchRxAPI.class)
                .getReceiveOrderInfo(token, signatrue, timestamp, data);
    }

    /**
     * 收货详情  点击确认
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> confirmReceive(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(DispatchRxAPI.class)
                .confirmReceive(token, signatrue, timestamp, data);
    }

    /**
     * 获取配送产品列表
     *
     * @return
     */
    public Observable<MBaseBean<List<DeliverProductBean>>> getDeliverProductList() {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(null, token, timestamp);
        return getDeliverProductList(token, signatrue, timestamp);
    }

    /**
     * 获取配送人列表
     *
     * @param searchKey
     * @return
     */
    public Observable<MBaseBean<List<DeliverPersonBean>>> getDeliverOrderList(String searchKey) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        if (!TextUtils.isEmpty(searchKey)) {
            data.put("key", searchKey);
        }
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getDeliverOrderList(token, signatrue, timestamp, data);
    }

    /**
     * 获取配送订单详情
     *
     * @param userId
     * @return
     */
    public Observable<MBaseBean<List<DeliverPersonOrderBean>>> getUserOrderInfo(long userId) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        if (userId != 0) {
            data.put("order_id", userId + "");
        }
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getUserOrderInfo(token, signatrue, timestamp, data);
    }

    /**
     * 完成配送
     *
     * @param deliverJson
     * @return
     */
    public Observable<MBaseBean<String>> deliverOk(long userId, String deliverJson) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        if (userId != 0) {
            data.put("order_id", userId + "");
        }
        if (!TextUtils.isEmpty(deliverJson)) {
            data.put("deliverJson", deliverJson);
        }
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return deliverOk(token, signatrue, timestamp, data);
    }

    /**
     * 获取收货列表
     *
     * @return
     */
    public Observable<MBaseBean<List<ReceiveGoodsBean>>> receiveList(int page, int rows, String keyword) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("page", page + "");
        data.put("rows", rows + "");
        if (!TextUtils.isEmpty(keyword)) {
            data.put("key", keyword);
        }
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return receiveList(token, signatrue, timestamp, data);
    }

    /**
     * 收货： 收货详情
     *
     * @param userId
     * @return
     */
    public Observable<MBaseBean<List<ReceiveOrderBean>>> getReceiveOrderInfo(long userId) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        if (userId != 0) {
            data.put("order_id", userId + "");
        }
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getReceiveOrderInfo(token, signatrue, timestamp, data);
    }

    /**
     * 收货详情  点击确认
     *
     * @param deliverJson
     * @return
     */
    public Observable<MBaseBean<String>> confirmReceive(long orderId, String deliverJson, String log) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("order_id", orderId + "");
        if (!TextUtils.isEmpty(deliverJson)) {
            data.put("dataJson", deliverJson);
        }
        if (!TextUtils.isEmpty(log)) {
            data.put("log", log);
        }
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return confirmReceive(token, signatrue, timestamp, data);
    }
}
