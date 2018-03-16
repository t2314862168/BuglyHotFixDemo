package com.tangxb.pay.hero.imageloader;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

/**
 * Created by Tangxb on 2017/2/14.
 */

public abstract class ImageLoaderFactory {
    abstract ImageLoaderInter getImageLoaderInter();

    /**
     * 加载普通的图片
     *
     * @param activity
     * @param imageUrl
     * @param imageView
     */
    public abstract void loadCommonImgByUrl(Activity activity, String imageUrl, ImageView imageView);

    /**
     * 加载普通的图片
     *
     * @param fragment
     * @param imageUrl
     * @param imageView
     */
    public abstract void loadCommonImgByUrl(Fragment fragment, String imageUrl, ImageView imageView);

    /**
     * 加载圆形或者是圆角图片
     *
     * @param activity
     * @param imageUrl
     * @param imageView
     */
    public abstract void loadCircleOrReboundImgByUrl(Activity activity, String imageUrl, ImageView imageView);

    /**
     * 加载圆形或者是圆角图片
     *
     * @param fragment
     * @param imageUrl
     * @param imageView
     */
    public abstract void loadCircleOrReboundImgByUrl(Fragment fragment, String imageUrl, ImageView imageView);

    public abstract void resumeRequests(Activity activity);

    public abstract void resumeRequests(Fragment fragment);

    public abstract void pauseRequests(Activity activity);

    public abstract void pauseRequests(Fragment fragment);
}
