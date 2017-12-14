package com.tsfeng.cn.design.proxy;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/10 15:50
 */
public class RealSubject implements Subject{
    @Override
    public void hello() {
        System.out.println("hello，我是真实对象");
    }
}
