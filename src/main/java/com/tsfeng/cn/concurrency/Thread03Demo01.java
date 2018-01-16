package com.tsfeng.cn.concurrency;

/**
 * @author tsfeng
 * @version 创建时间 2018/1/14 13:27
 */
public class Thread03Demo01 {
    private static Thread sThread;

    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("2==>" + sThread.getState());
            }
        });
        System.out.println("1==>" + sThread.getState());
        sThread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3==>" + sThread.getState());
    }
}
