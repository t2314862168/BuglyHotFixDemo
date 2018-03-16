package com.tangxb.pay.hero.api;

import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.bean.PurchaseUserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface TaskRxAPI {
    @GET("back/user/getPurchase")
    Observable<MBaseBean<List<PurchaseUserBean>>> getPurchaseUserList(@Header("token") String token, @Header("signatrue") String signatrue,
                                                    @Header("timestamp") String timestamp, @Query("page") int page,
                                                    @Query("rows") int rows);
}
