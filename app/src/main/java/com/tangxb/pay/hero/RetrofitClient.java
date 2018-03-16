package com.tangxb.pay.hero;

import com.tangxb.pay.hero.api.ApiContants;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tangxb on 2016/11/4.
 */

public enum RetrofitClient {

    INSTANCE;

    private final Retrofit retrofit;

    RetrofitClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            // 日志拦截器
//            builder.addNetworkInterceptor(loggingInterceptor);
            // 使用Chrome调试
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        OkHttpClient client = builder.build();
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(client)
                //baseUrl
                .baseUrl(ApiContants.BASEURL)
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static void enqueue(final Object tag, final String url, Call call, final Callback callback) {
        putCall(tag, url, call);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                callback.onResponse(call, response);
                if (tag != null)
                    removeCall(url);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                callback.onFailure(call, t);
                if (tag != null)
                    removeCall(url);
            }
        });
    }

    static Map<String, Call> CALL_MAP = new HashMap<>();

    private static synchronized void putCall(Object tag, String url, Call call) {
        if (tag == null)
            return;
        synchronized (CALL_MAP) {
            CALL_MAP.put(tag.toString() + url, call);
        }
    }

    /**
     * 取消某个界面都所有请求，或者是取消某个tag的所有请求
     * 如果要取消某个tag单独请求，tag需要转入tag+url
     */
    public static synchronized void cancel(Object tag) {
        if (tag == null)
            return;
        List<String> list = new ArrayList<>();
        synchronized (CALL_MAP) {
            for (String key : CALL_MAP.keySet()) {
                if (key.startsWith(tag.toString())) {
                    CALL_MAP.get(key).cancel();
                    list.add(key);
                }
            }
        }
        for (String s : list) {
            removeCall(s);
        }
    }

    /**
     * 移除某个请求
     */
    private static synchronized void removeCall(String url) {
        synchronized (CALL_MAP) {
            for (String key : CALL_MAP.keySet()) {
                if (key.contains(url)) {
                    url = key;
                    break;
                }
            }
            CALL_MAP.remove(url);
        }
    }
}
