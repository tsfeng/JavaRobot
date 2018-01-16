package com.tsfeng.cn.concurrency;

/**
 * @author tsfeng
 * @version 创建时间 2018/1/14 13:43
 */
public class Thread03Demo02 {
    private static Thread sThread;
    private static boolean KEEP_RUNNING = true;
    private static int position;

    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (KEEP_RUNNING) {
                    try {
                        if (++position < 5) {
                            Thread.sleep(2000);
                        } else {
                            KEEP_RUNNING = false;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        sThread.start();

        while (sThread.getState() != Thread.State.TERMINATED) {
            System.out.println("sThead's state is ==>" + sThread.getState());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(sThread.getState());
    }
}
