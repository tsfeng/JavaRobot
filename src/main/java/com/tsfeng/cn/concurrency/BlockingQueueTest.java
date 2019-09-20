package com.tsfeng.cn.concurrency;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author admin
 * @title: BlockingQueueTest
 * @projectName JavaRobot
 * @description: TODO
 * @date 2019/9/1115:04
 */
public class BlockingQueueTest {

    static ReentrantLock lock = new ReentrantLock();
    private static final int CONCURRENCE_COUNT = 10 + 1;
    static CountDownLatch latch = new CountDownLatch(11);

    public static void main(String[] args) throws InterruptedException {
//        BlockingQueue<Integer> queue = new SynchronousQueue<>();
//        boolean offer = queue.offer(null);
////        System.out.println(offer);
//        System.out.println(queue.offer(1) + " ");
//        System.out.println(queue.offer(2) + " ");
//        System.out.println(queue.offer(3) + " ");
//
//        System.out.println(queue.take() + " ");
//        System.out.println(queue.size());

//        Thread t1 = new Thread() {
//            @Override
//            public void run() {
//                Sync();
//            }
//        };
//        t1.setName("t1");
//        t1.start();
//        Thread.sleep(100);
//        for (int i = 0; i < 5; i++) {
//            Thread t2 = new Thread() {
//                @Override
//                public void run() {
//                    Sync();
//                }
//            };
//            t2.setName("t1" + i);
//            t2.start();
//            Thread.sleep(100);
//            System.out.println("===================循环中等待队列：" + lock.getQueueLength());
//        }

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new SyncTask());
            thread.setName("tt"+i);
            thread.start();
            latch.countDown();
            System.out.println(latch.getCount());
        }
        Thread.sleep(2000);
        long currentTimeMillis = System.currentTimeMillis();
        try {
            latch.countDown();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前用时：" + (System.currentTimeMillis() - currentTimeMillis));
//        System.out.println("main");
    }

    static void Sync() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "第一次");
//        lock.lock();
//        System.out.println(name + "第二次==========================================");
//        for (int i = 5; i > 0; i--) {
//            System.out.println("释放锁倒计时：" + i);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("剩余等待队列：" + lock.getQueueLength());
        if (Objects.equals(name, "t1")) {


//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("剩余等待队列2：" + lock.getQueueLength());
            lock.unlock();
//            System.out.println("结束unlock");
        }
    }


    private static class SyncTask implements Runnable {
        @Override
        public void run() {
            try {
                latch.await();
                Sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
