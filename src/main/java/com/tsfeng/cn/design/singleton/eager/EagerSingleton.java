package com.tsfeng.cn.design.singleton.eager;

/**
 * 创建型模式--单例模式
 * 饿汉式
 * 饿汉式是典型的空间换时间，当类装载的时候就会创建类的实例，不管你用不用，先创建出来，然后每次调用的时候，就不需要再判断，节省了运行时间
 * 在这个类被加载时，静态变量instance会被初始化，此时类的私有构造子会被调用。这时候，单例类的唯一实例就被创建出来了
 * 饿汉式其实是一种比较形象的称谓。既然饿，那么在创建对象实例的时候就比较着急，饿了嘛，于是在装载类的时候就创建对象实例
 * @author Administrator
 *
 */
public class EagerSingleton {

	private static EagerSingleton instance = new EagerSingleton();

	/**
	 * 私有默认构造方法
	 */
	private EagerSingleton(){

	}

	public static EagerSingleton getInstance(){
		return instance;
	}
}
