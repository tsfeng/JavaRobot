package com.tsfeng.cn.design.singleton.enums;

/**
 * 创建型模式--单例模式
 * 枚举
 * 按照《高效Java 第二版》中的说法：单元素的枚举类型已经成为实现Singleton的最佳方法。
 * 用枚举来实现单例非常简单，只需要编写一个包含单个元素的枚举类型即可
 * 使用枚举来实现单实例控制会更加简洁，而且无偿地提供了序列化机制，
 * 并由JVM从根本上提供保障，绝对防止多次实例化，是更简洁、高效、安全的实现单例的方式
 * @author Administrator
 *
 */
public enum EnumSingleton {

    /**
     * 定义一个枚举的元素，它就代表了Singleton的一个实例。
     */
    INSTANCE;

    /**
     * 单例可以有自己的操作
     */
    public void singletonOperation(){
        //功能处理
    }
}
