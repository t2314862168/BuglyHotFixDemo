package com.tangxb.pay.hero.controller;

import android.text.TextUtils;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.UserRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.UserBean;
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
     * 通过角色id来获取用户列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<List<UserBean>>> getUserListByRoleId(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(UserRxAPI.class)
                .getUserListByRoleId(token, signatrue, timestamp, data);
    }

    /**
     * 通过角色id来获取用户列表
     *
     * @param page
     * @param rows
     * @param roleId
     * @param key
     * @param enable
     * @return
     */
    public Observable<MBaseBean<List<UserBean>>> getUserListByRoleId(int page, int rows, long roleId, String key, int enable) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("page", page + "");
        data.put("rows", rows + "");
        data.put("role_id", roleId + "");
        if (!TextUtils.isEmpty(key)) {
            data.put("key", key);
        }
        data.put("enable", enable + "");
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getUserListByRoleId(token, signatrue, timestamp, data);
    }
}
