package com.tsfeng.cn.core.javakey;

import java.io.Serializable;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/27 18:03
 */
public class Person implements Serializable {

    private transient String name;
    private transient static String gender;
    private static String info;
    //无transient、static
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        Person.gender = gender;
    }

    public static String getInfo() {
        return info;
    }

    public static void setInfo(String info) {
        Person.info = info;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
