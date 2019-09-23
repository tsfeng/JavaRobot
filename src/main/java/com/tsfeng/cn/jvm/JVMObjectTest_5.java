package com.tsfeng.cn.jvm;

/**
 * @author admin
 * @title: JVMObjectTest_1
 * @projectName JavaRobot
 * @description: TODO
 * @date 2019/9/2315:20
 */
public class JVMObjectTest_5 {

    static class TheInnerObject {
        int innerA;
    }
    TheInnerObject innerObject = new TheInnerObject();
}
