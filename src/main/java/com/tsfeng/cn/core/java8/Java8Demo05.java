package com.tsfeng.cn.core.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/30 23:42
 * 5、Stream
 */
public class Java8Demo05 {

    public static void main(String[] args) {
        List<String> strings = new ArrayList<>(Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl"));
        strings.add("123");
        System.out.println(strings.stream()
            .filter(str -> str.isEmpty())
            .count());
    }
}
