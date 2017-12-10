package com.tsfeng.cn.core.java8;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/10 17:39
 */
public class StreamDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("java");
        list.add("stream");
//        int count = 0;
//        for (String str : list) {
//            if (str.length() < 5) {
//                count++;
//            }
//        }
        long count = list.stream().filter(str -> str.length() < 5).count();
        System.out.println("长度小于5的字符串有：" + count);
    }
}
