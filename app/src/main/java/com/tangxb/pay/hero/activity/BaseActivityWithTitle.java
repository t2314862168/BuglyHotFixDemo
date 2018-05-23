package com.tangxb.pay.hero.activity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.util.DensityUtils;

/**
 * Created by Administrator on 2018/5/14 0014.
 */

public abstract class BaseActivityWithTitle extends BaseActivity {
    protected View mTitleView;
    protected TextView mLeftTv;
    protected TextView mTitleTv;
    protected TextView mRightTv;

    /**
     * 点击右边按钮事件
     */
    public abstract void clickRightBtn();

    /**
     * 点击左边按钮事件
     */
    public abstract void clickLeftBtn();

    public void handleTitle() {
        mTitleView = View.inflate(mActivity, R.layout.layout_common_title, null);
        int dip50 = DensityUtils.dip2px(mActivity, 50f);
        FrameLayout contentView = (FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT
                , dip50);
        View rootView = contentView.getChildAt(0);
        contentView.addView(mTitleView, 0, layoutParams);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        layoutParams2.topMargin = dip50;
        rootView.setLayoutParams(layoutParams2);

        mLeftTv = findView(mTitleView, R.id.btn_left);
        mTitleTv = findView(mTitleView, R.id.tv_title);
        mRightTv = findView(mTitleView, R.id.btn_right);
        mRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRightBtn();
            }
        });
    }

    public void setLeftText(int resId) {
        mLeftTv.setText(mResources.getText(resId));
    }

    public void setLeftText(String text) {
        mLeftTv.setText(text);
    }

    public void setMiddleText(int resId) {
        mTitleTv.setText(mResources.getText(resId));
    }

    public void setMiddleText(String text) {
        mTitleTv.setText(text);
    }

    public void setRightText(int resId) {
        mRightTv.setText(mResources.getText(resId));
    }

    public void setRightText(String text) {
        mRightTv.setText(text);
    }
}
