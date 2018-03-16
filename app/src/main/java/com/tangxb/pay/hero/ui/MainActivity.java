package com.tangxb.pay.hero.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.RetrofitClient;
import com.tangxb.pay.hero.api.ApiFactory;
import com.tangxb.pay.hero.bean.BaseBean;
import com.tangxb.pay.hero.bean.WelfareBean;
import com.tangxb.pay.hero.okhttp.OkHttpUtils;
import com.tangxb.pay.hero.okhttp.callback.StringCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testOkHttpPostForm();
//        testRetrofit();
    }

    /**
     * <a href="https://github.com/jeasonlzy/okhttp-OkGo/blob/master/app/src/main/java/com/lzy/demo/utils/Urls.java">可以测试使用的地址</a>
     */
    private void testOkHttpPostForm() {
        String url = "http://server.jeasonlzy.com/OkHttpUtils/upload";
        OkHttpUtils.post().tag(TAG).url(url).addParams("key1", "value").build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                System.out.println();
            }

            @Override
            public void onResponse(String response, int id) {
                System.out.println();
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                System.out.println();
            }
        });
    }

    private void testRetrofit() {
        Call<BaseBean<List<WelfareBean>>> externalBeanCall = ApiFactory.getWelfareAPI().getExternalBean("福利", 20, 1);
        RetrofitClient.enqueue(TAG, "baiiu02", externalBeanCall, new Callback<BaseBean<List<WelfareBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<WelfareBean>>> call, Response<BaseBean<List<WelfareBean>>> response) {
                System.out.println();
            }

            @Override
            public void onFailure(Call<BaseBean<List<WelfareBean>>> call, Throwable t) {
                System.out.println();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RetrofitClient.cancel(TAG);
    }
}
