package com.tsfeng.cn.core.java8;

import java.util.Arrays;
import java.util.List;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/9 20:40
 * 引用静态方法
 */
public class StaticMethodReference {
    public static void main(String args[]) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        //方法引用
        list.forEach(StaticMethodReference::print);
        //Lambda表达式
        list.forEach(number -> StaticMethodReference.print(number));
        //Java8之前
        for (int number : list) {
            StaticMethodReference.print(number);
        }
    }

    public static void print(final int number) {
        System.out.println(number);
    }
}
