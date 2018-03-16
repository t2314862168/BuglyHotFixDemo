package com.tangxb.pay.hero.util;

import java.nio.charset.Charset;

/**
 * http://blog.csdn.net/sunnyfans/article/details/8286906 <br>
 * <a href="http://blog.csdn.net/blog_szhao/article/details/23997881">关于>>与>>>的区别</a> <br>
 * Created by Administrator on 2017/4/20.
 */

public class DataHelper {
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * @return 默认编码
     */
    private static Charset getCharset() {
        return Charset.forName("UTF-8");
    }

    /**
     * @param str
     * @param len
     * @return 转换为固定大小的byte数组
     */
    public static byte[] strToBytesWithLen(String str, int len) {
        byte[] bytes = new byte[len];
        byte[] temp = str.getBytes(getCharset());
        if (temp.length >= len) {
            System.arraycopy(temp, 0, bytes, 0, len);
        } else {
            System.arraycopy(temp, 0, bytes, 0, temp.length);
        }
        return bytes;
    }

    /**
     * @param data
     * @return byte数组转换为str
     */
    public static String bytesToStrWithLen(byte[] data) {
        return bytesToStrWithLen(data, 0, data.length);
    }

    /**
     * @param data
     * @param offset
     * @return byte数组转换为str
     */
    public static String bytesToStrWithLen(byte[] data, int offset) {
        return bytesToStrWithLen(data, offset, data.length);
    }

    /**
     * @param data
     * @param offset
     * @param len
     * @return byte数组转换为str
     */
    public static String bytesToStrWithLen(byte[] data, int offset, int len) {
        int realLen = 0;
        // 如果长度大于了数组长度
        if (offset + len > data.length) {
            len = data.length - offset;
        }
        for (int i = offset; i < offset + len; i++) {
            if ((data[i] & 0xFF) != 0) {
                realLen++;
            }
        }
        byte[] bytes = new byte[realLen];
        System.arraycopy(data, 0, bytes, 0, realLen);
        String str = new String(bytes, getCharset());
        return str;
    }

    /**
     * @param data
     * @return byte数组转换为int(大端)
     */
    public static int bytesToInt(byte[] data) {
        return bytesToInt(data, 0);
    }

    /**
     * @param data
     * @param offset 起始位置
     * @return byte数组转换为int(大端)
     */
    public static int bytesToInt(byte[] data, int offset) {
        int value;
        value = (((data[offset] & 0xFF) << 24)
                | ((data[offset + 1] & 0xFF) << 16)
                | ((data[offset + 2] & 0xFF) << 8)
                | (data[offset + 3] & 0xFF));
        return value;
    }

    /**
     * 将32位整数转换成长度为4的byte数组
     *
     * @param value int
     * @return byte[]
     */
    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (src.length - 1 - i) * 8;
            src[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return src;
    }

    /**
     * 将16位的short转换成byte数组
     *
     * @param s short
     * @return byte[] 长度为2
     */
    public static byte[] shortToBytes(short s) {
        byte[] targets = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xFF);
        }
        return targets;
    }

    /**
     * 通过byte数组取到short
     *
     * @param b
     * @param offset 第几位开始取
     * @return
     */
    public static short bytesToShort(byte[] b, int offset) {
        return (short) (((b[offset + 1] << 8) | b[offset + 0] & 0xFF));
    }

    public static byte[] longToBytes(long s) {
        byte[] targets = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xFF);
        }
        return targets;
    }

    public static long bytesToLong(byte[] buffer, int offset) {
        long values = 0;
        for (int i = offset; i < offset + 8; i++) {
            values <<= 8;
            values |= (buffer[i] & 0xFF);
        }
        return values;
    }

    public static byte[] doubleToBytes(long s) {
        long value = Double.doubleToRawLongBits(s);
        byte[] byteRet = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
        }
        return byteRet;
    }

    public static double bytesToDouble(byte[] buffer, int offset) {
        long value = 0;
        for (int i = offset; i < offset + 8; i++) {
            value |= ((long) (buffer[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }

    /**
     * 把byte数组转成16进制字符串
     *
     * @param b
     * @param pos 起始下标
     * @param len 长度
     * @return
     */
    public static String bytesToHexString(byte[] b, int pos, int len) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            resultSb.append(bytesToHexString(b[pos + i]));
        }
        return resultSb.toString();
    }

    /**
     * @param b
     * @return byte数组转换为16进制字符串
     */
    private static String bytesToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * @param n
     * @return 10进制转16进制
     */
    public static String intToHex(int n) {
        char[] ch = new char[20];
        int nIndex = 0;
        while (true) {
            int m = n / 16;
            int k = n % 16;
            if (k == 15)
                ch[nIndex] = 'F';
            else if (k == 14)
                ch[nIndex] = 'E';
            else if (k == 13)
                ch[nIndex] = 'D';
            else if (k == 12)
                ch[nIndex] = 'C';
            else if (k == 11)
                ch[nIndex] = 'B';
            else if (k == 10)
                ch[nIndex] = 'A';
            else
                ch[nIndex] = (char) ('0' + k);
            nIndex++;
            if (m == 0)
                break;
            n = m;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(ch, 0, nIndex);
        sb.reverse();
        String strHex = new String("0x");
        strHex += sb.toString();
        return strHex;
    }

    /**
     * @param strHex
     * @return 16进制转10进制
     */
    public static int hexToInt(String strHex) {
        int nResult = 0;
        if (!IsHex(strHex))
            return nResult;
        String str = strHex.toUpperCase();
        if (str.length() > 2) {
            if (str.charAt(0) == '0' && str.charAt(1) == 'X') {
                str = str.substring(2);
            }
        }
        int nLen = str.length();
        for (int i = 0; i < nLen; ++i) {
            char ch = str.charAt(nLen - i - 1);
            try {
                nResult += (GetHex(ch) * GetPower(16, i));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return nResult;
    }

    /**
     * @param ch
     * @return 计算16进制对应的数值
     * @throws Exception
     */
    public static int GetHex(char ch) throws Exception {
        if (ch >= '0' && ch <= '9')
            return (int) (ch - '0');
        if (ch >= 'a' && ch <= 'f')
            return (int) (ch - 'a' + 10);
        if (ch >= 'A' && ch <= 'F')
            return (int) (ch - 'A' + 10);
        throw new Exception("error param");
    }

    /**
     * @param nValue
     * @param nCount
     * @return 计算幂
     * @throws Exception
     */
    public static int GetPower(int nValue, int nCount) throws Exception {
        if (nCount < 0)
            throw new Exception("nCount can't small than 1!");
        if (nCount == 0)
            return 1;
        int nSum = 1;
        for (int i = 0; i < nCount; ++i) {
            nSum = nSum * nValue;
        }
        return nSum;
    }

    /**
     * @param strHex
     * @return 判断是否是16进制数
     */
    public static boolean IsHex(String strHex) {
        int i = 0;
        if (strHex.length() > 2) {
            if (strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x')) {
                i = 2;
            }
        }
        for (; i < strHex.length(); ++i) {
            char ch = strHex.charAt(i);
            if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F') || (ch >= 'a' && ch <= 'f'))
                continue;
            return false;
        }
        return true;
    }
}
