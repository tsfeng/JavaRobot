package com.tsfeng.cn.effective;

import java.lang.reflect.Field;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 20:25
 */
public class IntegerDemo {

    public static void main(String[] args) throws Exception{
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


        Class integer = Integer.class.getDeclaredClasses()[0];
        Field field = integer.getDeclaredField("cache");
        field.setAccessible(true);
        Integer[] array =  (Integer[]) field.get(integer);
        array[130] = array[131];
        System.out.println(1 + 1);

    }


    public static void sum() {
        Integer sum = 0;
        for (int i = 1; i < 10000; i++) {
            sum += i;
        }
    }
}
