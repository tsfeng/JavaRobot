package com.tsfeng.cn.design.strategy.demo01;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 15:09
 */
public class InsertSortStrategy implements SortStrategy {
    @Override
    public int[] sort(int[] a) {
        System.out.println("我是插入排序策略");
        return new int[0];
    }
}
