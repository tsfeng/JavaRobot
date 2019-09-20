package com.tsfeng.cn.redis;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @title: RedisDemo
 * @projectName JavaRobot
 * @description: TODO
 * @date 2019/9/916:01
 */
public class RedisDemo {

    public static void main(String[] args) {
        JdkSerializationRedisSerializer j = new JdkSerializationRedisSerializer();
//        GenericJackson2JsonRedisSerializer g = new GenericJackson2JsonRedisSerializer();
        GenericFastJsonRedisSerializer f = new GenericFastJsonRedisSerializer();
//        Jackson2JsonRedisSerializer j2 = new Jackson2JsonRedisSerializer(List.class);
        List<String> list = new ArrayList<>();
        list.add("123");
        byte[] serialize = f.serialize(list);

        Object deserialize = f.deserialize(serialize);
        System.out.println(deserialize);
    }
}
