package com.tsfeng.cn.concurrency;

/**
 * @author tsfeng
 * @version 创建时间 2018/1/14 14:11
 */
public class Thread03Demo04 {
    private static Thread sThread;
    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println("the thread is running");
                    sThread.interrupt();
                }
            }
        });
        sThread.start();
    }
}
