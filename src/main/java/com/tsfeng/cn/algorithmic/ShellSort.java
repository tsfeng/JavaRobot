package com.tsfeng.cn.algorithmic;

import java.util.Arrays;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/21 16:54
 * 希尔排序
 */
public class ShellSort {

    public static void shellSort(int[] a) {
        int i, j, temp;
        int length = a.length;
        //设置希尔排序的增量
        int d = length / 2;
        while (d >= 1) {
            for (i = d; i < length; i++) {
                temp = a[i];
                j = i - d;
                while (j >= 0 && a[j] > temp) {
                    a[j + d] = a[j];
                    j = j - d;
                }
                a[j + d] = temp;
            }
            System.out.println(d + "==========" + Arrays.toString(a));
            //缩小增量
            d = d / 2;
        }
    }

    public static void main(String[] args) {
        int[] a = {35, 26, 26, 9, 28, 42};
        System.out.println(Arrays.toString(a));
        shellSort(a);
        System.out.println(Arrays.toString(a));
    }
}
