package com.tsfeng.cn.core.java8;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/5 16:43
 */

@FunctionalInterface
public interface FunctionalInterfaceDemo extends SuperInterface1, SuperInterface2{

//    void hello();
//
//    static void world(){
//        System.out.println("world");
//    };

//    String toString();
//    void finalize();

}



interface SuperInterface1 {
    void hello();
//    int m(Iterable<String> arg);
//    Iterable m(Iterable<String> arg);
//    int m(Iterable<String> arg);
}

interface SuperInterface2 {
    void hello();
//    int m(Iterable<String> arg);
//    Iterable<String> m(Iterable arg);
//    int m(Iterable<Integer> arg);
}