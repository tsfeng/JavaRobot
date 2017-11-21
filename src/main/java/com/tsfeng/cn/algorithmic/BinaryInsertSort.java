package com.tsfeng.cn.algorithmic;

import java.util.Arrays;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/21 15:45
 * 二分插入排序
 */
public class BinaryInsertSort {
    public static void binaryInsertSort(int[] a) {
        int i, j, temp;
        int startIndex, endIndex, mid;
        for (i = 1; i < a.length; i++) {
            //二分查找开始下标
            startIndex = 0;
            //二分查找结束下标
            endIndex = i - 1;
            temp = a[i];
            while (startIndex <= endIndex) {
                // 二分查找中间值
                mid = (startIndex + endIndex) >> 1;
                //如果待插入记录比中间记录小
                if (temp < a[mid]) {
                    // 插入点在低半区
                    endIndex = mid - 1;
                } else {
                    // 插入点在高半区
                    startIndex = mid + 1;
                }
            }
            //将前面所有大于当前待插入记录的记录后移
            for (j = i - 1; j >= startIndex; j--) {
                a[j + 1] = a[j];
            }
            //把待排序的元素temp插入腾出的位置(startIndex)
            a[startIndex] = temp;
        }
    }
    public static void main(String[] args) {
        int[] a = {35, 26, 26, 9, 28, 42};
        System.out.println(Arrays.toString(a));
        binaryInsertSort(a);
        System.out.println(Arrays.toString(a));
    }
}
