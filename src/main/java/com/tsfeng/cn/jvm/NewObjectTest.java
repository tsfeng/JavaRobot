package com.tsfeng.cn.jvm;

import lombok.Data;

import java.util.Date;

public class NewObjectTest {

    public static void main(String[] args) {
        User user = new User();
        System.out.println(user.toString());

        System.out.println(3/2);
    }
}


@Data
class User {

    public User () {
        System.out.println("构造");
    }

    private String name;

    private int age;

    private Date birth;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birth=" + birth +
                '}';
    }
}