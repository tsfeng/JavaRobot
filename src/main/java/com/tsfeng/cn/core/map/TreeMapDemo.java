package com.tsfeng.cn.core.map;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author admin
 * @title: TreeMapDemo
 * @projectName JavaRobot
 * @description: TODO
 * @date 2019/9/1913:55
 */
public class TreeMapDemo {


    public static void main(String[] args) {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "a");
        treeMap.put(2, "b");
        treeMap.put(3, "c");
        treeMap.put(4, "d");
        //treeMap：{1=a, 2=b, 3=c, 4=d}
        System.out.println("init treeMap：" + treeMap);
//        treeMap.remove(4);
        // treeMap: {1=a, 2=b, 3=c}
        System.out.println("remove 4，treeMap：" + treeMap);
        int sizeOfTreeMap = treeMap.size();
        // sizeOfTreeMap: 3
        System.out.println("sizeOfTreeMap：" + sizeOfTreeMap);
        treeMap.replace(2, "e");
        System.out.println("replace 2->e，treeMap：" + treeMap);

        Map.Entry entry = treeMap.firstEntry();
        System.out.println("treeMap firstEntry：" + entry);
        Integer key = treeMap.firstKey();
        System.out.println("treeMap firstKey：" + key);

        entry = treeMap.lastEntry();
        System.out.println("treeMap lastEntry：" + entry);
        key = treeMap.lastKey();
        System.out.println("treeMap lastKey：" + key);
        System.out.println(treeMap.lowerEntry(2));

        String value = treeMap.get(3);
        System.out.println("treeMap key=3的vaue：" + value);

        System.out.println("============================");
        System.out.println("init treeMap：" + treeMap);
        SortedMap sortedMap = treeMap.headMap(3);
        System.out.println("treeMap key<2的Map：" + sortedMap);

        SortedMap sortedMap2 = treeMap.tailMap(3);
        System.out.println("treeMap key<2的Map：" + sortedMap);
        System.out.println("============================");

        sortedMap = treeMap.subMap(1, 3);
        System.out.println("treeMap 1<=key<3的Map：" + sortedMap);
//        for (Map.Entry<Object, Object> next : treeMap.entrySet()) {
//            System.out.println(next.getKey() + "_" + next.getValue());
//        }

        System.out.println("============================");
//        treeMap.forEach((key, value) -> System.out.println(key));

    }
}
