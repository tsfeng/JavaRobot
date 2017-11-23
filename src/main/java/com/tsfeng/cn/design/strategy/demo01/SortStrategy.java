package com.tsfeng.cn.design.strategy.demo01;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 15:02
 */
public interface SortStrategy {

    /**
     * 对整型数组排序
     * @param a 排序前的数组
     * @return 排序后的数组
     */
    int[] sort(int[] a);
}
