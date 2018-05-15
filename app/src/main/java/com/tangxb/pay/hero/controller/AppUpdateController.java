package com.tangxb.pay.hero.controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import com.tangxb.pay.hero.RetrofitRxClient;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.activity.LoginActivity;
import com.tangxb.pay.hero.api.AppUpdateRxAPI;
import com.tangxb.pay.hero.bean.AppUpdateBean;
import com.tangxb.pay.hero.okhttp.OkHttpUtils;
import com.tangxb.pay.hero.okhttp.callback.FileCallBack;
import com.tangxb.pay.hero.util.MPackageUtils;
import com.tangxb.pay.hero.util.NetworkUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.Call;

/**
 * Created by tangxuebing on 2018/5/14.
 */

public class AppUpdateController {
    private BaseActivity mActivity;
    private AppUpdateListener updateListener;
    private String mApkFileUrl = "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/sample-debug.apk";
    private AlertDialog mAlertDialog;
    private ProgressDialog mApkDownloadDialog;

    public interface AppUpdateListener {
        /**
         * 应用没有更新
         */
        void notUpdate();
    }

    public AppUpdateController(BaseActivity activity, AppUpdateListener listener) {
        this.mActivity = activity;
        this.updateListener = listener;
    }

    /**
     * 获取app升级信息
     *
     * @return
     */
    public Observable<AppUpdateBean> getAppUpdate() {
        String versionCode = MPackageUtils.getVersionCode(mActivity);
        String versionName = MPackageUtils.getVersionName(mActivity);
        versionCode = "1";
        versionName = "manger";
        return RetrofitRxClient.INSTANCE
                .getRetrofit()
                .create(AppUpdateRxAPI.class)
                .getAppUpdate(versionCode, versionName);
    }

    /**
     * 检查更新
     */
    public void checkAppUpdate() {
        mActivity.addSubscription(getAppUpdate(), new Consumer<AppUpdateBean>() {
            @Override
            public void accept(AppUpdateBean appUpdateBean) throws Exception {
//                appUpdateBean.setUrl(mApkFileUrl);
//                appUpdateBean.setDescription("强制升级应用咯。。。。");
//                showAppUpdateTipDialog(appUpdateBean);
                updateListener.notUpdate();
            }
        });
    }

    /**
     * 显示更新提示框
     *
     * @param mUpdateBean
     */
    private void showAppUpdateTipDialog(final AppUpdateBean mUpdateBean) {
        String title = "更新提示:wifi环境";
        String btnName = "开始升级";
        if (!NetworkUtils.wifiIsConnect()) {
            title = "更新提示：非wifi状态";
            btnName = "流量升级";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(mUpdateBean.getDescription());
        builder.setPositiveButton(btnName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showApkDownloadDialog(mUpdateBean);
            }
        });

        String exitText = "下次";
        if (mUpdateBean.isDeprecate()) {
            exitText = "退出";
        }
        builder.setNeutralButton(exitText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mUpdateBean.isDeprecate()) {
                    mActivity.finish();
                } else {
                    mAlertDialog.dismiss();
                    updateListener.notUpdate();
                }
            }
        });
        mAlertDialog = builder.show();
    }

    /**
     * 显示下载进度框
     *
     * @param mUpdateBean
     */
    private void showApkDownloadDialog(AppUpdateBean mUpdateBean) {
        if (mActivity instanceof LoginActivity) {
            boolean hasPer = ((LoginActivity) mActivity).isHasPer();
            if (!hasPer) {
                ((LoginActivity) mActivity).applyNeedPermissions();
                return;
            }
        }
        mApkDownloadDialog = createProgressDialog();
        mApkDownloadDialog.show();
        // 先创建下载目录
        File file = new File(Environment.getExternalStorageDirectory(), "App_Download");
        if (!file.exists()) {
            file.mkdir();
        }
        String downloadDir = file.getAbsolutePath();
        String fileName = "sample-debug.apk";
        // 如果存在apk文件,先删除
        File apkFile = new File(file, fileName);
        if (apkFile.exists()) {
            apkFile.delete();
        }
        OkHttpUtils.get()
                .url(mUpdateBean.getUrl())
                .build()
                .execute(new FileCallBack(downloadDir, fileName) {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        mApkDownloadDialog.setProgress((int) (progress * 100));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mApkDownloadDialog.dismiss();
                        mActivity.finish();
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        installApk(response);
                    }
                });
    }

    /**
     * 调用系统安装apk
     *
     * @param apkFile
     */
    public void installApk(File apkFile) {
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //在AndroidManifest中的android:authorities值
            Uri apkUri = FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".fileprovider", apkFile);
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            mActivity.startActivity(install);
        } else {
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(install);
        }
        mApkDownloadDialog.dismiss();
        mActivity.finish();
    }

    private ProgressDialog createProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(mActivity);
        PackageManager pm = mActivity.getPackageManager();
        Drawable icon = mActivity.getApplicationInfo().loadIcon(pm);
        CharSequence label = mActivity.getApplicationInfo().loadLabel(pm);
        progressDialog.setTitle("正在更新" + label);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIcon(icon);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mApkDownloadDialog.dismiss();
                mActivity.finish();
            }
        });
        return progressDialog;
    }
}