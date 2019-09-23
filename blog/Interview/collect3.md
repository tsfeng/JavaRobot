基础

以下都是我认为面试中经常会被考察到的知识点的整理，不够完整，但大部分都是常见面试题。
java基础
集合

集合分为两大块：java.util包下的非线程安全集合和java.util.concurrent下的线程安全集合。
List

ArrayList与LinkedList的实现和区别
Map

HashMap：了解其数据结构、hash冲突如何解决（链表和红黑树）、扩容时机、扩容时避免rehash的优化

LinkedHashMap：了解基本原理、哪两种有序、如何用它实现LRU

TreeMap：了解数据结构、了解其key对象为什么必须要实现Compare接口、如何用它实现一致性哈希
Set

Set基本上都是由对应的map实现，简单看看就好
常见问题

    hashmap如何解决hash冲突，为什么hashmap中的链表需要转成红黑树？
    hashmap什么时候会触发扩容？
    jdk1.8之前并发操作hashmap时为什么会有死循环的问题？
    hashmap扩容时每个entry需要再计算一次hash吗？
    hashmap的数组长度为什么要保证是2的幂？
    如何用LinkedHashMap实现LRU？
    如何用TreeMap实现一致性hash？

线程安全的集合
Collections.synchronized

了解其实现原理
CopyOnWriteArrayList

了解写时复制机制、了解其适用场景、思考为什么没有ConcurrentArrayList
ConcurrentHashMap

了解实现原理、扩容时做的优化、与HashTable对比。
BlockingQueue

了解LinkedBlockingQueue、ArrayBlockingQueue、DelayQueue、SynchronousQueue
常见问题

    ConcurrentHashMap是如何在保证并发安全的同时提高性能？
    ConcurrentHashMap是如何让多线程同时参与扩容？
    LinkedBlockingQueue、DelayQueue是如何实现的？
    CopyOnWriteArrayList是如何保证线程安全的？

并发
synchronized

了解偏向锁、轻量级锁、重量级锁的概念以及升级机制、以及和ReentrantLock的区别
CAS

了解AtomicInteger实现原理、CAS适用场景、如何实现乐观锁
AQS

了解AQS内部实现、及依靠AQS的同步类比如ReentrantLock、Semaphore、CountDownLatch、CyclicBarrier等的实现
ThreadLocal

了解ThreadLocal使用场景和内部实现
ThreadPoolExecutor

了解线程池的工作原理以及几个重要参数的设置
常见问题

    synchronized与ReentrantLock的区别？
    乐观锁和悲观锁的区别？
    如何实现一个乐观锁？
    AQS是如何唤醒下一个线程的？
    ReentrantLock如何实现公平和非公平锁是如何实现？
    CountDownLatch和CyclicBarrier的区别？各自适用于什么场景？
    适用ThreadLocal时要注意什么？比如说内存泄漏?
    说一说往线程池里提交一个任务会发生什么？
    线程池的几个参数如何设置？
    线程池的非核心线程什么时候会被释放？
    如何排查死锁？

推荐文章：

死磕Synchronized底层实现--概论（比较深入）
引用

了解Java中的软引用、弱引用、虚引用的适用场景以及释放机制
常见问题

    软引用什么时候会被释放
    弱引用什么时候会被释放

推荐文章：

Java引用类型原理剖析（比较深入）
类加载

了解双亲委派机制
常见问题

    双亲委派机制的作用？
    Tomcat的classloader结构
    如何自己实现一个classloader打破双亲委派

IO

了解BIO和NIO的区别、了解多路复用机制
常见问题

    同步阻塞、同步非阻塞、异步的区别？

    select、poll、eopll的区别？

    java NIO与BIO的区别？

    refactor线程模型是什么?

JVM
GC

垃圾回收基本原理、几种常见的垃圾回收器的特性、重点了解CMS（或G1）以及一些重要的参数
内存区域

