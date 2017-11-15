package com.tsfeng.cn;

import java.util.Arrays;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/14 16:53
 * 冒泡排序的算法原理：比较两个相邻的元素。如果第一个比第二个大，就交换他们两个；
 * 这样，关键字较小的记录将逐渐从后面向前面移动，就象气泡在水中向上浮一样，所以该算法也称为气泡排序法
 */
public class BubbleSort {

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        System.out.println("===" + a[i] + "===" + a[j]);
        System.out.println(Arrays.toString(a));
    }

    public static void bubbleSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                }
            }
            System.out.println("====================" + (i + 1) + "====================");
        }
    }

    public static void bubbleSort2(int[] a) {
        //发生了交换就为true, 没发生就为false，第一次判断时必须标志位true。
        boolean flag = true;
        int k = a.length;
        while (flag) {
            // 每次开始排序前，都设置flag为未排序过
            flag = false;
            for (int j = 1; j < k; j++) {
                // 前面的数字大于后面的数字就交换
                if (a[j - 1] > a[j]) {
                    // 交换a[j-1]和a[j]
                    swap(a, j, j - 1);
                    // 表示交换过数据;
                    flag = true;
                }
            }
            System.out.println("====================" + k + "====================");
            // 减小一次排序的尾边界
            k--;
        }
    }

    public static void bubbleSort3(int[] a) {
        //flag来记录最后交换的位置，也就是排序的尾边界
        int flag = a.length;
        //排序未结束标志
        while (flag > 0) {
            //k 来记录遍历的尾边界
            int k = flag;
            flag = 0;
            for (int j = 1; j < k; j++) {
                // 前面的数字大于后面的数字就交换
                if (a[j - 1] > a[j]) {
                    // 交换a[j-1]和a[j]
                    swap(a, j, j - 1);
                    // 表示交换过数据;
                    flag = j;
                }
            }
            System.out.println("====================" + k + "====================");
        }
    }

    public static void main(String[] args) {
        int[] a = {49, 38, 65, 97, 76, 13, 27, 49};
        System.out.println(Arrays.toString(a));
        bubbleSort(a);
        System.out.println(Arrays.toString(a));
        bubbleSort2(a);
        System.out.println(Arrays.toString(a));
        bubbleSort3(a);
    }
}
