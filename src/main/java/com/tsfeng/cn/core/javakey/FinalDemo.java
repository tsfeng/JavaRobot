package com.tsfeng.cn.core.javakey;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/28 23:52
 * 当一个方法被修饰为final方法时，意味着编译器可能将该方法用内联(inline)方式载入，
 * 所谓内联方式，是指编译器不用像平常调用函数那样的方式来调用方法，
 * 而是直接将方法内的代码通过一定的修改后copy到原代码中(将方法主体直接插入到调用处，而不是进行方法调用)。
 * 这样可以让代码执行的更快（因为省略了调用函数的开销）
 * 另一方面，私有方法也被编译器隐式修饰为final，这意味着private final void f()和private void f()并无区别。
 */
public class FinalDemo {

    //声明final变量
    private final List<String> finalList;

    public FinalDemo() {
        finalList = new ArrayList<>();
        finalList.add("hello");
    }

    public void setList(List list) {
        //编译时错误：Cannot assign a value to final variable 'foo'
        //this.finalList = list;
    }

    public static void main(String[] args) {
        FinalDemo f = new FinalDemo();
        f.finalList.add("world");
        //编译时错误：Cannot assign a value to final variable 'foo'
        //f.finalList = new ArrayList<>();
        System.out.println(JSON.toJSONString(f.finalList));

        String a = "hello world";
        final String b = "hello";
        String c = b + " world";
        System.out.println("a == c：" + (a == c));

        final String b2 = getHello();
        String c2 = b2 + " world";
        System.out.println("a == c2：" + (a == c2));

        String b3 = "hello";
        String c3 = b3 + " world";
        System.out.println("a == c3：" + (a == c3));

    }

    public static String getHello() {
        return "hello";
    }
}
