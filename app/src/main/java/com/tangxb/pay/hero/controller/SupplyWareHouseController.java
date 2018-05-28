package com.tangxb.pay.hero.controller;

import android.text.TextUtils;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.SupplyWareHouseRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.WarehouseAllInOneBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 补仓界面Controller<br>
 * Created by zll on 2018/5/26.
 */

public class SupplyWareHouseController extends BaseControllerWithActivity {
    public SupplyWareHouseController(BaseActivity baseActivity) {
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
    Observable<MBaseBean<List<WarehouseAllInOneBean>>> getStorageInfoList(String token, String signatrue
            , String timestamp) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(SupplyWareHouseRxAPI.class)
                .getStorageInfoList(token, signatrue, timestamp);
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
                .create(SupplyWareHouseRxAPI.class)
                .dispatchOrder(token, signatrue, timestamp, data);
    }


    /**
     * 获取各地库房订单列表
     *
     * @return
     */
    public Observable<MBaseBean<List<WarehouseAllInOneBean>>> getStorageInfoList() {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(null, token, timestamp);
        return getStorageInfoList(token, signatrue, timestamp);
    }


    /**
     * 分配完毕
     *
     * @param deliverJson
     * @return
     */
    public Observable<MBaseBean<String>> dispatchOrder(String deliverJson) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        if (!TextUtils.isEmpty(deliverJson)) {
            data.put("dataJson", deliverJson);
        }
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return dispatchOrder(token, signatrue, timestamp, data);
    }

}
