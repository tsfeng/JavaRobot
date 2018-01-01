package com.tsfeng.cn.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/24 13:47
 */
public class ThreadDemo {
    public static void main(String[] args) {
//        new MyThread().start();
//        new MyThread().start();
//        new MyThread().start();

//        Thread thread = new Thread(() -> System.out.println("run"));
//        System.out.println(thread.getState()); // NEW
//        thread.start();
//        System.out.println(thread.getState()); // RUNNABLE
        Thread thread1 = new Thread();
//        thread1.run();
        System.out.println(thread1.getName());
        System.out.println(thread1.getId());

//        final Object lock = new Object();
//        Thread threadA = new Thread(() -> {
//            synchronized (lock) {
//                System.out.println(Thread.currentThread().getName() + " invoke");
//                try {
//                    Thread.sleep(20000l);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "BLOCKED-Thread-A");
//        Thread threadB = new Thread(() -> {
//            synchronized (lock) {
//                System.out.println(Thread.currentThread().getName() + " invoke");
//                try {
//                    Thread.sleep(20000l);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "BLOCKED-Thread-B");
//        threadA.start();
//        threadB.start();
//        System.out.println("threadA：" + threadA.getState());
//        System.out.println("threadB：" + threadB.getState());
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