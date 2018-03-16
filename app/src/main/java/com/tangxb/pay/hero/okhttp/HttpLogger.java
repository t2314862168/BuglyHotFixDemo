package com.tangxb.pay.hero.okhttp;

import com.tangxb.pay.hero.util.MLogUtils;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Taxngb on 2017/8/2.
 */

public class HttpLogger implements HttpLoggingInterceptor.Logger {
    private static final String TAG = HttpLogger.class.getSimpleName();
    private StringBuilder mMessage = new StringBuilder();

    @Override
    public void log(String message) {
        try {
            // 请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if ((message.startsWith("{") && message.endsWith("}"))
                    || (message.startsWith("[") && message.endsWith("]"))) {
                message = JsonForOkHttp.formatJson(message);
            }
            mMessage.append(message.concat("\n"));
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                MLogUtils.d(TAG, mMessage);
            }
        } catch (Exception e) {
        }
    }
}