能说清jvm的内存划分
常见问题

    CMS GC回收分为哪几个阶段？分别做了什么事情？
    CMS有哪些重要参数？
    Concurrent Model Failure和ParNew promotion failed什么情况下会发生？
    CMS的优缺点？
    有做过哪些GC调优？
    为什么要划分成年轻代和老年代？
    年轻代为什么被划分成eden、survivor区域？
    年轻代为什么采用的是复制算法？
    老年代为什么采用的是标记清除、标记整理算法
    什么情况下使用堆外内存？要注意些什么？
    堆外内存如何被回收？
    jvm内存区域划分是怎样的？

推荐文章：JVM垃圾回收历险
中间件、存储、以及其他框架
Spring

bean的生命周期、循环依赖问题、spring cloud（如项目中有用过）、AOP的实现、spring事务传播
常见问题

    java动态代理和cglib动态代理的区别（经常结合spring一起问所以就放这里了）

    spring中bean的生命周期是怎样的？

    属性注入和构造器注入哪种会有循环依赖的问题？

Dubbo（或其他Rpc框架）

了解一个常用RPC框架如Dubbo的实现：服务发现、路由、异步调用、限流降级、失败重试
常见问题

    Dubbo如何做负载均衡？
    Dubbo如何做限流降级？
    Dubbo如何优雅的下线服务？
    Dubbo如何实现异步调用的？

RocketMq（或其他消息中间件）

了解一个常用消息中间件如RocketMq的实现：如何保证高可用和高吞吐、消息顺序、重复消费、事务消息、延迟消息、死信队列
常见问题

    RocketMq如何保证高可用的？
    RocketMq如何保证高吞吐的？
    RocketMq的消息是有序的吗？
    RocketMq的消息局部顺序是如何保证的?
    RocketMq事务消息的实现机制？
    RocketMq会有重复消费的问题吗？如何解决？
    RocketMq支持什么级别的延迟消息？如何实现的？
    RocketMq是推模型还是拉模型？
    Consumer的负载均衡是怎么样的？

Redis（或其他缓存系统）

redis工作模型、redis持久化、redis过期淘汰机制、redis分布式集群的常见形式、分布式锁、缓存击穿、缓存雪崩、缓存一致性问题

推荐书籍：《Redis 设计与实现》

推荐文章：

https://github.com/farmerjohngit/myblog/issues/1

https://github.com/farmerjohngit/myblog/issues/2

https://github.com/farmerjohngit/myblog/issues/5
常见问题

    redis性能为什么高?
    单线程的redis如何利用多核cpu机器？
    redis的缓存淘汰策略？
    redis如何持久化数据？
    redis有哪几种数据结构？
    redis集群有哪几种形式？
    有海量key和value都比较小的数据，在redis中如何存储才更省内存？
    如何保证redis和DB中的数据一致性？
    如何解决缓存穿透和缓存雪崩？
    如何用redis实现分布式锁？

Mysql

事务隔离级别、锁、索引的数据结构、聚簇索引和非聚簇索引、最左匹配原则、查询优化（explain等命令）

推荐文章：http://hedengcheng.com/?p=771

https://tech.meituan.com/2014/06/30/mysql-index.html

http://hbasefly.com/2017/08/19/mysql-transaction/
常见问题

    Mysql(innondb 下同) 有哪几种事务隔离级别？
    不同事务隔离级别分别会加哪些锁？
    mysql的行锁、表锁、间隙锁、意向锁分别是做什么的？
    说说什么是最左匹配？
    如何优化慢查询？
    mysql索引为什么用的是b+ tree而不是b tree、红黑树
    分库分表如何选择分表键
    分库分表的情况下，查询时一般是如何做排序的？

zk

zk大致原理（可以了解下原理相近的Raft算法）、zk实现分布式锁、zk做集群master选举
常见问题

    如何用zk实现分布式锁，与redis分布式锁有和优缺点

HBase（如简历有写）

HBase适用的场景、架构、merge和split、查写数据的流程。

推荐文章：http://hbasefly.com/2017/07/26/transaction-2/ 及该博客下相关文章
Storm（如简历有写）

Storm与Map Reduce、Spark、Flink的比较。Storm高可用、消息ack机制
算法

算法的话不是所有公司都会问，但最好还是准备下，主要是靠刷题，在leetcode上刷个100-200道easy和medium的题，然后对应公司的面经多看看，问题应该不大。