package com.tangxb.pay.hero.controller;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.activity.BaseActivity;

/**
 * Created by tangxuebing on 2018/6/4.
 */

public class HomeController extends BaseControllerWithActivity {
    private Resources mResources;
    private int[] mColorIds = new int[]{R.color.color_6cbe4a, R.color.color_6ac0ff
            , R.color.color_d485f7, R.color.color_f2b100
            , R.color.color_fd7742, R.color.color_1dbfb9
            , R.color.color_7b5cde, R.color.color_red, R.color.main_color
    };
    private int[] mDrawableResIds = new int[]{R.drawable.ic_user_manger, R.drawable.ic_order_manger
            , R.drawable.ic_goods_manger, R.drawable.ic_data_statistics
            , R.drawable.ic_warehouse_manger, R.drawable.ic_deliver_goods_manger
            , R.drawable.ic_person_warehouse, R.drawable.ic_permission_manger, R.drawable.ic_personal_center
    };

    public HomeController(BaseActivity baseActivity) {
        super(baseActivity);
        mResources = baseActivity.getResources();
    }

    public int getItemBackgroundColor(String itemStr) {
        int resId = 0;
        if (itemStr.equals(mResources.getString(R.string.user_manger))) {
            resId = ContextCompat.getColor(baseActivity.get(), mColorIds[0]);
        } else if (itemStr.equals(mResources.getString(R.string.order_manger))) {
            resId = ContextCompat.getColor(baseActivity.get(), mColorIds[1]);
        } else if (itemStr.equals(mResources.getString(R.string.goods_manger))) {
            resId = ContextCompat.getColor(baseActivity.get(), mColorIds[2]);
        } else if (itemStr.equals(mResources.getString(R.string.deliver_goods_manger))) {
            resId = ContextCompat.getColor(baseActivity.get(), mColorIds[5]);
        } else if (itemStr.equals(mResources.getString(R.string.data_statistics))) {
            resId = ContextCompat.getColor(baseActivity.get(), mColorIds[3]);
        } else if (itemStr.equals(mResources.getString(R.string.person_warehouse))) {
            resId = ContextCompat.getColor(baseActivity.get(), mColorIds[6]);
        } else if (itemStr.equals(mResources.getString(R.string.warehouse_manger))) {
            resId = ContextCompat.getColor(baseActivity.get(), mColorIds[4]);
        } else if (itemStr.equals(mResources.getString(R.string.permission_manger))) {
            resId = ContextCompat.getColor(baseActivity.get(), mColorIds[7]);
        } else if (itemStr.equals(mResources.getString(R.string.personal_center))) {
            resId = ContextCompat.getColor(baseActivity.get(), mColorIds[8]);
        }
        return resId;
    }

    public Drawable getItemDrawable(String itemStr) {
        Drawable drawable = null;
        if (itemStr.equals(mResources.getString(R.string.user_manger))) {
            drawable = ContextCompat.getDrawable(baseActivity.get(), mDrawableResIds[0]);
        } else if (itemStr.equals(mResources.getString(R.string.order_manger))) {
            drawable = ContextCompat.getDrawable(baseActivity.get(), mDrawableResIds[1]);
        } else if (itemStr.equals(mResources.getString(R.string.goods_manger))) {
            drawable = ContextCompat.getDrawable(baseActivity.get(), mDrawableResIds[2]);
        } else if (itemStr.equals(mResources.getString(R.string.deliver_goods_manger))) {
            drawable = ContextCompat.getDrawable(baseActivity.get(), mDrawableResIds[5]);
        } else if (itemStr.equals(mResources.getString(R.string.data_statistics))) {
            drawable = ContextCompat.getDrawable(baseActivity.get(), mDrawableResIds[3]);
        } else if (itemStr.equals(mResources.getString(R.string.person_warehouse))) {
            drawable = ContextCompat.getDrawable(baseActivity.get(), mDrawableResIds[6]);
        } else if (itemStr.equals(mResources.getString(R.string.warehouse_manger))) {
            drawable = ContextCompat.getDrawable(baseActivity.get(), mDrawableResIds[4]);
        } else if (itemStr.equals(mResources.getString(R.string.permission_manger))) {
            drawable = ContextCompat.getDrawable(baseActivity.get(), mDrawableResIds[7]);
        } else if (itemStr.equals(mResources.getString(R.string.personal_center))) {
            drawable = ContextCompat.getDrawable(baseActivity.get(), mDrawableResIds[8]);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

}
