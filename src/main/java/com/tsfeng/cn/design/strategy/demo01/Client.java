package com.tsfeng.cn.design.strategy.demo01;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 15:15
 */
public class Client {
    public static void main(String[] args) {
        SortRobot sortRobot = new SortRobot(new BubbleSortStrategy());
        sortRobot.sort(new int[0]);

        sortRobot.changeStrategy(new InsertSortStrategy());
        sortRobot.sort(new int[0]);
    }
}
