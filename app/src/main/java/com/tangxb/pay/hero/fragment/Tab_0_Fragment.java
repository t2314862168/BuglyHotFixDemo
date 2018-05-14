package com.tangxb.pay.hero.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.activity.OptionsScannerActivity;
import com.tangxb.pay.hero.util.MLogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by tangxuebing on 2018/5/8.
 */

public class Tab_0_Fragment extends BaseFragment {
    @BindView(R.id.btn_0)
    Button mBtn0;
    @BindView(R.id.btn_1)
    Button mBtn1;
    private static final String TAG = Tab_0_Fragment.class.getSimpleName();

    public static Tab_0_Fragment getInstance(String fragmentName) {
        Tab_0_Fragment fragment = new Tab_0_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("fragmentName", fragmentName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_0;
    }

    @Override
    protected void receiveBundleFromActivity(Bundle arg) {
        String fragmentName = arg.getString("fragmentName");
        if (!TextUtils.isEmpty(fragmentName)) {
            MLogUtils.d(TAG, "fragmentName===" + fragmentName);
        }
    }

    @OnClick(R.id.btn_0)
    public void clickBtn0(View view) {
        Intent intent = new Intent(mActivity, OptionsScannerActivity.class);
        intent.putExtra("msg", "扫码收件");
        startActivity(intent);
    }

    @OnClick(R.id.btn_1)
    public void clickBtn1(View view) {
        Intent intent = new Intent(mActivity, OptionsScannerActivity.class);
        intent.putExtra("msg", "扫码收货");
        startActivity(intent);
    }
}
