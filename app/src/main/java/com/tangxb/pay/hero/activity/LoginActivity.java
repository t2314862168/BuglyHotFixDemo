package com.tangxb.pay.hero.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.controller.LoginController;
import com.tangxb.pay.hero.entity.UserEntity;
import com.tangxb.pay.hero.help.KeyboardWatcher;
import com.tangxb.pay.hero.util.ConstUtils;
import com.tangxb.pay.hero.util.SPUtils;
import com.tangxb.pay.hero.util.ToastUtils;
import com.tangxb.pay.hero.view.AlertProgressDialog;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class LoginActivity extends BaseActivity implements KeyboardWatcher.SoftKeyboardStateListener {
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.til_account)
    TextInputLayout til_account;
    @BindView(R.id.til_password)
    TextInputLayout til_password;
    @BindView(R.id.tv_remember_password)
    TextView mRememberPwdTv;
    @BindView(R.id.iv_clean_phone)
    ImageView iv_clean_phone;
    @BindView(R.id.clean_password)
    ImageView clean_password;
    @BindView(R.id.iv_show_pwd)
    ImageView iv_show_pwd;
    @BindView(R.id.card_view)
    View cardView;
    private boolean mRememberPwdFlag;
    private int i = -1;
    private AlertDialog mAlertDialog;
    private KeyboardWatcher keyboardWatcher;
    private int screenHeight = 0;//屏幕高度
    /**
     * 是否显示密码
     */
    private boolean flag = false;
    private LoginController controller;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        StatusBarCompat.translucentStatusBar(mActivity);
        buildListener();
        boolean contains = SPUtils.contains(mApplication, ConstUtils.ACCOUNT_KEY);
        if (contains) {
            String username = (String) SPUtils.get(mApplication, ConstUtils.ACCOUNT_KEY, "");
            String password = (String) SPUtils.get(mApplication, ConstUtils.PASSWORD_KEY, "");
            til_account.getEditText().setText(username);
            til_password.getEditText().setText(password);
            mRememberPwdFlag = true;
            int resId = mRememberPwdFlag ? R.drawable.ic_check_box_red_400_24dp : R.drawable.ic_check_box_outline_blank_grey_700_24dp;
            Drawable drawable = ContextCompat.getDrawable(mActivity, resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mRememberPwdTv.setCompoundDrawables(drawable, null, null, null);
        }

        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyboardWatcher = new KeyboardWatcher(findViewById(Window.ID_ANDROID_CONTENT));
        keyboardWatcher.addSoftKeyboardStateListener(this);

        String account = til_account.getEditText().getText().toString();
        if (!TextUtils.isEmpty(account)) {
            til_account.getEditText().setSelection(account.length());
        }
        controller = new LoginController();
    }

    public void buildListener() {
        til_account.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && iv_clean_phone.getVisibility() == View.GONE) {
                    iv_clean_phone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    iv_clean_phone.setVisibility(View.GONE);
                }
            }
        });
        til_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && clean_password.getVisibility() == View.GONE) {
                    clean_password.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    clean_password.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    ToastUtils.t(mApplication, mResources.getString(R.string.please_input_limit_pwd));
                    s.delete(temp.length() - 1, temp.length());
                    til_password.getEditText().setSelection(s.length());
                }
            }
        });
    }

    /**
     * 清除用户名
     *
     * @param view
     */
    @OnClick(R.id.iv_clean_phone)
    public void clickCleanAccount(View view) {
        til_account.getEditText().setText("");
    }

    /**
     * 清除密码
     *
     * @param view
     */
    @OnClick(R.id.clean_password)
    public void clickCleanPwd(View view) {
        til_password.getEditText().setText("");
    }

    /**
     * 显示密码
     *
     * @param view
     */
    @OnClick(R.id.iv_show_pwd)
    public void clickShowPwd(View view) {
        if (flag == true) {
            til_password.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
            iv_show_pwd.setImageResource(R.drawable.pass_gone);
            flag = false;
        } else {
            til_password.getEditText().setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            iv_show_pwd.setImageResource(R.drawable.pass_visuable);
            flag = true;
        }
        String pwd = til_password.getEditText().getText().toString();
        if (!TextUtils.isEmpty(pwd)) {
            til_password.getEditText().setSelection(pwd.length());
        }
    }

    /**
     * 点击登录按钮
     *
     * @param view
     */
    @OnClick(R.id.btn_login)
    public void clickLoginBtn(View view) {
        String account = til_account.getEditText().getText().toString();
        String password = til_password.getEditText().getText().toString();
        til_account.setErrorEnabled(false);
        til_password.setErrorEnabled(false);
        //验证用户名和密码
        if (validateAccount(account) && validatePassword(password)) {
            saveAccountLoginInfo(account, password);
            showDialog();
            doLogin();
        }
    }

    private void doLogin() {
        addSubscription(controller.login(), new Consumer<UserEntity>() {
            @Override
            public void accept(UserEntity userEntity) throws Exception {
                loginSuccess();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                loginError();
            }
        });
    }

    /**
     * 保存用户名和密码到本地
     *
     * @param username
     * @param password
     */
    private void saveAccountLoginInfo(String username, String password) {
        if (!mRememberPwdFlag) return;
        boolean contains = SPUtils.contains(mApplication, ConstUtils.ACCOUNT_KEY);
        String username_1 = (String) SPUtils.get(mApplication, ConstUtils.ACCOUNT_KEY, "");
        String password_1 = (String) SPUtils.get(mApplication, ConstUtils.PASSWORD_KEY, "");
        if (!TextUtils.isEmpty(username) && username.trim().length() > 0 && !username.equals(username_1)) {
            SPUtils.put(mApplication, ConstUtils.ACCOUNT_KEY, username);
        }
        if (!TextUtils.isEmpty(password) && password.trim().length() > 0 && !password.equals(password_1)) {
            SPUtils.put(mApplication, ConstUtils.PASSWORD_KEY, password);
        }
    }

    /**
     * 显示登录进度框
     */
    public void showDialog() {
        mAlertDialog = new AlertProgressDialog.Builder(mActivity)
                .setView(R.layout.layout_alert_dialog)
                .setCancelable(false)
                .setMessage(R.string.login_ing)
                .show();
    }

    /**
     * 隐藏登录进度框
     */
    public void hiddenDialog() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }

    /**
     * 登录失败
     */
    public void loginError() {
        hiddenDialog();
        ToastUtils.t(mApplication, mResources.getString(R.string.login_error));
    }

    /**
     * 登录成功
     */
    public void loginSuccess() {
        hiddenDialog();
        ToastUtils.t(mApplication, mResources.getString(R.string.login_success));
        Intent intent = new Intent(mActivity, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 验证用户名
     *
     * @param account
     * @return
     */
    private boolean validateAccount(String account) {
        if (TextUtils.isEmpty(account)) {
            showError(til_account, "用户名不能为空");
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     *
     * @param password
     * @return 验证密码长度
     */
    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            showError(til_password, "密码不能为空");
            return false;
        }
        if (password.length() < 2 || password.length() > 18) {
            showError(til_password, "密码长度为2-18位");
            return false;
        }
        return true;
    }

    /**
     * 显示错误提示，并获取焦点
     *
     * @param textInputLayout
     * @param error
     */
    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    @OnClick(R.id.tv_remember_password)
    public void clickRememberPwdTv(View view) {
        mRememberPwdFlag = !mRememberPwdFlag;
        int resId = mRememberPwdFlag ? R.drawable.ic_check_box_red_400_24dp : R.drawable.ic_check_box_outline_blank_grey_700_24dp;
        Drawable drawable = ContextCompat.getDrawable(mActivity, resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mRememberPwdTv.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardWatcher.removeSoftKeyboardStateListener(this);
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardSize) {
        int[] location = new int[2];
        cardView.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int bottom = screenHeight - (y + cardView.getHeight());
        if (keyboardSize > bottom) {
            ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(cardView, "translationY", 0.0f, -(keyboardSize - bottom));
            mAnimatorTranslateY.setDuration(300);
            mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimatorTranslateY.start();
        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(cardView, "translationY", cardView.getTranslationY(), 0);
        mAnimatorTranslateY.setDuration(300);
        mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorTranslateY.start();
    }
}
