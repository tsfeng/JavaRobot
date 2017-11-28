package com.tsfeng.cn.core.map;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/27 16:06
 * Map(interface)
 * SortedMap(interface)
 * NavigableMap(interface)
 * AbstractMap(abstract class)
 * IdentityHashMap(class)
 * Hashtable(class)
 * HashMap(class)
 * WeakHashMap(class)
 * EnumMap(class)
 * TreeMap(class)
 * Properties(class)
 * LinkedHashMap(class)
 */
public class MapDemo {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "2");
        map.put("3", "4");
        map.put("5", "6");
        map.put("9", "10");
        System.out.println("map.entrySet()：" + map.entrySet());

        boolean remove = map.remove("5", "5");
        System.out.println(remove);
        //Java8新方法
        //如果key不存在，新建key进行存储；如果key存在，value为null则删除此节点，不为null替换节点value并返回此value。
        System.out.println("compute：" + map.compute("7", (k, v) -> "8"));
        System.out.println("map.entrySet()：" + map.entrySet());
        System.out.println("compute：" + map.compute("9", (k, v) -> null));
        System.out.println("map.entrySet()：" + map.entrySet());
        System.out.println("compute：" + map.compute("9", (k, v) -> "10"));
        System.out.println("map.entrySet()：" + map.entrySet());

        //Java8
        String orDefault = map.getOrDefault("5", "5");
        System.out.println(orDefault);
        map.put(null, "-1");
        //比较key对应的value，value为null则把新value存入，返回存入之前的value。
        System.out.println("putIfAbsent：" + map.putIfAbsent(null, "-2"));
        //Java8迭代
        map.forEach((key, value) -> System.out.println("key：" + key + "，value：" + value));
        //Java7及之前
        for(Map.Entry<String, String> entry : map.entrySet()){
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }


//        map.merge("", "", (k1, v1) -> (k2, v2));

        System.out.println("===================================================");
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("11", "12");
        hashtable.put("13", "14");
        System.out.println(hashtable.containsKey("11"));
        System.out.println(hashtable.contains("12"));
        System.out.println(hashtable.containsValue("14"));
        Enumeration<String> elements = hashtable.elements();
        System.out.println(JSON.toJSONString(elements));
        Set<Map.Entry<String, String>> entries = hashtable.entrySet();
        System.out.println(JSON.toJSONString(entries));
    }
}


//Hashtable是同步的，而HashMap不是。这HashMap对非线程应用程序更好，因为非同步对象通常比同步对象执行得更好。
//Hashtable不允许null键或值。  HashMap允许一个null键和任意数量的null值。
//一个HashMap的子类是LinkedHashMap，所以如果你想要可预测的迭代顺序（这是默认的插入顺序），你可以很容易地换出HashMapfor a LinkedHashMap。如果你正在使用，这将不那么容易Hashtable。

//1）线程安全
//
//HashTable是内部同步的。
//因此，在多线程应用程序中使用HashTable是非常安全的。
//HashMap不是内部同步的。
//因此，在没有外部同步的情况下，在多线程应用程序中使用HashMap是不安全的。
//您可以使用Collections.synchronizedMap()方法从外部同步HashMap。
//2）继承自
//
//虽然HashMap和HashTable都实现了Map接口，但它们扩展了两个不同的类。
//HashMap扩展了AbstractMap类，而HashTable继承了java中的遗留类Dictionary类。
//3）空键和空值
//
//HashMap允许最大一个空键和任意数量的空值。
//HashTable甚至不允许单个空键和空值。
//4）遍历
//
//HashMap只返回用于遍历HashMap元素的迭代器。
//HashTable返回Iterator以及Enumeration，它可以用来遍历HashTable的元素。
//5）Fail-Fast Vs Fail-Safe
//
//由HashMap返回的迭代器本质上是Fail-Fast，即如果Iterator创建之后,迭代器自身的remove()方法以外的修改了HashMap，
// 则抛出ConcurrentModificationException异常。
//另一方面，由HashTable返回的枚举本质上是Fail-Safe，即如果在创建Enumeration之后修改了HashTable，它们不会抛出任何异常。
//6）性能
//
//由于HashTable是内部同步的，这使得HashTable比HashMap稍慢。
//7）遗留类
//
//HashTable是一个遗留类。
//这几乎是视为弃用
//从JDK 1.5开始，ConcurrentHashMap被认为是比HashTable更好的选择。
//8）Java Collection框架的成员
//
//HashMap是在JDK 1.2中引入之初的Java Collection Framework的成员。
//而HashTable在JDK 1.2之前就存在。从JDK 1.2开始，实现了Map接口，使之成为集合框架的一员。