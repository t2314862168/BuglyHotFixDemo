package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.BaseBean;
import com.tangxb.pay.hero.bean.WelfareBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Tangxb on 2016/11/4.
 */

public interface WelfareAPI {
    @GET("data/{welfare}/{pageSize}/{pageNum}")
    Call<BaseBean<List<WelfareBean>>> getExternalBean(@Path("welfare") String welfare, @Path("pageSize") int pageSize, @Path("pageNum") int pageNum);

    /**
     * 跨过BaseUrl使用url直接访问
     *
     * @param url
     * @return
     */
    @GET
    Call<BaseBean<List<WelfareBean>>> getExternalBeanByNeedUrl(@Url String url);
}
