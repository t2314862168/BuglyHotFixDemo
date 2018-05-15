package com.tangxb.pay.hero.imageloader;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

/**
 * Created by Tangxb on 2017/2/14.
 */

public class GlideLoaderFactory extends ImageLoaderFactory {
    @Override
    ImageLoaderInter getImageLoaderInter() {
        return new GlideLoaderInterImpl();
    }
    /**
     * 加载普通的图片
     *
     * @param activity
     * @param imageResId
     * @param imageView
     */
    @Override
    public void loadCommonImgByUrl(Activity activity, int imageResId, ImageView imageView) {
        getImageLoaderInter().loadCommonImgByUrl(activity, imageResId, imageView);
    }

    /**
     * 加载普通的图片
     *
     * @param activity
     * @param imageUrl
     * @param imageView
     */
    @Override
    public void loadCommonImgByUrl(Activity activity, String imageUrl, ImageView imageView) {
        getImageLoaderInter().loadCommonImgByUrl(activity, imageUrl, imageView);
    }

    /**
     * 加载普通的图片
     *
     * @param fragment
     * @param imageUrl
     * @param imageView
     */
    @Override
    public void loadCommonImgByUrl(Fragment fragment, String imageUrl, ImageView imageView) {
        getImageLoaderInter().loadCommonImgByUrl(fragment, imageUrl, imageView);
    }

    /**
     * 加载圆形或者是圆角图片
     *
     * @param activity
     * @param imageUrl
     * @param imageView
     */
    @Override
    public void loadCircleOrReboundImgByUrl(Activity activity, String imageUrl, ImageView imageView) {
        getImageLoaderInter().loadCircleOrReboundImgByUrl(activity, imageUrl, imageView);
    }

    /**
     * 加载圆形或者是圆角图片
     *
     * @param fragment
     * @param imageUrl
     * @param imageView
     */
    @Override
    public void loadCircleOrReboundImgByUrl(Fragment fragment, String imageUrl, ImageView imageView) {
        getImageLoaderInter().loadCircleOrReboundImgByUrl(fragment, imageUrl, imageView);
    }

    @Override
    public void resumeRequests(Activity activity) {
        getImageLoaderInter().resumeRequests(activity);
    }

    @Override
    public void resumeRequests(Fragment fragment) {
        getImageLoaderInter().resumeRequests(fragment);
    }

    @Override
    public void pauseRequests(Activity activity) {
        getImageLoaderInter().pauseRequests(activity);
    }

    @Override
    public void pauseRequests(Fragment fragment) {
        getImageLoaderInter().pauseRequests(fragment);
    }
}
