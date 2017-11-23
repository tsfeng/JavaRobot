package com.tsfeng.cn.effective;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/23 22:04
 */
public class DoubleDemo {
    public static void main(String[] args) {
        Double i1 = 100.0;
        Double i2 = 100.0;
        Double i3 = 200.0;
        Double i4 = 200.0;
        //打印false
        System.out.println(i1 == i2);
        //打印false
        System.out.println(i3 == i4);
    }
}
