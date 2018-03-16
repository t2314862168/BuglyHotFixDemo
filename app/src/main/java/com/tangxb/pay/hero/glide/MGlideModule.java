package com.tangxb.pay.hero.glide;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * 设置Glide缓存目录
 * Created by Tangxb on 2016/7/11.
 */
public class MGlideModule implements GlideModule {
    /**
     * 1M = 1024k
     * 1k = 1024字节
     * 20M的大小
     */
    public static final int DISK_CACHE_SIZE = 20 * 1024 * 1024;
    public static final String DISK_CACHE_NAME = "glide";

    /**
     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                builder.setDiskCache(new DiskLruCacheFactory(context.getExternalCacheDir().getAbsolutePath()
                        , DISK_CACHE_NAME, DISK_CACHE_SIZE));
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
