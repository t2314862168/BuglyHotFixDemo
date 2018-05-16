package com.tangxb.pay.hero.encrypt;


/**
 * 此类是专门用于操作字节数组的类
 * @author 李熠
 * @date 2014-7-8
 * @company 成都市映潮科技有限公司
 * @version 0.1.3
 * @since 0.1.3
 */
public class ByteUtils {

	/**
	 * 将byte[]数组转换成16进制字符串
	 * @param b 字节数组
	 * @return 16进制字符串
	 */
	public static String byte2HexString(byte b[]){
		int len = 0;
		if(null == b || (len = b.length) == 0){
			throw new NullPointerException("byte[] is empty.");
		}
		StringBuilder builder = new StringBuilder(len);
		String hexString = null;
		for (int i = 0; i < len; i++) {
			hexString = Integer.toHexString(b[i] & 0xFF);
			if(hexString.length() < 2){
				hexString = "0" + hexString;
			}
			builder.append(hexString);
		}
		return builder.toString();
	}
	
	/**
	 * 将16进制字符串转化为字节数组
	 * @param hexString 16进制字符串
	 * @return 字节数组
	 */
	public static byte[] hexString2Byte(String hexString){
		int len = 0;
		if(null == hexString || (len = hexString.length()) == 0){
			throw new NullPointerException("String is empty.");
		}
		hexString = hexString.toUpperCase();
		char c[] = hexString.toCharArray();
		len = len / 2;
		byte b[] = new byte[len];
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			b[i] = (byte)(char2Byte(c[pos]) << 4 | char2Byte(c[pos+1]));
		}
		return b;
	}
	
	 /**
     * 将int数组转化为byte数组
     *
     * @param data int数组
     * @param includeLength
     * @return
     */
    public static byte[] intArray2ByteArray(int[] data, boolean includeLength) {
        int n;
        if (includeLength) {
            n = data[data.length - 1];
        } else {
            n = data.length << 2;
        }
        byte[] result = new byte[n];
        for (int i = 0; i < n; i++) {
            result[i] = (byte) (data[i >>> 2] >>> ((i & 3) << 3));
        }
        return result;
    }
    
    /**
     * 将byte数组转化为int数组
     *
     * @param data byte数组
     * @param includeLength
     * @return
     */
    public static int[] byteArray2IntArray(byte[] data, boolean includeLength) {
        int n = (((data.length & 3) == 0) ? (data.length >>> 2)
                : ((data.length >>> 2) + 1));
        int[] result;
        if (includeLength) {
            result = new int[n + 1];
            result[n] = data.length;
        } else {
            result = new int[n];
        }
        n = data.length;
        for (int i = 0; i < n; i++) {
            result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
        }
        return result;
    }
	
	/** 
	 * Convert char to byte 
	 * @param c char 
	 * @return byte 
	 */  
	 private static byte char2Byte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  
	
	private ByteUtils(){
		throw new AssertionError();
	}
	
}
