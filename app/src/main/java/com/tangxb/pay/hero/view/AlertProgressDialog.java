package com.tangxb.pay.hero.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tangxb.pay.hero.R;


/**
 * <p>
 * new AlertProgressDialog.Builder(mActivity)
 * .setView(R.layout.layout_alert_dialog)
 * .setMessage("我是内容")
 * .show();
 * </p>
 * <p>
 * 使用系统兼容性的material dialog 显示
 * </p>
 * Created by Taxngb on 2018/1/3.
 */

public class AlertProgressDialog {

    public static class Builder {
        private Context mContext;
        private AlertDialog.Builder mBuilder;
        private View mView;
        private TextView mContentTv;
        private CharSequence mMessage;
        private boolean mCancelable;

        public Builder(@NonNull Context context) {
            mContext = context;
        }

        public Builder setView(int layoutResId) {
            mView = LayoutInflater.from(mContext).inflate(layoutResId, null);
            mContentTv = (TextView) mView.findViewById(R.id.md_content);
            return this;
        }

        public Builder setView(View view) {
            mView = view;
            mContentTv = (TextView) mView.findViewById(R.id.md_content);
            return this;
        }

        public Builder setMessage(@StringRes int messageId) {
            mMessage = mContext.getText(messageId);
            return this;
        }

        public Builder setMessage(@Nullable CharSequence message) {
            mMessage = message;
            return this;
        }

        public Builder setCancelable(boolean flag) {
            mCancelable = flag;
            return this;
        }

        public AlertDialog show() {
            mBuilder = new AlertDialog.Builder(mContext, R.style.MyAlertDialogStyle);
            mBuilder.setView(mView);
            if (mContentTv != null) {
                mContentTv.setText(mMessage);
            }
            mBuilder.setCancelable(mCancelable);
            final AlertDialog dialog = mBuilder.show();
            return dialog;
        }
    }
}
