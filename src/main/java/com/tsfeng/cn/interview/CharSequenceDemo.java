package com.tsfeng.cn.interview;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/16 16:40
 */
public class CharSequenceDemo {

    public static void main(String[] args) {
        CharSequence obj = "hello";
        String str = "hello";
        System.out.println("Type of obj: " + obj.getClass().getSimpleName());
        System.out.println("Type of str: " + str.getClass().getSimpleName());
        System.out.println("Value of obj: " + obj);
        System.out.println("Value of str: " + str);
        System.out.println("Is obj a String? " + (obj instanceof String));
        System.out.println("Is obj a CharSequence? " + (obj instanceof CharSequence));
        System.out.println("Is str a String? " + (str instanceof String));
        System.out.println("Is str a CharSequence? " + (str instanceof CharSequence));
        System.out.println("Is \"hello\" a String? " + ("hello" instanceof String));
        System.out.println("Is \"hello\" a CharSequence? " + ("hello" instanceof CharSequence));
        System.out.println("str.equals(obj)? " + str.equals(obj));
        System.out.println("(str == obj)? " + (str == obj));
    }

//    @Test
    public void test() {
//        CharSequence firstString = "hello";
//        String secondString = "hello";
//        System.out.println(firstString == secondString);
//        System.out.println(firstString.hashCode());
//        System.out.println(secondString.hashCode());
//        System.out.println(firstString.equals(secondString));
//        assertNotEquals(firstString, secondString);

        tt("1");
        tt(new StringBuffer("2"));
        tt(new StringBuilder("3"));
    }



    static void tt(CharSequence c) {
        System.out.println(c);
    }
}
