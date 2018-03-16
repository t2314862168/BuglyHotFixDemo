package com.tangxb.pay.hero.imageloader;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

/**
 * Created by Tangxb on 2017/2/14.
 */

public interface ImageLoaderInter {
    /**
     * 加载普通的图片
     *
     * @param activity
     * @param imageUrl
     * @param imageView
     */
    void loadCommonImgByUrl(Activity activity, String imageUrl, ImageView imageView);

    /**
     * 加载普通的图片
     *
     * @param fragment
     * @param imageUrl
     * @param imageView
     */
    void loadCommonImgByUrl(Fragment fragment, String imageUrl, ImageView imageView);

    /**
     * 加载圆形或者是圆角图片
     *
     * @param activity
     * @param imageUrl
     * @param imageView
     */
    void loadCircleOrReboundImgByUrl(Activity activity, String imageUrl, ImageView imageView);

    /**
     * 加载圆形或者是圆角图片
     *
     * @param fragment
     * @param imageUrl
     * @param imageView
     */
    void loadCircleOrReboundImgByUrl(Fragment fragment, String imageUrl, ImageView imageView);

    void resumeRequests(Activity activity);

    void resumeRequests(Fragment fragment);

    void pauseRequests(Activity activity);

    void pauseRequests(Fragment fragment);
}
