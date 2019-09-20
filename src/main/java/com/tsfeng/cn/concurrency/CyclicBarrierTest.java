package com.tsfeng.cn.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author admin
 * @title: CyclicBarrierTest
 * @projectName JavaRobot
 * @description: TODO
 * @date 2019/9/1117:35
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        System.out.println("start");
        CyclicBarrier cyclicBarrier =  new CyclicBarrier(5, () -> System.out.println("线程组执行结束"));
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                        System.out.println("线程组任务结束，其他任务继续");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        synchronized (CyclicBarrierTest.class) {

        }
        System.out.println("over");
    }
}
