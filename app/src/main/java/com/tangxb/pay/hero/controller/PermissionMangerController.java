package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.UserRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class PermissionMangerController extends BaseControllerWithActivity {
    public PermissionMangerController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    Observable<MBaseBean<String>> getUserList(String token, String signatrue, String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(UserRxAPI.class)
                .getUserList(token, signatrue, timestamp, data);
    }

    public Observable<MBaseBean<String>> getUserList(int page, int rows, long role_id, String key, int enable) {
        String token = mApplication.getToken();
        Map<String, String> data = new HashMap<>();
        data.put("page", page + "");
        data.put("rows", rows + "");
//        data.put("role_id", role_id + "");
//        if (!TextUtils.isEmpty(key)) {
//            data.put("key", key);
//        }
//        data.put("rows", rows + "");
//        data.put("enable", enable + "");
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getUserList(token, signatrue, timestamp, data);
    }
}
