package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.RoleRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.RoleBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class PermissionMangerController extends BaseControllerWithActivity {
    public PermissionMangerController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取角色列表
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @return
     */
    Observable<MBaseBean<List<RoleBean>>> getRoleList(String token, String signatrue, String timestamp) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(RoleRxAPI.class)
                .getRoleList(token, signatrue, timestamp);
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    public Observable<MBaseBean<List<RoleBean>>> getRoleList() {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(null, token, timestamp);
        return getRoleList(token, signatrue, timestamp);
    }
}
