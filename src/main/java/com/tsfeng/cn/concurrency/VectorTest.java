package com.tsfeng.cn.concurrency;

import java.util.Vector;

/**
 * @author admin
 * @title: VectorTest
 * @projectName JavaRobot
 * @description: TODO
 * @date 2019/9/2316:23
 */
public class VectorTest {

    public void vectorTest(){
        Vector<String> vector = new Vector<String>();
        for(int i = 0 ; i < 10 ; i++){
            vector.add(i + "");
        }

        System.out.println(vector);
    }
}
