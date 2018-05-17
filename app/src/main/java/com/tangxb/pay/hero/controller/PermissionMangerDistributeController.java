package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.PermissionRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.PermissionBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class PermissionMangerDistributeController extends BaseControllerWithActivity {
    public PermissionMangerDistributeController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取我能看见的权限列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    Observable<MBaseBean<List<PermissionBean>>> getMyPermissions(String token, String signatrue, String timestamp
            , long roleId) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(PermissionRxAPI.class)
                .getMyPermissions(token, signatrue, timestamp, roleId);
    }

    /**
     * 更新用户权限
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> updateRolePermissions(String token, String signatrue, String timestamp
            , Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(PermissionRxAPI.class)
                .updateRolePermissions(token, signatrue, timestamp, data);
    }


    /**
     * 获取我能看见的权限列表
     *
     * @return
     */
    public Observable<MBaseBean<List<PermissionBean>>> getMyPermissions(long roleId) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("role_id", roleId + "");
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getMyPermissions(token, signatrue, timestamp, roleId);
    }

    /**
     * 更新用户权限
     *
     * @param roleId
     * @param groupJson
     * @return
     */
    public Observable<MBaseBean<String>> updateRolePermissions(long roleId, String groupJson) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("role_id", roleId + "");
        data.put("permissions", groupJson);
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return updateRolePermissions(token, signatrue, timestamp, data);
    }
}
