package com.tsfeng.cn.security;

import com.alibaba.fastjson.JSON;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 10:43
 */
public class MD5Util {
    private static final String ALGORITHM = "MD5";

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encodeByMD5(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>(16);
        for (int i = 0; i < 100; i++) {
            String str = encodeByMD5(String.valueOf(i));
            String key = str.substring(0, 1);
            if (map.containsKey(key)) {
                map.put(key, (map.get(key)+1));
            } else {
                map.put(key, 1);
            }
        }
        System.out.println(map.size());
        System.out.println(JSON.toJSONString(map));
        Map<Integer, Integer> valueMap = new HashMap<>(16);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            Integer key = entry.getValue();
            if (valueMap.containsKey(key)) {
                valueMap.put(key, (valueMap.get(key)+1));
            } else {
                valueMap.put(key, 1);
            }
        }
        System.out.println(valueMap.size());
        System.out.println(JSON.toJSONString(valueMap));
    }
}
