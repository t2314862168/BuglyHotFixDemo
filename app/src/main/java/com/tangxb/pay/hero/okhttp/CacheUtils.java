package com.tangxb.pay.hero.okhttp;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by Tangxb on 2017/2/22.
 */

public class CacheUtils {
    /**
     * 1M = 1024k
     * 1k = 1024字节
     * 10M的大小
     */
    public static final int DISK_CACHE_SIZE = 10 * 1024 * 1024;
    public static final String DISK_CACHE_NAME = "okhttp_cache";
    private static Context CONTEXT;

    public static void setContext(Context context) {
        CacheUtils.CONTEXT = context;
    }

    public static Cache getCacheDir() {
        Cache cache = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                cache = new Cache(new File(CONTEXT.getExternalCacheDir(), DISK_CACHE_NAME), DISK_CACHE_SIZE);
            } catch (Exception e) {
            }
        }
        return cache;
    }
}
