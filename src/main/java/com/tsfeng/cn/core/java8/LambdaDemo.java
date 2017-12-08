package com.tsfeng.cn.core.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/7 22:26
 */
public class LambdaDemo {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        System.out.print("Print all numbers:");
        evaluate(list, (n)->true);
        System.out.println();

        System.out.print("Print no numbers:");
        evaluate(list, (n)->false);
        System.out.println();

        System.out.print("Print even numbers:");
        evaluate(list, (n)-> n%2 == 0 );
        System.out.println();

        System.out.print("Print odd numbers:");
        evaluate(list, (n)-> n%2 == 1 );
        System.out.println();

        System.out.print("Print numbers greater than 3:");
        evaluate(list, (n)-> n > 3 );
        System.out.println();
    }

    public static void evaluate(List<Integer> list, Predicate<Integer> predicate) {
        for(Integer n: list)  {
            if(predicate.test(n)) {
                System.out.print(n + " ");
            }
        }
    }
}
