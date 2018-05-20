package com.tangxb.pay.hero.controller;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.GoodsCategoryRxAPI;
import com.tangxb.pay.hero.bean.GoodsCategoryBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by zll on 2018/5/19.
 */

public class GoodsCategoryMangerController extends BaseControllerWithActivity {
    public GoodsCategoryMangerController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取商品列表数据
     *
     * @return
     */
    Observable<MBaseBean<List<GoodsCategoryBean>>> getCategoryList(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(GoodsCategoryRxAPI.class)
                .getCategoryList(token, signatrue, timestamp, data);
    }

    /**
     * 更改种类显示状态
     *
     * @return
     */
    Observable<MBaseBean<String>> updateCategoryStatus(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(GoodsCategoryRxAPI.class)
                .updateCategoryStatus(token, signatrue, timestamp, data);
    }

    /**
     * 添加种类
     *
     * @return
     */
    Observable<MBaseBean<String>> addCategory(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(GoodsCategoryRxAPI.class)
                .addCategory(token, signatrue, timestamp, data);
    }

    /**
     * 获取商品列表数据
     *
     * @param status 1 显示  0 隐藏
     * @return
     */
    public Observable<MBaseBean<List<GoodsCategoryBean>>> getCategoryList(int status) {
        String token = mApplication.getToken();
        Map<String, String> data = new HashMap<>();
        data.put("status", status + "");
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return getCategoryList(token, signatrue, timestamp, data);
    }

    /**
     * 更改种类显示状态
     *
     * @param status 1 显示  0 隐藏
     * @return
     */
    public Observable<MBaseBean<String>> updateCategoryStatus(long categoryId, int status) {
        String token = mApplication.getToken();
        Map<String, String> data = new HashMap<>();
        data.put("categoryId", categoryId + "");
        data.put("status", status + "");
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return updateCategoryStatus(token, signatrue, timestamp, data);
    }

    /**
     * 添加种类
     *
     * @return
     */
    public Observable<MBaseBean<String>> addCategory(long parentId, String name) {
        String token = mApplication.getToken();
        Map<String, String> data = new HashMap<>();
        if (parentId != 0) {
            data.put("parentId", parentId + "");
        }
        data.put("name", name);
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return updateCategoryStatus(token, signatrue, timestamp, data);
    }
}
