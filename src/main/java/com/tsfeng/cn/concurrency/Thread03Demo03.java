package com.tsfeng.cn.concurrency;

/**
 * @author tsfeng
 * @version 创建时间 2018/1/14 14:02
 */
public class Thread03Demo03 {
    private static Thread sThread;
    private static boolean KEEP_RUNNING = true;

    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (KEEP_RUNNING) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
        KEEP_RUNNING = false;
    }
}
