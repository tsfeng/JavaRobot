package com.tsfeng.cn.design.strategy.demo01;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 15:07
 */
public class BubbleSortStrategy implements SortStrategy{
    @Override
    public int[] sort(int[] a) {
        System.out.println("我是冒泡排序策略");
        return new int[0];
    }
}
