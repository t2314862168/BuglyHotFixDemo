package com.tangxb.pay.hero.rxhttp;

import android.content.Context;
import android.text.TextUtils;

/**
 *
 * HttpDialog
 */
public class HttpDialogUtils {

	public static void showDialog(Context context, boolean canceledOnTouchOutside, String messageText) {
		if (context != null) {
			if (TextUtils.isEmpty(messageText)) {
				CustomWaitDialogUtil.showWaitDialog(context, canceledOnTouchOutside);
			} else {
				CustomWaitDialogUtil.showWaitDialog(context, messageText, canceledOnTouchOutside);
			}
		}
	}

	public static void dismissDialog() {
		CustomWaitDialogUtil.stopWaitDialog();
	}

}
