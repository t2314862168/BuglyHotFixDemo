package com.tangxb.pay.hero.controller;

import android.text.TextUtils;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.StorageRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.StorageOrderBean;
import com.tangxb.pay.hero.bean.StorageOrderItemBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 发货管理的Controller<br>
 * Created by tangxuebing on 2018/5/23.
 */

public class SendGoodsMangerController extends BaseControllerWithActivity {
    public SendGoodsMangerController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取各地库房订单列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    Observable<MBaseBean<List<StorageOrderBean>>> getStorageOrderList(String token, String signatrue
            , String timestamp) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(StorageRxAPI.class)
                .getStorageOrderList(token, signatrue, timestamp);
    }

    /**
     * 获取新订单
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<List<StorageOrderBean>>> getNewStorageOrderList(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(StorageRxAPI.class)
                .getNewStorageOrderList(token, signatrue, timestamp);
    }

    /**
     * 获取单个库房详细订单
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<List<StorageOrderItemBean>>> getStorageOrderInfoList(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(StorageRxAPI.class)
                .getStorageOrderInfoList(token, signatrue, timestamp, data);
    }

    /**
     * 分配完毕
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> dispatchOrder(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(StorageRxAPI.class)
                .dispatchOrder(token, signatrue, timestamp, data);
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
                .create(StorageRxAPI.class)
                .deliverOk(token, signatrue, timestamp, data);
    }

    /**
     * 获取各地库房订单列表
     *
     * @return
     */
    public Observable<MBaseBean<List<StorageOrderBean>>> getStorageOrderList() {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(null, token, timestamp);
        return getStorageOrderList(token, signatrue, timestamp);
    }

    /**
     * 获取新订单
     *
     * @param searchKey
     * @return
     */
    public Observable<MBaseBean<List<StorageOrderBean>>> getNewStorageOrderList(String searchKey) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        if (!TextUtils.isEmpty(searchKey)) {
            data.put("key", searchKey);
        }
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getNewStorageOrderList(token, signatrue, timestamp, data);
    }

    /**
     * 获取单个库房详细订单
     *
     * @param userId
     * @return
     */
    public Observable<MBaseBean<List<StorageOrderItemBean>>> getStorageOrderInfoList(long userId) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        if (userId != 0) {
            data.put("order_id", userId + "");
        }
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getStorageOrderInfoList(token, signatrue, timestamp, data);
    }

    /**
     * 分配完毕
     *
     * @param deliverJson
     * @return
     */
    public Observable<MBaseBean<String>> dispatchOrder(long userId, String deliverJson) {
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
        return dispatchOrder(token, signatrue, timestamp, data);
    }

    /**
     * 发车
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
}
