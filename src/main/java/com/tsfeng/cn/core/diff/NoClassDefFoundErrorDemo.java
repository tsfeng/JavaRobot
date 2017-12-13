package com.tsfeng.cn.core.diff;

import org.junit.Test;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/13 22:18
 */
public class NoClassDefFoundErrorDemo {

    public ClassWithInitErrors getClassWithInitErrors() {
        ClassWithInitErrors test;
        try {
            test = new ClassWithInitErrors();
        } catch (Throwable t) {
            System.out.println(t);
        }
        test = new ClassWithInitErrors();
        return test;
    }


    @Test
    public void test() {
        NoClassDefFoundErrorDemo sample = new NoClassDefFoundErrorDemo();
        sample.getClassWithInitErrors();
    }
}


class ClassWithInitErrors {
    static int data = 1 / 0;
}