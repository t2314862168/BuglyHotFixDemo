package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.RoleRxAPI;
import com.tangxb.pay.hero.api.UserRxAPI;
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
     * 更改用户密码
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> updateUserPwd(String token, String signatrue, String timestamp
            , Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(RoleRxAPI.class)
                .updateUserPwd(token, signatrue, timestamp, data);
    }

    /**
     * 更改城市
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> updateUserCity(String token, String signatrue, String timestamp
            , Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(RoleRxAPI.class)
                .updateUserCity(token, signatrue, timestamp, data);
    }

    /**
     * 更改地址
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> updateUserAddress(String token, String signatrue, String timestamp
            , Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(RoleRxAPI.class)
                .updateUserAddress(token, signatrue, timestamp, data);
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
    Observable<MBaseBean<String>> updateUserState(String token, String signatrue, String timestamp
            , Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(RoleRxAPI.class)
                .updateUserState(token, signatrue, timestamp, data);
    }

    /**
     * 更改用户多设备登录
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param data
     * @return
     */
    Observable<MBaseBean<String>> updateMultiLogin(String token, String signatrue, String timestamp
            , Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(UserRxAPI.class)
                .updateMultiLogin(token, signatrue, timestamp, data);
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
     * 更改用户密码
     *
     * @return
     */
    public Observable<MBaseBean<String>> updateUserPwd(long changeUserId, String newPwd, String vercode, String mobile) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("user_id", changeUserId + "");
        data.put("newPwd", newPwd);
        data.put("vercode", vercode);
        data.put("mobile", mobile);
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return updateUserPwd(token, signatrue, timestamp, data);
    }

    /**
     * 更改城市
     *
     * @return
     */
    public Observable<MBaseBean<String>> updateUserCity(long changeUserId, String city) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("user_id", changeUserId + "");
        data.put("city", city);
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return updateUserCity(token, signatrue, timestamp, data);
    }

    /**
     * 更改地址
     *
     * @return
     */
    public Observable<MBaseBean<String>> updateUserAddress(long changeUserId, String city) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("user_id", changeUserId + "");
        data.put("address", city);
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return updateUserAddress(token, signatrue, timestamp, data);
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

    /**
     * 更改用户状态
     *
     * @return
     */
    public Observable<MBaseBean<String>> updateUserState(long userId, int status) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
        data.put("user_id", userId + "");
        data.put("status", status + "");
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return updateUserState(token, signatrue, timestamp, data);
    }


    /**
     * 更改用户多设备登录
     *
     * @return
     */
    public Observable<MBaseBean<String>> updateMultiLogin(long userId, int status) {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        Map<String, String> data = new HashMap<>();
//        data.put("user_id", userId + "");
        data.put("multi", status + "");
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return updateMultiLogin(token, signatrue, timestamp, data);
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
