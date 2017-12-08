package com.tsfeng.cn.core.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/8 15:57
 */
public class MethodReferenceDemo {

    public static void main(String[] args) {
        List numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16);
        List primeNumbers = MethodReferenceDemo.findPrimeNumbers(numbers,
                (number) -> MethodReferenceDemo.isPrime((int) number));

        System.out.println("质数：" + primeNumbers);


        List list = new ArrayList();
        List list2 = list;
    }



    public static boolean isPrime(int number) {
        if (number == 1) {
            return false;
        }
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static List findPrimeNumbers(List list, Predicate predicate) {
        List sortedNumbers = new ArrayList();
        list.stream().filter((i) -> (predicate.test(i)))
                .forEach((i) -> {
                    sortedNumbers.add(i);
                });
        return sortedNumbers;

    }
}
