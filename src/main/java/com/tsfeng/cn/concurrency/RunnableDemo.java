package com.tsfeng.cn.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/24 16:22
 */
public class RunnableDemo {
    public static void main(String[] args) {
        MyRunnableThread my = new MyRunnableThread();
        new Thread(my).start();
        new Thread(my).start();
        new Thread(my).start();
    }
}


class MyRunnableThread implements Runnable {
    private int ticket = 5;

    public void run() {
        for (int i = 0; i < 10; i++) {
            if (ticket > 0) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ticket = " + ticket--);
            }
        }
    }
}