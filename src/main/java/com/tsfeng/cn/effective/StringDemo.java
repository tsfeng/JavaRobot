package com.tsfeng.cn.effective;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/24 10:26
 */
public class StringDemo {
    public static void main(String[] args) {
        String s = "abcd";
        String s1 = s.concat("abcd");

        String s2 = s.substring(0);
        String s3 = s1.substring(4);

        System.out.println(s);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);

        System.out.println(s == s2);
        System.out.println(s == s3);
    }
}
