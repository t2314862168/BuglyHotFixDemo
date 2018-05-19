package com.tangxb.pay.hero.util;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * Created by zll on 2017/5/20.
 */

/**
 * 价格计算器
 */
public class PriceCalculator {
    public static String multiply(String num, String num1) {
        if (TextUtils.isEmpty(num) || TextUtils.isEmpty(num1)) {
            return "";
        }
        if (num.equals("0") || num1.equals("0")) {
            return "";
        }
        try {
            BigDecimal numD = new BigDecimal(num);
            BigDecimal numD1 = new BigDecimal(num1);
            BigDecimal multiply = numD.multiply(numD1);
            //NumberFormat Format = NumberFormat.getNumberInstance();
            // Format.setMinimumFractionDigits(2);//设置数的小数部分所允许的最小位数(如果不足后面补0)
            return multiply.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            //return Format.format(multiply);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String PriceDivide(String num, String num1) {
        if (TextUtils.isEmpty(num) || TextUtils.isEmpty(num1)) {
            return "";
        }
        if (num.equals("0") || num1.equals("0")) {
            return "";
        }
        try {
            BigDecimal numD = new BigDecimal(num);
            BigDecimal numD1 = new BigDecimal(num1);
            BigDecimal multiply = numD.divide(numD1);
            //NumberFormat Format = NumberFormat.getNumberInstance();
            // Format.setMinimumFractionDigits(2);//设置数的小数部分所允许的最小位数(如果不足后面补0)
            return multiply.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            //return Format.format(multiply);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isZERO(String price) {
        if (TextUtils.isEmpty(price) || price.equals("0")) {
            return true;
        }
        try {
            BigDecimal pNum = new BigDecimal(price);
            int r = pNum.compareTo(BigDecimal.ZERO); //和0，Zero比较
            return r != 1;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String add(String num, String num1) {
        if (num == null || "null".equals(num.trim()) || num.length() == 0) num = "0";
        if (num1 == null || "null".equals(num1.trim()) || num1.length() == 0) num1 = "0";
        try {
            BigDecimal numD = new BigDecimal(num);
            BigDecimal numD1 = new BigDecimal(num1);
            BigDecimal multiply = numD.add(numD1);
            //NumberFormat Format = NumberFormat.getNumberInstance();
            // Format.setMinimumFractionDigits(2);//设置数的小数部分所允许的最小位数(如果不足后面补0)
            return multiply.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            //return Format.format(multiply);
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 价格比较.
     *
     * @param cost
     * @param subPrice
     * @return 如果 价格出入过大，返回true,正常范围显示false。
     */
    public static boolean difference(String cost, String subPrice) {
        if (isZERO(cost)) {//付款为0 是直接返回 false
            return false;
        }
        if (isZERO(subPrice)) {//付款不为0，而合计为0 时，说明付款有误。
            return true;
        }

        float intCost = Float.parseFloat(cost);
        float intSub = Float.parseFloat(subPrice);

        float result = Math.abs(intSub - intCost) * 100 / intSub;

        if (result > 10) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 把价格变成保留2位小数的字符串
     *
     * @param d
     * @return
     */
    public static String getNeedPriceStr(Double d) {
        if (d == null) {
            return "";
        } else {
            try {
                BigDecimal numD = new BigDecimal(d);
                return numD.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    /**
     * 保留2个小数
     *
     * @param price
     * @return
     */
    public static String leaveTwoNumPrice(String price) {
        if (TextUtils.isEmpty(price) || price.equals("null") || price.trim().length() == 0) {
            return "0.00";
        }
        try {
            BigDecimal numD = new BigDecimal(price);
            return numD.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
        }
        return "0.00";
    }
}
