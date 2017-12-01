package com.tsfeng.cn.core.java8;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/30 23:01
 * 4、接口新增：默认方法与静态方法
 * 为什么要有这个特性？
 * 首先，之前的接口是个双刃剑，好处是面向抽象而不是面向具体编程，
 * 缺陷是，当需要修改接口时候，需要修改全部实现该接口的类，
 * 目前的java 8之前的集合框架没有foreach方法，通常能想到的解决办法是在JDK里给相关的接口添加新的方法及实现。
 * 然而，对于已经发布的版本，是没法在给接口添加新方法的同时不影响已有的实现。
 * 所以引进的默认方法。他们的目的是为了解决接口的修改与现有的实现不兼容的问题。
 */
public class Java8Demo04 {
    public static void main(String[] args) {
        Vehicle vehicle = new Car();
        vehicle.print();
    }
}



interface Vehicle {
    //Java8接口新增默认方法
    default void print(){
        System.out.println("我是一辆车!");
    }

    //Java8的另一个特性是接口可以声明（并且可以提供实现）静态方法
    static void blowHorn(){
        System.out.println("按喇叭!!!");
    }
}

interface FourWheeler {
    default void print(){
        System.out.println("我是一辆四轮车!");
    }
}

class Car implements Vehicle, FourWheeler {
    public void print(){
        Vehicle.super.print();
        FourWheeler.super.print();
        Vehicle.blowHorn();
        System.out.println("我是一辆汽车!");
    }
}
