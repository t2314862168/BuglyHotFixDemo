package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.AreaBean;
import com.tangxb.pay.hero.bean.MBaseBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by tangxuebing on 2018/5/16.
 */

public interface ChooseCityRxAPI {
    /**
     * 获取区域
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param parentId  如果没有父id,则传0
     * @return
     */
    @FormUrlEncoded
    @POST("common/getArea")
    Observable<MBaseBean<List<AreaBean>>> getArea(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @Field("parentId") String parentId);
}
