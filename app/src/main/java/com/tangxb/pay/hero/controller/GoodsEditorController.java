package com.tangxb.pay.hero.controller;

import android.text.TextUtils;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.api.GoodsEditorRxAPI;
import com.tangxb.pay.hero.bean.BuyUnitBean;
import com.tangxb.pay.hero.bean.GoodsBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.encrypt.MSignUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by zll on 2018/5/19.
 */

public class GoodsEditorController extends BaseControllerWithActivity {
    public GoodsEditorController(BaseActivity baseActivity) {
        super(baseActivity);
    }

    /**
     * 获取订购单位
     *
     * @return
     */
    Observable<MBaseBean<List<BuyUnitBean>>> getUnits(String token, String signatrue
            , String timestamp) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(GoodsEditorRxAPI.class)
                .getUnits(token, signatrue, timestamp);
    }

    /**
     * 上传商品和修改商品
     *
     * @return
     */
    Observable<MBaseBean<String>> uploadGoods(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(GoodsEditorRxAPI.class)
                .uploadGoods(token, signatrue, timestamp, data);
    }

    /**
     * 更改上下架状态
     *
     * @return
     */
    Observable<MBaseBean<String>> changeStatus(String token, String signatrue
            , String timestamp, Map<String, String> data) {
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(GoodsEditorRxAPI.class)
                .changeStatus(token, signatrue, timestamp, data);
    }

    /**
     * 获取订购单位
     *
     * @return
     */
    public Observable<MBaseBean<List<BuyUnitBean>>> getUnits() {
        String token = mApplication.getToken();
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(null, token, timestamp);
        return getUnits(token, signatrue, timestamp);
    }

    /**
     * 上传商品和修改商品
     *
     * @param bean
     * @return
     */
    public Observable<MBaseBean<String>> uploadGoodsInfo(GoodsBean bean) {
        String token = mApplication.getToken();
        Map<String, String> data = new HashMap<>();
        if (bean.getId() != 0) {
            data.put("id", bean.getId() + "");
        }
        data.put("name", bean.getName());
        data.put("price", bean.getPrice());
        data.put("subtitle", bean.getSubtitle());
        data.put("main_image", bean.getMainImage());
        data.put("unit", bean.getUnit());
        data.put("status", bean.getStatus() + "");
        data.put("category_id", bean.getCategoryId() + "");
        data.put("freight", bean.getFreight());
        data.put("weight", bean.getWeight());
        data.put("sub_images", bean.getSubImages());
        if (!TextUtils.isEmpty(bean.getDetailImages())) {
            data.put("detail", bean.getDetailImages());
        }
        data.put("promotion", bean.getPromotion() + "");
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return uploadGoods(token, signatrue, timestamp, data);
    }

    /**
     * 更改上下架状态
     *
     * @return
     */
    public Observable<MBaseBean<String>> changeStatus(long goodId, int status) {
        String token = mApplication.getToken();
        Map<String, String> data = new HashMap<>();
        data.put("id", goodId + "");
        data.put("status", status + "");
        String timestamp = System.currentTimeMillis() + "";
        String signatrue = MSignUtils.getSign(data, token, timestamp);
        return changeStatus(token, signatrue, timestamp, data);
    }
}
