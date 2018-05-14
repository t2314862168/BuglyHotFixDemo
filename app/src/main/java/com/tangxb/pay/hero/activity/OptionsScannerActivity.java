package com.tangxb.pay.hero.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.ScannerView;
import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by tangxuebing on 2018/5/8.
 */

public class OptionsScannerActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, OnScannerCompletionListener {
    @BindView(R.id.scanner_view)
    ScannerView mScannerView;
    final int RC_CAMERA_PERM = 122;
    final String TAG = getClass().getSimpleName();
    private Vibrator mVibrator;
    private String msg;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_scanner_options;
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        msg = intent.getStringExtra("msg");
    }

    @Override
    protected void initData() {
        mVibrator = (Vibrator) mActivity.getSystemService(VIBRATOR_SERVICE);
        applyCameraPermission();

        ScannerOptions.Builder builder = new ScannerOptions.Builder();
        builder
//                .setFrameStrokeColor(Color.RED)
//                .setFrameStrokeWidth(1.5f)
//                .setFrameSize(256, 256)
//                .setFrameCornerLength(22)
//                .setFrameCornerWidth(2)
//                .setFrameCornerColor(0xff06c1ae)
//                .setFrameCornerInside(true)

//                .setLaserLineColor(0xff06c1ae)
//                .setLaserLineHeight(18)

//                .setLaserStyle(ScannerOptions.LaserStyle.RES_LINE,R.mipmap.wx_scan_line)

//                .setLaserStyle(ScannerOptions.LaserStyle.RES_GRID, R.mipmap.zfb_grid_scan_line)//网格图
//                .setFrameCornerColor(0xFF26CEFF)//支付宝颜色

//                .setScanFullScreen(true)

//                .setFrameHide(false)
//                .setFrameCornerHide(false)
//                .setLaserMoveFullScreen(false)
                .setScanMode(BarcodeFormat.QR_CODE)
//                .setTipText("请联系其它已添加该设备用户获取二维码")
                .setTipTextSize(19)
                .setTipTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryDark))
//                .setCameraZoomRatio(2)
        ;
        if (TextUtils.isEmpty(msg)) {
            msg = "随便扫点什么吧";
        }
        builder.setTipText(msg);
        mScannerView.setScannerOptions(builder.build());
    }

    @Override
    protected void initListener() {
        mScannerView.setOnScannerCompletionListener(this);
    }

    @Override
    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        ToastUtils.t(mApplication, rawResult.getText());
        vibrate();
        mScannerView.restartPreviewAfterDelay(0);
    }

    private void vibrate() {
        mVibrator.vibrate(200);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mScannerView.onResume();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mScannerView.onPause();
        } catch (Exception e) {
        }
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void applyCameraPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Log.d(TAG, "hasPermissions true");
        } else {
            EasyPermissions.requestPermissions(this, "Apply CAMERA Permissions", RC_CAMERA_PERM, perms);
        }
    }

    /**
     * 参照 https://github.com/googlesamples/easypermissions/blob/master/app/src/main/java/pub/devrel/easypermissions/sample/MainActivity.java
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发给EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.

        }
    }
}
