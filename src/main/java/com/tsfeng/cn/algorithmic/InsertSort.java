package com.tsfeng.cn.algorithmic;

import java.util.Arrays;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/21 13:55
 * 直接插入排序
 * 插入排序的基本操作就是将一个数据插入到已经排好序的有序数据中，从而得到一个新的、个数加一的有序数据；
 * 二分查找插入排序的原理：是直接插入排序的一个变种，
 * 区别是：在有序区中查找新元素插入位置时，为了减少元素比较次数提高效率，采用二分查找算法进行插入位置的确定；
 * 希尔排序是一种分组直接插入排序方法，
 * 其原理是：先将整个序列分割成若干小的子序列，再分别对子序列进行直接插入排序，使得原来序列成为基本有序。
 * 这样通过对较小的序列进行插入排序，然后对基本有序的数列进行插入排序，能够提高插入排序算法的效率。
 */
public class InsertSort {

    public static void insertsort(int[] a) {
        int i, j, temp;
        for (i = 1; i < a.length; i++) {
            //a[0,i-1]都是有序的;
            if (a[i] < a[i - 1]) {
                temp = a[i];
                for (j = i - 1; j >= 0 && a[j] > temp; j--) {
                    //把比temp大的元素全部往后移动一个位置
                    a[j + 1] = a[j];
                }
                //把待排序的元素temp插入腾出的位置(j+1)
                a[j + 1] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {35, 26, 26, 9, 28, 42};
        System.out.println(Arrays.toString(a));
        insertsort(a);
        System.out.println(Arrays.toString(a));
    }
}
