package com.tsfeng.cn.design.strategy.demo01;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 15:11
 */
public class SortRobot {
    private SortStrategy sortStrategy;

    public SortRobot(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void changeStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public int[] sort(int[] a) {
        return this.sortStrategy.sort(a);
    }
}
