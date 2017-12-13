package com.tsfeng.cn.core.java8;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/10 17:39
 * http://www.cnblogs.com/CarpenterLee/p/6637118.html  底层
 * https://martinfowler.com/articles/collection-pipeline/ 译文
 */
public class StreamDemo {
    public static void main(String[] args) {
//        List<String> list = Arrays.asList("hello", "world", "java", "stream");
        List<Integer> nums = Arrays.asList(1, null, 3, 4, null, 6);
//        int count = 0;
//        for (Integer num : nums) {
//            if (num != null) {
//                count++;
//            }
//        }
//        System.out.println(count);
//        long count = nums.stream().filter(num -> num != null).count();
//        System.out.println(count);
        //1、创建Stream
        Stream<Integer> stream = nums.stream();
        //2、转换Stream
        Stream<Integer> integerStream = nums.stream().filter(s -> {
            System.out.println("filter: " + s);
            return s != null;
        });
        System.out.println("中间操作之后：" + JSON.toJSONString(nums));
        System.out.println(stream.equals(integerStream));
        //3、最终操作
        integerStream.forEach(System.out::println);
        System.out.println("最终操作之后：" + JSON.toJSONString(nums));
        long count = integerStream.count();
        System.out.println(count);
//        int count = 0;
//        for (String str : list) {
//            if (str.length() < 5) {
//                count++;
//            }
//        final Stream<String> stream = list.stream();
//        System.out.println(stream);
//        System.out.println(JSON.toJSONString(stream));
//        final Stream<String> stringStream = stream.filter(str -> str.length() < 5);
//        System.out.println(stringStream);
//        stringStream.forEach(System.out::println);
//        long count = list.stream().filter(str -> str.length() < 5).count();
//        System.out.println("长度小于5的字符串有：" + count);
    }
}
