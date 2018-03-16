package com.tangxb.pay.hero.util;

public class MStringUtils {
    /**
     * 将时间转换为 6:53 格式字符串
     *
     * @param time
     * @return
     */
    public static String getSongNeedTime(long time) {
        int temp = (int) (time / 1000L);
        if (temp >= 3600) {
            int m = temp / 3600;
            int n = temp % 3600 / 60;
            int i1 = temp % 3600 % 60;
            Object[] arrayOfObject2 = new Object[3];
            arrayOfObject2[0] = Integer.valueOf(m);
            arrayOfObject2[1] = Integer.valueOf(n);
            arrayOfObject2[2] = Integer.valueOf(i1);
            return String.format("%02d:%02d:%02d", arrayOfObject2);
        }
        int j = temp / 60;
        int k = temp % 60;
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = Integer.valueOf(j);
        arrayOfObject1[1] = Integer.valueOf(k);
        return String.format("%02d:%02d", arrayOfObject1);
    }
}
