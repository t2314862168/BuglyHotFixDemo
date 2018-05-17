package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.RoleRxAPI;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.RoleBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class EditUserInfoController extends BaseControllerWithActivity {
    public EditUserInfoController(BaseActivity baseActivity) {
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
     * 更改用户所属于的角色
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> updateRoleToUser(String token, String signatrue, String timestamp
            , Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(RoleRxAPI.class)
                .updateRoleToUser(token, signatrue, timestamp, data);
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

    /**
     * 更改用户所属于的角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    public Observable<MBaseBean<String>> updateRoleToUser(long userId, long roleId) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("user_id", userId + "");
        data.put("role_id", roleId + "");
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return updateRoleToUser(token, signatrue, timestamp, data);
    }
}
