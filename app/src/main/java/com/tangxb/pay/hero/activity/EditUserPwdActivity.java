package com.tangxb.pay.hero.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.controller.EditUserInfoController;
import com.tangxb.pay.hero.controller.VerifyCodeController;
import com.tangxb.pay.hero.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/5/20 0020.
 */

public class EditUserPwdActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.et_pwd)
    EditText mPwdEt;
    @BindView(R.id.et_sure_pwd)
    EditText mSurePwdEt;
    @BindView(R.id.et_verify_code)
    EditText mVerCodeEt;
    @BindView(R.id.btn_get_verify_code)
    Button mVerCodeBtn;
    @BindView(R.id.btn_commit)
    Button mCommitBtn;
    VerCodeTimer verCodeTimer;
    VerifyCodeController codeController;
    EditUserInfoController editUserInfoController;
    long currentUserId;
    String phone;

    /**
     * 获取验证码
     */
    class VerCodeTimer extends CountDownTimer {
        public VerCodeTimer() {
            super(10 * 1000L, 1000L);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int leaveSecond = (int) (millisUntilFinished / 1000L);
            mVerCodeBtn.setText(String.format(mResources.getString(R.string.get_verify_code_2), leaveSecond));
        }

        @Override
        public void onFinish() {
            mVerCodeBtn.setEnabled(true);
            mVerCodeBtn.setText(R.string.get_verify_code);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_edit_user_pwd;
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        currentUserId = intent.getLongExtra("currentUserId", 0);
        phone = intent.getStringExtra("phone");
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.update_pwd);

        codeController = new VerifyCodeController(this);
        editUserInfoController = new EditUserInfoController(this);
    }

    /**
     * 获取验证码
     *
     * @param view
     */
    @OnClick(R.id.btn_get_verify_code)
    public void clickGetVerCode(View view) {
        final Map<String, String> map = checkData();
        if (map == null) return;
        codeController.getVerCode(phone);
        view.setEnabled(false);
        verCodeTimer = new VerCodeTimer();
        verCodeTimer.start();
    }

    /**
     * 点击提交
     *
     * @param view
     */
    @OnClick(R.id.btn_commit)
    public void clickCommit(View view) {
        final Map<String, String> map = checkData();
        String code = mVerCodeEt.getText().toString();
        if (map == null) {
            ToastUtils.t(mApplication, "信息不完整");
            return;
        }
        if (code.length() < 4) {
            ToastUtils.t(mApplication, "验证码不正确");
            return;
        }
        String passNew = mPwdEt.getText().toString();
        addSubscription(editUserInfoController.updateUserPwd(currentUserId, passNew, code, phone), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> baseBean) throws Exception {
                ToastUtils.t(mApplication, baseBean.getMessage());
                finish();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.t(mApplication, throwable.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (verCodeTimer != null) {
            verCodeTimer.cancel();
            verCodeTimer = null;
        }
    }

    /**
     * 检查数据完整性
     *
     * @return
     */
    private Map<String, String> checkData() {
        String passNew = mPwdEt.getText().toString();
        String passNew1 = mSurePwdEt.getText().toString();

        if (passNew.trim().length() == 0) {
            ToastUtils.t(mApplication, "请输入密码");
            return null;
        }
        if (!passNew.equals(passNew1)) {
            ToastUtils.t(mApplication, "两次密码不一致");
            return null;
        }
        if (phone == null || phone.length() != 11) {
            ToastUtils.t(mApplication, "电话号码不正确");
            return null;
        }
        Map<String, String> data = new HashMap<>();
        return data;
    }
}
