package com.tsfeng.cn.core.javakey;

import java.io.Serializable;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/27 18:03
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 645519021949051626L;

    private Long id;
    private transient String name;
    private transient static int age;
    private static String sex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        Student.age = age;
    }

    public static String getSex() {
        return sex;
    }

    public static void setSex(String sex) {
        Student.sex = sex;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
