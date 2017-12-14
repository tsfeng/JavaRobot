package com.tsfeng.cn.design.proxy;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/10 12:35
 */
public class Proxy implements Subject{

    private RealSubject realSubject = new RealSubject();

    @Override
    public void hello() {
        System.out.println("hello，我是代理对象，我来调用真实对象");
        realSubject.hello();
    }
}

