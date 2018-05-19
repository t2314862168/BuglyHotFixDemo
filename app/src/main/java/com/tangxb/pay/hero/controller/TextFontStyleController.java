package com.tangxb.pay.hero.controller;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;

import com.tangxb.pay.hero.R;

/**
 * Created by zll on 2018/5/19.
 */

public class TextFontStyleController {
    /**
     * 返回黑中红小的字符串
     *
     * @param context
     * @param blackStr
     * @param redStr
     * @return
     */
    public static SpannableString blackMiddleRedSmall(Context context, String blackStr, String redStr) {
        StringBuffer sb = new StringBuffer(blackStr);
        int oneEnd = sb.length();
        sb.append("(");
        sb.append(redStr);
        sb.append(")");
        SpannableString styledText = new SpannableString(sb.toString());
        styledText.setSpan(new TextAppearanceSpan(context, R.style.text_black_middle), 0, oneEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.text_red_small), oneEnd, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return styledText;
    }

    /**
     * 返回红大黑小的字符串,最开始带有¥
     *
     * @param context
     * @param redStr
     * @param blackStr
     * @return
     */
    public static SpannableString redBigBlackSmall(Context context, String redStr, String blackStr) {
        String totalPrice = "¥" + redStr;
        StringBuffer sb = new StringBuffer(totalPrice);
        sb.append(blackStr);
        SpannableString styledText = new SpannableString(sb);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.text_red_small), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.text_red_big), 1, totalPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.text_black_small), totalPrice.length(), sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return styledText;
    }
}
