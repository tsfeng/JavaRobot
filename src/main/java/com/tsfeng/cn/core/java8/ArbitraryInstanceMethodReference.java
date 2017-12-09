package com.tsfeng.cn.core.java8;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.List;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/9 21:05
 * 引用特定类型任意对象的实例方法
 */
public class ArbitraryInstanceMethodReference {

    public static void main(String args[]) {
        final List<Person> people = Arrays.asList(new Person("张三"), new Person("李四"));
        //方法引用
        people.forEach(Person::printName);
        //Lambda表达式
        people.forEach(person -> person.printName());
        //Java8之前
        for (final Person person : people) {
            person.printName();
        }

        String[] strArray = {"hello", "world", "Java"};
        Arrays.sort(strArray, String::compareToIgnoreCase);
        System.out.println(JSON.toJSONString(strArray));
    }

    private static class Person {
        private String name;

        public Person(final String name) {
            this.name = name;
        }

        public void printName() {
            System.out.println(name);
        }
    }
}
