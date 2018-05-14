package com.tangxb.pay.hero.activity;

import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.util.DensityUtils;

/**
 * Created by tangxuebing on 2018/5/14.
 */

public abstract class BaseActivityWithSearch extends BaseActivity {
    protected View mSearchTitleView;
    protected TextView mLeftBtn;
    protected TextView middleTv;
    protected ImageButton mSearchIb;
    protected EditText mSearchEt;
    protected Animation showAnim;
    protected Animation hiddenAnim;

    public void handleSearchTitle() {
        mSearchTitleView = View.inflate(mActivity, R.layout.layout_common_title_with_search, null);
        int dip50 = DensityUtils.dip2px(mActivity, 50f);
        FrameLayout contentView = (FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT
                , dip50);
        View rootView = contentView.findViewById(R.id.root_container);
        contentView.addView(mSearchTitleView, 0, layoutParams);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        layoutParams2.topMargin = dip50;
        rootView.setLayoutParams(layoutParams2);

        initSearchAnim();
        mLeftBtn = findView(mSearchTitleView, R.id.btn_left);
        middleTv = findView(mSearchTitleView, R.id.tv_middle);
        mSearchIb = findView(mSearchTitleView, R.id.ib_right);
        mSearchEt = findView(mSearchTitleView, R.id.et_search);
        // 默认不可见
        mSearchEt.setVisibility(View.GONE);
        mSearchIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRightIb();
            }
        });
        mSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchEvent();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 点击左边的按钮处理
     */
    public abstract void clickLeftBtn();

    public void setLeftBtnText(int resId) {
        mLeftBtn.setText(mResources.getText(resId));
        mLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLeftBtn();
            }
        });
    }

    public void setLeftBtnText(String text, View.OnClickListener listener) {
        mLeftBtn.setText(text);
        mLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLeftBtn();
            }
        });
    }

    public void setMiddleText(int resId) {
        middleTv.setText(mResources.getText(resId));
    }

    public void setMiddleText(String text) {
        middleTv.setText(text);
    }

    /**
     * 点击右边的搜索按钮
     */
    public void clickRightIb() {
        if (mSearchEt.getVisibility() == View.GONE) {
            mSearchEt.startAnimation(showAnim);
            mSearchEt.setVisibility(View.VISIBLE);
            mSearchEt.setFocusable(true);
            mSearchEt.setFocusableInTouchMode(true);
            showSoftInput(mActivity, mSearchEt);
        } else {
            hideSoftInput(mActivity, mSearchEt);
            String content = mSearchEt.getText().toString();
            mSearchEt.startAnimation(hiddenAnim);
            middleTv.setText(content);
        }
    }

    /**
     * 软键盘里面的搜索按钮事件
     */
    public void searchEvent() {
        hideSoftInput(mActivity, mSearchEt);
        String content = mSearchEt.getText().toString();
        mSearchEt.startAnimation(hiddenAnim);
        middleTv.setText(content);
    }

    /**
     * 搜索动画
     */
    public void initSearchAnim() {
        showAnim = AnimationUtils.loadAnimation(this, R.anim.scale_show);
        hiddenAnim = AnimationUtils.loadAnimation(this, R.anim.scale_hidden);
        hiddenAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSearchEt.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
