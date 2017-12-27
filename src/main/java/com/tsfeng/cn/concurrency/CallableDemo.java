package com.tsfeng.cn.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/24 16:43
 */
public class CallableDemo implements Callable{
    @Override
    public Object call() throws Exception {
        System.out.println("call");
        return null;
    }


    public static void main(String[] args) {
        Thread thread = new Thread(new FutureTask<>(new CallableDemo()));
        thread.start();
    }
}
