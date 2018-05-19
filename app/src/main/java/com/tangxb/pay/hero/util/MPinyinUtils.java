package com.tangxb.pay.hero.util;

import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * Created by zll on 2018/5/19.
 */

public class MPinyinUtils {
    /**
     * 字符串转换为拼音
     *
     * @param str
     * @return
     */
    public static String toPinyin(String str) {
        if (TextUtils.isEmpty(str)) return "";
        return Pinyin.toPinyin(str, "").toLowerCase();
    }
}
