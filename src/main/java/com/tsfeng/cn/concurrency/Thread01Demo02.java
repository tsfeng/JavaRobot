package com.tsfeng.cn.concurrency;

/**
 * @author tsfeng
 * @version 创建时间 2018/1/1 11:58
 */
public class Thread01Demo02 {
    public static void main(String[] args) {
        MyThread0102 mt = new MyThread0102();
        // 启动3个线程t1,t2,t3(它们共用一个Runnable对象)，这3个线程一共卖5张票！
        Thread t1 = new Thread(mt);
        Thread t2 = new Thread(mt);
        Thread t3 = new Thread(mt);
        t1.start();
        t2.start();
        t3.start();
    }
}

class MyThread0102 implements Runnable {
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