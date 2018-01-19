package com.tsfeng.cn.concurrency;

/**
 * @author tsfeng
 * @version 创建时间 2018/1/19 13:59
 */
public class Thread03Demo07 {

    private static Thread sThread;

    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!sThread.isInterrupted()) {
                    System.out.println("Thread is beginning");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    for (int i = 1; i <= 5; i++) {
                        System.out.println(i + "==>the thread is running...");
                    }
                }
            }
        });
        sThread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stopThread();
    }


    private static void stopThread() {
        System.out.println("stop sThread...");
        sThread.interrupt();
    }
}
