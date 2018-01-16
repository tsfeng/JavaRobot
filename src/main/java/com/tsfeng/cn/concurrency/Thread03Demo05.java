package com.tsfeng.cn.concurrency;

/**
 * @author tsfeng
 * @version 创建时间 2018/1/14 14:14
 */
public class Thread03Demo05 {
    private static Thread sThread;
    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!sThread.isInterrupted()) {
                    System.out.println("the thread is running");
                    sThread.interrupt();
                }
            }
        });
        sThread.start();
    }
}
