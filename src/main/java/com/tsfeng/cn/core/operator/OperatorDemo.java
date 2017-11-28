package com.tsfeng.cn.core.operator;

import javax.sound.midi.Soundbank;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/28 9:52
 * Java的+ =， -  =，* =，/ =复合赋值运算符
 */
public class OperatorDemo {
    public static void main(String[] args) {
        short x = 3;
        double y= 4.6;
        int z = 1;
        //编译正确
        x += y;
        //编译正确
        x = (short)(x + y);
        //编译错误
//        x = x + y;
        z += 1;
        z = z + 1;
    }
}
