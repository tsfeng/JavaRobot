package com.tsfeng.cn.core.base;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/17 14:35
 */
public class ProtectedDemo {
    public static void main(String[] args) throws Throwable {
        // MyObject是Object的子类
        MyObject otherSubClass = new MyObject();
        //编译正确
        otherSubClass.clone();

        //ProtectedDemo也是Object的子类
        ProtectedDemo thisSubClass = new ProtectedDemo();
        //编译正确
        thisSubClass.clone();

        //父类
        Object superClass = new Object();
        //编译正确
//        superClass.clone();

        int i = 0;
    }
}

class MyObject {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}