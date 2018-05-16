package com.tangxb.pay.hero.encrypt;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tangxuebing on 2018/5/16.
 */

public class MSignUtils {
    /**
     * 对password进行加密
     *
     * @param password  要加密的明文
     * @param algorithm 加密算法名字，有MD5,SHA1
     * @return 加密后的密文
     * @throws Exception
     */
    private static String encrypt(String password, String algorithm) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] b = md.digest(password.getBytes());
        return ByteUtils.byte2HexString(b);
    }

    /**
     * @param data
     * @param token
     * @param timestamp
     * @return
     */
    public static String getSign(Map<String, String> data, String token, String timestamp) {

        Map<String, String> map = new TreeMap<String, String>();
        map.put("token", token);
        map.put("timestamp", timestamp);
        if (data != null) map.putAll(data);

        List<Map.Entry<String, String>> paramList = new ArrayList<Map.Entry<String, String>>(map.entrySet());

        Collections.sort(paramList, new Comparator<Map.Entry<String, String>>() {
            //升序排序
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });


        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> item : paramList) {
            if (!first) {
                builder.append('&');
            } else {
                first = false;
            }
            builder.append(item.getKey()).append('=').append(item.getValue());
        }


        String sign = null;
        try {
            sign = encrypt(builder.toString(), Algorithm.SHA1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }
}
