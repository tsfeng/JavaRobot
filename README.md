# **学习笔记(持续更新中)** 
所有文章均同步发布到微信公众号【JavaRobot】，关注微信公众号，及时得到文章推送，谢谢支持。  
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/8cm.jpg)

### 说明：如无特别说明，所有代码都基于JDK8  
---

- **JavaSE(Java基础)**  
    
    - Java Core
        - 关键字
            - [final关键字](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/KeyWord/Java%E5%85%B3%E9%94%AE%E5%AD%97final%E8%A7%A3%E6%9E%90.md)
            - [transient关键字](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/KeyWord/Java%E5%85%B3%E9%94%AE%E5%AD%97transient%E8%A7%A3%E6%9E%90.md)
            - [volatile关键字](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/KeyWord/Java%E5%85%B3%E9%94%AE%E5%AD%97volatile%E8%A7%A3%E6%9E%90.md)
            - synchronized关键字
        - [访问控制修饰符](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/Base/Java%E4%B8%AD%E7%9A%84%E8%AE%BF%E9%97%AE%E4%BF%AE%E9%A5%B0%E7%AC%A6.md)
    - Java String  
        - [String与CharSequence比较](https://github.com/tsfeng/JavaRobot/blob/master/blog/Interview/CharSequence%E4%B8%8EString%E7%9A%84%E5%8C%BA%E5%88%AB.md)
        - [String的hashCode为什么选择31作为乘数](https://github.com/tsfeng/JavaRobot/blob/master/blog/Interview/String%E7%9A%84hashCode%E4%B8%BA%E4%BB%80%E4%B9%88%E9%80%89%E6%8B%A931%E4%BD%9C%E4%B8%BA%E4%B9%98%E6%95%B0.md)
    - Java Arrays
    - Java Collections
    - Java 泛型
    - Java NIO
        - Buffer
        - Channel
        - Selector
    - Java 8 Features
        - [Java8新特性系列(一)](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/Java8Feature/Java8%E6%96%B0%E7%89%B9%E6%80%A7%E7%B3%BB%E5%88%97(%E4%B8%80).md)
        - [Java8新特性系列(二)](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/Java8Feature/Java8%E6%96%B0%E7%89%B9%E6%80%A7%E7%B3%BB%E5%88%97(%E4%BA%8C).md)
        - [Java8新特性系列(三)](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/Java8Feature/Java8%E6%96%B0%E7%89%B9%E6%80%A7%E7%B3%BB%E5%88%97(%E4%B8%89).md)
        - [Java8新特性系列(四)](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/Java8Feature/Java8%E6%96%B0%E7%89%B9%E6%80%A7%E7%B3%BB%E5%88%97(%E5%9B%9B).md)
        - [Java8新特性系列(五)](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/Java8Feature/Java8%E6%96%B0%E7%89%B9%E6%80%A7%E7%B3%BB%E5%88%97(%E4%BA%94).md)
        - [Java8新特性系列(六)](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/Java8Feature/Java8%E6%96%B0%E7%89%B9%E6%80%A7%E7%B3%BB%E5%88%97(%E5%85%AD).md)

- **源码解读**  
    - String源码系列
        - [String源码阅读笔记](https://github.com/tsfeng/JavaRobot/blob/master/blog/SourceCode/String%E6%BA%90%E7%A0%81%E9%98%85%E8%AF%BB%E7%AC%94%E8%AE%B0.md)
        - [StringBuffer和StringBuilder源码阅读笔记](https://github.com/tsfeng/JavaRobot/blob/master/blog/SourceCode/StringBuffer%E5%92%8CStringBuilder%E6%BA%90%E7%A0%81%E9%98%85%E8%AF%BB%E7%AC%94%E8%AE%B0.md)

    - List源码系列
        - ArrayList
        - LinkedList
        - CopyOnWriteArrayList
        - Vector
        
    - Map源码系列    
        - HashMap
        - LinkedHashMap
        - ConcurrentHashMap
        - TreeMap
        - Hashtable
        
    - Set源码系列    
        - HashSet
        - LinkedHashSet
        - TreeSet
        - HashSet
        
    - Concurrent源码系列
        - [ThreadPoolExecutor源码阅读笔记](https://github.com/tsfeng/JavaRobot/blob/master/blog/SourceCode/ThreadPoolExecutor%E6%BA%90%E7%A0%81%E9%98%85%E8%AF%BB%E7%AC%94%E8%AE%B0.md)
        
    - 待完善
#
- **JVM(Java虚拟机)**  
    - [JVM内存区域](https://github.com/tsfeng/JavaRobot/blob/master/blog/JVM/JVM%E5%86%85%E5%AD%98%E5%8C%BA%E5%9F%9F.md)
    - 类加载
    - 垃圾回收算法
#
- JavaConcurrent(Java并发系列)
    - [【Java并发系列】实现多线程常见的三种方法](https://github.com/tsfeng/JavaRobot/blob/master/blog/Concurrent/%E3%80%90Java%E5%B9%B6%E5%8F%91%E7%B3%BB%E5%88%97%E3%80%91%E5%AE%9E%E7%8E%B0%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%B8%B8%E8%A7%81%E7%9A%84%E4%B8%89%E7%A7%8D%E6%96%B9%E6%B3%95.md)
    - [【Java并发系列】关于Java的线程状态](https://github.com/tsfeng/JavaRobot/blob/master/blog/Concurrent/%E3%80%90Java%E5%B9%B6%E5%8F%91%E7%B3%BB%E5%88%97%E3%80%91%E5%85%B3%E4%BA%8EJava%E7%9A%84%E7%BA%BF%E7%A8%8B%E7%8A%B6%E6%80%81.md)
    - [【Java并发系列】线程中断机制](https://github.com/tsfeng/JavaRobot/blob/master/blog/Concurrent/%E3%80%90Java%E5%B9%B6%E5%8F%91%E7%B3%BB%E5%88%97%E3%80%91%E7%BA%BF%E7%A8%8B%E4%B8%AD%E6%96%AD%E6%9C%BA%E5%88%B6.md)
    - [【Java并发系列】为什么Thread.stop等方法被弃用](https://github.com/tsfeng/JavaRobot/blob/master/blog/Concurrent/%E3%80%90Java%E5%B9%B6%E5%8F%91%E7%B3%BB%E5%88%97%E3%80%91%E4%B8%BA%E4%BB%80%E4%B9%88Thread.stop%E7%AD%89%E6%96%B9%E6%B3%95%E8%A2%AB%E5%BC%83%E7%94%A8%EF%BC%9F.md)
    - 【Java并发系列】线程阻塞
    - Executor和ThreadPoolExecutor
    - 阻塞队列BlockingQueue
    - 原子变量Atomic
    - 锁
    - 同步工具
        - CountDownLatch
        - Semaphore
        - CyclicBarrier
        - Phaser
        - Exchange
    - 并发集合
        - ConcurrentHashMap
        - CopyOnWriteArrayList

#
- DataStructure(数据结构)
    - 数组
    - 栈
    - 队列
    - 链表
    - 树
    - 图
    - 堆
    - 散列表
    
#
- **Algorithm(算法)**
    - 排序算法
        - 插入排序
            - [直接插入排序和二分插入排序](https://github.com/tsfeng/JavaRobot/blob/master/blog/Algorithm/SortAlgorithm/%E6%8E%92%E5%BA%8F%E7%AE%97%E6%B3%95%E4%B9%8B%E6%8F%92%E5%85%A5%E6%8E%92%E5%BA%8F.md)
            - 希尔排序
        - 选择排序
            - 简单选择排序
            - 堆排序
        - 交换排序
            - [冒泡排序](https://github.com/tsfeng/JavaRobot/blob/master/blog/Algorithm/SortAlgorithm/%E6%8E%92%E5%BA%8F%E7%AE%97%E6%B3%95%E4%B9%8B%E5%86%92%E6%B3%A1%E6%8E%92%E5%BA%8F.md)
            - 快速排序
        - 分布排序
            - 基数排序
            - 桶排序
        - 归并排序
        
    - 查找算法
    - leetcode
#
- **DesignPattern(设计模式)**
    - [设计模式总纲](https://github.com/tsfeng/JavaRobot/blob/master/blog/DesignPattern/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F.md)
    - 创建型模式
        - 工厂方法模式(FactoryMethod)
        - 抽象工厂模式(AbstractFactory)
        - [单例模式(Singleton)](https://github.com/tsfeng/JavaRobot/blob/master/blog/DesignPattern/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B9%8B%E5%8D%95%E4%BE%8B%E6%A8%A1%E5%BC%8F.md)
        - 建造者模式(Builder)
        - 原型模式(ProtoType)
        
    - 结构型模式
        - 适配器模式(Adapter)
    	- 装饰器模式(Decorator)
    	- 代理模式(Proxy)
    	- 外观模式(Facade)
    	- 桥接模式(Bridge)
    	- 组合模式(Composite)
    	- 享元模式(Flyweight)
    - 行为型模式
        - [策略模式(Strategy)](https://github.com/tsfeng/JavaRobot/blob/master/blog/DesignPattern/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B9%8B%E7%AD%96%E7%95%A5%E6%A8%A1%E5%BC%8F.md)
        - 模板方法模式(Template Method)
        - 观察者模式(Observer)
        - 迭代子模式(Iterator)
        - 责任链模式(Chain of Responsibility)
        - 命令模式(Command)
        - 备忘录模式(Memento)
        - 状态模式(Pattern of Objects for States)
        - 访问者模式(Visitor)
        - 中介者模式(Mediator)
        - 解释器模式(Interpreter)
#

- 值得关注的技术网站
    - https://stackoverflow.com  
    - http://www.importnew.com  
    - https://www.programcreek.com  
    - http://javarevisited.blogspot.hk/
    - https://www.journaldev.com/  
    - https://www.infoq.com  
    - http://www.baeldung.com/category/java/
    
- 再次邀请您关注我的微信公众号JavaRobot    
    ![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/8cm.jpg)