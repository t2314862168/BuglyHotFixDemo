package com.tangxb.pay.hero.util;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by Taxngb on 2017/5/2.
 */

public class ToastUtils {
    public static void t(final Application application, final String text) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showInMainLooper(application, text);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    showInMainLooper(application, text);
                }
            });
        }
    }

    private static void showInMainLooper(final Application application, final String text) {
        Toast.makeText(application, text, Toast.LENGTH_SHORT).show();
    }
}
