package com.tangxb.pay.hero.imageloader;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tangxb.pay.hero.R;

/**
 * Created by Tangxb on 2017/2/14.
 */

public class GlideLoaderInterImpl implements ImageLoaderInter {
    private int placeholderResId;
    private int errorResId;

    public GlideLoaderInterImpl() {
        placeholderResId = R.mipmap.ic_launcher;
        errorResId = R.mipmap.ic_launcher;
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
        Glide.with(activity).load(imageUrl)
                .placeholder(placeholderResId)
                .error(errorResId)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imageView);
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
        Glide.with(fragment).load(imageUrl)
                .placeholder(placeholderResId)
                .error(errorResId)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imageView);
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
        Glide.with(activity).load(imageUrl)
                .placeholder(placeholderResId)
                .error(errorResId)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);
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
        Glide.with(fragment).load(imageUrl)
                .placeholder(placeholderResId)
                .error(errorResId)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void resumeRequests(Activity activity) {
        if (Glide.with(activity).isPaused()) {
            Glide.with(activity).resumeRequests();
        }
    }

    @Override
    public void resumeRequests(Fragment fragment) {
        if (Glide.with(fragment).isPaused()) {
            Glide.with(fragment).resumeRequests();
        }
    }

    @Override
    public void pauseRequests(Activity activity) {
        if (!Glide.with(activity).isPaused()) {
            Glide.with(activity).pauseRequests();
        }
    }

    @Override
    public void pauseRequests(Fragment fragment) {
        if (!Glide.with(fragment).isPaused()) {
            Glide.with(fragment).pauseRequests();
        }
    }
}
