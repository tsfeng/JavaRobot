package com.tsfeng.cn.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/23 15:08
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
//        ExecutorService pool = Executors.newSingleThreadExecutor();
//        for (int i = 0; i < 5; i++) {
//            pool.execute(new TaskInPool(i));
//        }
//        pool.shutdown();

        if (!true && false){
            System.out.println(1);
        }

        if(! false && false){
            System.out.println(2);
        }

        if(! (true && false)){
            System.out.println(3);
        }

        if(! false && true){
            System.out.println(4);
        }
    }
}


class TaskInPool implements Runnable {
    private final int id;

    TaskInPool(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("TaskInPool-["+id+"] is running phase-"+i);
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("TaskInPool-["+id+"] is over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
