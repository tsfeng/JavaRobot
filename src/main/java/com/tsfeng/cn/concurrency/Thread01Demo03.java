package com.tsfeng.cn.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author tsfeng
 * @version 创建时间 2018/1/1 12:04
 */
public class Thread01Demo03 {
    public static void main(String[] args) {
        MyThread0103 myThread0103 = new MyThread0103();
        FutureTask<Integer> ft = new FutureTask<>(myThread0103);
        Thread t1 = new Thread(ft);
        t1.start();
        try {
            Integer i = ft.get();
            System.out.println("返回值：" + i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class MyThread0103 implements Callable {
    private int ticket = 5;
    @Override
    public Object call() throws Exception {
        for (int i = 0; i < 10; i++) {
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + " ticket = " + ticket--);
            }
        }
        return null;
    }
}