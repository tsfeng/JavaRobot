package com.tsfeng.cn.algorithmic;

import java.util.Arrays;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/15 17:17
 */
public class QuickSort {

    public static void sort(int[] a, int startIndex, int endIndex) {
        int leftIndex = startIndex;
        int rightIndex = endIndex;
        int key = a[startIndex];

        while (rightIndex > leftIndex) {
            //从后往前比较-如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
            while (rightIndex > leftIndex && a[rightIndex] >= key) {
                rightIndex--;
            }
            if (a[rightIndex] <= key) {
                int temp = a[rightIndex];
                a[rightIndex] = a[leftIndex];
                a[leftIndex] = temp;
            }
            //从前往后比较-如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
            while (rightIndex > leftIndex && a[leftIndex] <= key) {
                leftIndex++;
            }
            if (a[leftIndex] >= key) {
                int temp = a[leftIndex];
                a[leftIndex] = a[rightIndex];
                a[rightIndex] = temp;
            }
            //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }
        System.out.println(Arrays.toString(a) + "====" + leftIndex + "====" + rightIndex);
        //递归
        if (leftIndex > startIndex) {
            //左边序列。第一个索引位置到关键值索引-1
            sort(a, startIndex, leftIndex - 1);
        }
        if (rightIndex < endIndex) {
            //右边序列。从关键值索引+1到最后一个
            sort(a, rightIndex + 1, endIndex);
        }
    }

    public static void main(String[] args) {
        int[] a = {49, 38, 65, 97, 76, 13, 27, 49};
//        int[] a = {13, 38, 27, 49, 76, 97, 65, 49};

//        int[] a = {27, 38, 65, 97, 76, 13, 49, 49};
//        int[] a = {27, 38, 65, 97, 76, 13, 49, 49};
//        int[] a = {27, 38, 49, 97, 76, 13, 65, 49};
//        int[] a = {27, 38, 13, 97, 76, 49, 65, 49};
//        int[] a = {27, 38, 13, 49, 76, 97, 65, 49};
//        int[] a = {27, 38, 13, 49, 76, 97, 65, 49};
        System.out.println(Arrays.toString(a));
        sort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }
}
