package com.tangxb.pay.hero.controller;

import android.text.TextUtils;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.DataStatisticsRxAPI;
import com.tangxb.pay.hero.bean.DataStatisticsBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by tangxuebing on 2018/5/30.
 */

public class DataStatisticsBySalesManFragmentController extends BaseControllerWithActivity {
    public DataStatisticsBySalesManFragmentController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 按业务统计获取列表数据
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<List<DataStatisticsBean>>> getBusinessData(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(DataStatisticsRxAPI.class)
                .getBusinessData(token, signatrue, timestamp, data);
    }

    /**
     * 按业务统计获取列表数据
     *
     * @return
     */
    public Observable<MBaseBean<List<DataStatisticsBean>>> getBusinessData(int level, String id, String product_id, String startTime
            , String endTime, boolean isFright, boolean isUnit) {
        String token = mApplication.getToken();
        Map<String, String> data = new HashMap<>();
        data.put("level", level + "");
        if (!TextUtils.isEmpty(id)) {
            data.put("id", id);
        }
        if (!TextUtils.isEmpty(product_id)) {
            data.put("product_id", product_id);
        }
        data.put("startTime", startTime);
        data.put("endTime", endTime);
        if (isFright) {
            data.put("isFright", isFright + "");
        }
        if (isUnit) {
            data.put("isUnit", isUnit + "");
        }
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getBusinessData(token, signatrue, timestamp, data);
    }
}
