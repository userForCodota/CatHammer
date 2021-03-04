package com.data.common;

/**
 * @ClassName StrUtils
 * @Description TODO
 * @Author dororo
 * @Date 2021/3/4 0004 17:56
 * @Version 1.0
 */
public class StrUtils {

    public static String encryption(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ 2000);//固定2000
        }
        return new String(chars);
    }
}
