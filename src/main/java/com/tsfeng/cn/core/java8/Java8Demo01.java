package com.tsfeng.cn.core.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/30 22:41
 * 1、Lambda 表达式
 * Lambda 表达式，也可称为闭包，它是推动 Java 8 发布的最重要新特性。
 * Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。
 * 使用 Lambda 表达式可以使代码变的更加简洁紧凑。
 */
public class Java8Demo01 {

    public List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));

    public static void main(String[] args) {

    }

    @Test
    public void testLambda(){
        list.forEach(System.out::println);
        list.forEach(e -> System.out.println(e));
    }

}
