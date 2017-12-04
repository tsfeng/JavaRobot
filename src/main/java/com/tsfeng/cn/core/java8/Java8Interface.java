package com.tsfeng.cn.core.java8;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/4 22:01
 */
public class Java8Interface implements MyData{
    public boolean isNull(String str) {
        System.out.println("Impl Null Check");
        return str == null ? true : false;
    }

    public static void main(String args[]){
        Java8Interface obj = new Java8Interface();
        obj.print("");
        obj.isNull("abc");
    }

}


interface MyData {

    default void print(String str) {
        if (!isNull(str)) {
            System.out.println("MyData Print:" + str);
        }
    }

    default boolean isNull(String str) {
        System.out.println("Interface Null Check");

        return str == null ? true : "".equals(str) ? true : false;
    }
}