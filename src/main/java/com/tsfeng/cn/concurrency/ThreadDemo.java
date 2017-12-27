package com.tsfeng.cn.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/24 13:47
 */
public class ThreadDemo {
    public static void main(String[] args) {
        new MyThread().start();
        new MyThread().start();
        new MyThread().start();
    }
}

class MyThread extends Thread {
    private int ticket = 5;

    public void run() {
        for (int i = 0; i < 10; i++) {
            if (ticket > 0) {
                System.out.println("ticket = " + ticket--);
            }
        }
    }
}