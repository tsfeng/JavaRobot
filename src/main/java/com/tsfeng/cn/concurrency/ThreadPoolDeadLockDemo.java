package com.tsfeng.cn.concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/24 11:22
 */
public class ThreadPoolDeadLockDemo {


    protected static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2, 100, 2, TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadFactory() {
                private AtomicInteger id = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("home-service-" + id.addAndGet(1));
                    return thread;
                }
            },
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Future<Long> f1 = executor.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                Thread.sleep(1000); //延时以使得第二层的f3在第一层的f2占用corePoolSize后才submit
                Future<Long> f3 = executor.submit(new Callable<Long>() {
                    @Override
                    public Long call() throws Exception {
                        return -1L;
                    }
                });
                System.out.println("f1.f3" + f3.get());
                return -1L;
            }
        });
        Future<Long> f2 = executor.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                Thread.sleep(1000);//延时
                Future<Long> f4 = executor.submit(new Callable<Long>() {
                    @Override
                    public Long call() throws Exception {

                        return -1L;
                    }
                });
                System.out.println("f2.f4" + f4.get());
                return -1L;
            }
        });
        System.out.println("here");
        System.out.println("f1" + f1.get());
        System.out.println("f2" + f2.get());
    }
}
