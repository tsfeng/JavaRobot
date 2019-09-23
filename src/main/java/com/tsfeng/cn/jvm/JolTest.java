package com.tsfeng.cn.jvm;

import org.openjdk.jol.info.ClassLayout;

public class JolTest {

    static JolTest jolTest = new JolTest();
    long a;
    public static void main(String[] args) {
        System.out.println(Integer.toHexString(jolTest.hashCode()));
//        System.out.println(ClassLayout.parseClass(JolTest.class).toPrintable());
        System.out.println(ClassLayout.parseInstance(jolTest).toPrintable());
    }
}
