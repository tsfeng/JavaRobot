package com.tsfeng.cn.effective;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 20:25
 */
public class IntegerDemo {

    public static void main(String[] args) {
        Integer i1 = 100;
        Integer i2 = 100;
        //true
        System.out.println(i1 == i2);

        Integer i3 = 200;
        Integer i4 = 200;
        //false
        System.out.println(i3 == i4);

        //true
        System.out.println(i1 == 100);
        //true
        System.out.println(i3 == 200);
    }


    public static void sum() {
        Integer sum = 0;
        for (int i = 1; i < 10000; i++) {
            sum += i;
        }
    }
}
