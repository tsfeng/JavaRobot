package com.tsfeng.cn.concurrency;

/**
 * @author tsfeng
 * @version 创建时间 2018/1/14 14:16
 */
public class Thread03Demo06 {
    private static Thread sThread;
    private static boolean KEEP_RUNNING = true;

    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (KEEP_RUNNING) {
                    System.out.println("Thread is beginning");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        Thread.currentThread().interrupt();
                        return;
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
