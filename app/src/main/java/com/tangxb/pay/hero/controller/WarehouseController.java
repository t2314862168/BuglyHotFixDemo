package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.WarehouseRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.UserBean;
import com.tangxb.pay.hero.bean.WarehouseAllInOneBean;
import com.tangxb.pay.hero.bean.WarehouseBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 仓库Controller<br>
 * Created by zll on 2018/5/26.
 */

public class WarehouseController extends BaseControllerWithActivity {
    public WarehouseController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取库房列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    Observable<MBaseBean<List<WarehouseBean>>> getStorageList(String token, String signatrue
            , String timestamp) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(WarehouseRxAPI.class)
                .getStorageList(token, signatrue, timestamp);
    }

    /**
     * 保存/创建 库房信息
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> updateStorage(String token, String signatrue, String timestamp
            , Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(WarehouseRxAPI.class)
                .updateStorage(token, signatrue, timestamp, data);
    }

    /**
     * 获取单个库房的业务员列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<List<UserBean>>> getStorageProxyer(String token, String signatrue, String timestamp
            , Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(WarehouseRxAPI.class)
                .getStorageProxyer(token, signatrue, timestamp, data);
    }

    /**
     * 获取总览库存
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    Observable<MBaseBean<List<WarehouseAllInOneBean>>> getStorageOrderAllInOne(String token, String signatrue
            , String timestamp) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(WarehouseRxAPI.class)
                .getStorageOrderAllInOne(token, signatrue, timestamp);
    }

    /**
     * 设置库房状态
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> updateStorageStatus(String token, String signatrue, String timestamp
            , Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(WarehouseRxAPI.class)
                .updateStorageStatus(token, signatrue, timestamp, data);
    }

    /**
     * 获取库房列表
     *
     * @return
     */
    public Observable<MBaseBean<List<WarehouseBean>>> getStorageList() {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(null, token, timestamp);
        return getStorageList(token, signatrue, timestamp);
    }

    /**
     * 保存/创建 库房信息
     *
     * @return
     */
    public Observable<MBaseBean<String>> updateStorage(long id, String name, String city, String address
            , String distance, String proxy_ids) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        if (id != 0) {
            data.put("id", id + "");
        }
        data.put("name", name);
        data.put("city", city);
        data.put("address", address);
        data.put("distance", distance);
        data.put("proxy_ids", proxy_ids);
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return updateStorage(token, signatrue, timestamp, data);
    }

    /**
     * 获取单个库房的业务员列表
     *
     * @param id
     * @return
     */
    public Observable<MBaseBean<List<UserBean>>> getStorageProxyer(long id) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("storageId", id + "");
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getStorageProxyer(token, signatrue, timestamp, data);
    }

    /**
     * 获取总览库存
     *
     * @return
     */
    public Observable<MBaseBean<List<WarehouseAllInOneBean>>> getStorageOrderAllInOne() {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(null, token, timestamp);
        return getStorageOrderAllInOne(token, signatrue, timestamp);
    }

    /**
     * 设置库房状态
     *
     * @return
     */
    public Observable<MBaseBean<String>> updateStorageStatus(long id, int status) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("storage_id", id + "");
        data.put("status", status + "");
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return updateStorageStatus(token, signatrue, timestamp, data);
    }
}