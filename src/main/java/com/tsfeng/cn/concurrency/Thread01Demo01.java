package com.tsfeng.cn.concurrency;

/**
 * @author tsfeng
 * @version 创建时间 2018/1/1 11:25
 */
public class Thread01Demo01 {
    public static void main(String[] args) {
        MyThread0101 t1 = new MyThread0101();
        MyThread0101 t2 = new MyThread0101();
        MyThread0101 t3 = new MyThread0101();
        t1.start();
        t2.start();
        t3.start();
    }
}


class MyThread0101 extends Thread {
    private int ticket = 5;
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + " ticket = " + ticket--);
            }
        }
    }
}
