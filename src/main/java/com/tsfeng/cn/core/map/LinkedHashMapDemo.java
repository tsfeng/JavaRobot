package com.tsfeng.cn.core.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author admin
 * @title: LinkedHashMapDemo
 * @projectName JavaRobot
 * @description: TODO
 * @date 2019/9/2416:24
 */
public class LinkedHashMapDemo {


    public static void main(String[] args) {
        int size = 5;
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(size, .75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                boolean tooBig = size() > size;
                if (tooBig) {
                    System.out.println("最近最少使用的key=" + eldest.getKey());
                }
                return tooBig;
            }
        };
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.put("4", "4");
        map.put("5", "5");
        System.out.println(map.toString());
        map.put("6", "6");
        map.get("2");
        System.out.println(map.toString());
        map.put("7", "7");
        map.get("4");

        System.out.println(map.toString());
    }
}
