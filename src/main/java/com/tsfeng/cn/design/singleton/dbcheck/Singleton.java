package com.tsfeng.cn.design.singleton.dbcheck;

/**
 * 创建型模式--单例模式
 * 双重检查加锁
 * 所谓“双重检查加锁”机制，指的是：并不是每次进入getInstance方法都需要同步，而是先不同步，
 * 进入方法后，先检查实例是否存在，如果不存在才进行下面的同步块，这是第一重检查，
 * 进入同步块过后，再次检查实例是否存在，如果不存在，就在同步的情况下创建一个实例，这是第二重检查。
 * 这样一来，就只需要同步一次了，从而减少了多次在同步情况下进行判断所浪费的时间
 * “双重检查加锁”机制的实现会使用关键字volatile，它的意思是：被volatile修饰的变量的值，将不会被本地线程缓存，
 * 所有对该变量的读写都是直接操作共享内存，从而确保多个线程能正确的处理该变量
 *
 * 提示：由于volatile关键字可能会屏蔽掉虚拟机中一些必要的代码优化，所以运行效率并不是很高。
 * 因此一般建议，没有特别的需要，不要使用。也就是说，虽然可以使用“双重检查加锁”机制来实现线程安全的单例，
 * 但并不建议大量采用，可以根据情况来选用
 * @author Administrator
 *
 * Q:
 * 问题一:为什么要使用volatile关键字？
 * 问题二:为什么要使用synchronized (Singleton.class)，使用synchronized(this)，或者synchronized(instance)不行吗？
 * 而且synchronized(instance)的效率更加高？
 *
 * A:
 * 1、volatile保证了instance的修改对各个线程的可见性。
 * 2、这是个static方法synchronized(this)肯定是不行的，因为没有this。
 * 再说synchronized(instance)，synchronized是针对对象而言的，对象都是堆里的对象，
 * 但是初始化状态下instance是null，只是栈里的一个标识，在堆里没有。我试了一下synchronized(null)会报空指针异常。
 *
 *
 * 这段代码看起来很完美，很可惜，它是有问题。
 * 主要在于instance = new Singleton()这句，这并非是一个原子操作，事实上在 JVM 中这句话大概做了下面 3 件事情。
 * 1）给 instance 分配内存
 * 2）调用 Singleton 的构造函数来初始化成员变量
 * 3）将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）
 * 但是在 JVM 的即时编译器中存在指令重排序的优化。
 * 也就是说上面的第二步和第三步的顺序是不能保证的，最终的执行顺序可能是 1-2-3 也可能是 1-3-2。
 * 如果是后者，则在 3 执行完毕、2 未执行之前，被线程二抢占了，这时 instance 已经是非 null 了（但却没有初始化），
 * 所以线程二会直接返回 instance，然后使用，然后顺理成章地报错。
 * 我们只需要将 instance 变量声明成 volatile 就可以了。
 *
 */
public class Singleton {

	private volatile static Singleton instance = null;

	private Singleton(){};


	public static Singleton getInstance(){
		//先检查实例是否存在，如果不存在才进入下面的同步块
		if(instance == null){
			//同步块，线程安全的创建实例
			synchronized (Singleton.class) {
				//再次检查实例是否存在，如果不存在才真正的创建实例
				if(instance == null){
					instance = new Singleton();
				}
			}
		}
		return instance;
	}
}