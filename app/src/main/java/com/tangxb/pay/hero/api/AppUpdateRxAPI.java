package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.AppUpdateBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface AppUpdateRxAPI {

    /**
     * 检查app版本信息
     *
     * @param versionCode
     * @param packageName
     * @return
     */
    @GET("common/checkVersion")
    Observable<AppUpdateBean> getAppUpdate(@Query("code") String versionCode
            , @Query("packageName") String packageName);
}
