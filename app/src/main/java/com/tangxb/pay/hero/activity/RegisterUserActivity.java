package com.tangxb.pay.hero.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.controller.ChooseCityController;
import com.tangxb.pay.hero.controller.ChooseSexController;
import com.tangxb.pay.hero.controller.RegisterUserController;
import com.tangxb.pay.hero.controller.VerifyCodeController;
import com.tangxb.pay.hero.listener.SimpleResultListener;
import com.tangxb.pay.hero.util.AccountValidatorUtil;
import com.tangxb.pay.hero.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by tangxuebing on 2018/5/16.
 */

public class RegisterUserActivity extends BaseActivityWithTitleOnly {
    @BindView(R.id.et_name)
    EditText mNameEt;
    @BindView(R.id.et_pwd)
    EditText mPwdEt;
    @BindView(R.id.et_sure_pwd)
    EditText mSurePwdEt;
    @BindView(R.id.tv_sex)
    TextView mSexTv;
    @BindView(R.id.tv_city)
    TextView mCityTv;
    @BindView(R.id.et_address)
    EditText mAddressEt;
    @BindView(R.id.et_phone)
    EditText mPhoneEt;
    @BindView(R.id.et_verify_code)
    EditText mVerCodeEt;
    @BindView(R.id.btn_get_verify_code)
    Button mVerCodeBtn;
    @BindView(R.id.btn_commit)
    Button mCommitBtn;
    VerCodeTimer verCodeTimer;
    ChooseSexController sexController;
    ChooseCityController cityController;
    VerifyCodeController codeController;
    RegisterUserController userController;
    String areaId;
    String areaName;
    int chooseSex = -1;

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
        return R.layout.activity_register_user;
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.register_user);
        sexController = new ChooseSexController(this, new ChooseSexController.ChooseListener() {
            @Override
            public void chooseArea(int sexIndex, String str) {
                chooseSex = sexIndex;
                mSexTv.setText(str);
            }
        });
        cityController = new ChooseCityController(this, new ChooseCityController.ChooseListener() {
            @Override
            public void chooseArea(String parentId, String parentName) {
                areaId = parentId;
                areaName = parentName;
                mCityTv.setText(parentName);
            }
        });
        codeController = new VerifyCodeController(this);
        userController = new RegisterUserController(this);
    }

    /**
     * 选择性别
     *
     * @param view
     */
    @OnClick(R.id.tv_sex)
    public void clickSex(View view) {
        sexController.chooseSex();
    }

    /**
     * 选择城市
     *
     * @param view
     */
    @OnClick(R.id.tv_city)
    public void clickCity(View view) {
        cityController.chooseArea(null, null);
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
        String phone = mPhoneEt.getText().toString();
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
        if (map == null) return;
        String code = mVerCodeEt.getText().toString();
        if (code.length() < 4) {
            ToastUtils.t(mApplication, "验证码不正确");
            return;
        }
        if (map == null) {
            ToastUtils.t(mApplication, "验证码不完整");
            return;
        }
        map.put("vercode", code);
        userController.registerUser(map, new SimpleResultListener() {
            @Override
            public void doSuccess() {
                mActivity.finish();
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
        final String phone = mPhoneEt.getText().toString();
        String address = mAddressEt.getText().toString();
        String nickName = mNameEt.getText().toString();
        String area = mCityTv.getText().toString();
        if (nickName.trim().length() == 0) {
            ToastUtils.t(mApplication, "请输入姓名");
            return null;
        }
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
        if (!AccountValidatorUtil.isName(nickName.trim())) {
            ToastUtils.t(mApplication, "请输入真实姓名");
            return null;
        }
        if (chooseSex == -1) {
            ToastUtils.t(mApplication, "性别不能为空");
            return null;
        }
        if (area.trim().length() < 3) {
            ToastUtils.t(mApplication, "省市县不能为空");
            return null;
        }
        if (address.trim().length() < 3) {
            ToastUtils.t(mApplication, "详细地址不能少于3个字");
            return null;
        }

        Map<String, String> data = new HashMap<String, String>();
        data.put("realname", nickName);
        data.put("mobile", phone);
        data.put("password", passNew);
        data.put("mobile", phone);
        data.put("city", area);
        data.put("address", address);
        data.put("sex", chooseSex + "");
        return data;
    }
}
