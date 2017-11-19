package com.tsfeng.cn.concurrency;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/17 14:40
 * 我认为你的思维进入了一个误区。
 * 谁能保证，线程2读取race的值、加1、重新赋值给race，这三个动作一定是连续完成的呢？
 *
 * 如果实际的执行顺序是如下这样呢？
 *  1、线程1读取race的值后，还没有操作就被阻塞了。
 *  2、线程2被唤醒，从主存读取race的值，加1，然后被阻塞。（此时还没来得及把新的值重新赋值给race，当然也还没同步到主存）
 *  3、线程1被唤醒，race值加1，然后同步到主存（线程1结束）
 *  4、线程2被唤醒，把最新的值赋值给race，同步到主存（此时线程2，race的值在第2步时已经被处理过了，
 *  仅仅只是把新的值赋值给race而已。这个时候是不会再去读取race的缓存行的，虽然race的缓存行此时已经无效了）
 */
public class VolatileTest {

    public static volatile int race = 0;
    private static final int THREAD_COUNT = 20;
    public static void increase() {
        race++;
    }
    public static void main(String[] args) {
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increase();
                }
            });
            threads[i].start();
        }
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(race);
    }

}
