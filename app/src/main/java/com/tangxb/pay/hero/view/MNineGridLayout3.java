package com.tangxb.pay.hero.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.tangxb.pay.hero.MApplication;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.event.ChooseDetailImgEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by tangxuebing on 2018/5/15.
 */

public class MNineGridLayout3 extends NineGridLayout3 {

    public MNineGridLayout3(Context context) {
        super(context);
    }

    public MNineGridLayout3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(RatioImageView imageView, String url, int parentWidth) {
        return true;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        MApplication mApplication = (MApplication) mContext.getApplicationContext();
        Activity activity = (Activity) mContext;
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.image_show_piceker_add);
        } else {
            mApplication.getImageLoaderFactory().loadCommonImgByUrl(activity, url, imageView);
        }
    }

    @Override
    protected void onClickImage(int position, String url, List<String> urlList) {
        if (TextUtils.isEmpty(url)) {
            EventBus.getDefault().post(new ChooseDetailImgEvent());
        }
    }
}