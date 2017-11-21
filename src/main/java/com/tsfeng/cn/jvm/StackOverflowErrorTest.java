package com.tsfeng.cn.jvm;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/21 10:46
 */
public class StackOverflowErrorTest {
    private static int stackDeep = 1;

    public void addStackLength() {
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
