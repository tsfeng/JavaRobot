package com.tsfeng.cn.jvm;

import java.util.concurrent.TimeUnit;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/21 10:46
 */
public class StackOverflowErrorTest {
    private static int stackDeep = 1;

    public void addStackLength() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stackDeep++;
        addStackLength();
    }

    public static void main(String[] args) {
        StackOverflowErrorTest test = new StackOverflowErrorTest();
        try {
            test.addStackLength();
        } catch (Throwable e) {
            System.out.println("Stack deep : " + stackDeep);
            e.printStackTrace();
        }
    }
}
