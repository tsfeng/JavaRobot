Java线程到底存在几种状态？网络上存在各种说法。  
### **Java线程状态** 
在Thread类里有一个枚举类型**State**，**明确**定义了**Java线程**的6种状态，源码如下（特意保留了2段注释）：
```java
/**
 * A thread can be in only one state at a given point in time.
 * These states are virtual machine states which do not reflect
 * any operating system thread states.
 *
 * @since   1.5
 * @see #getState
 */
public enum State {
    
    NEW,
   /** 
    * Thread state for a runnable thread.  A thread in the runnable
    * state is executing in the Java virtual machine but it may
    * be waiting for other resources from the operating system
    * such as processor.
    */
    RUNNABLE,
    BLOCKED,
    WAITING,
    TIMED_WAITING,
    TERMINATED;
}
```
### **Java线程状态含义** 
Java线程状态示意图，如下图：
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/Thread02.png)

- **NEW**：线程创建之后，但是还没有启动（start），这时候它的状态就是NEW。

- **RUNNABLE**：正在Java虚拟机下跑任务的线程的状态。处于RUNNABLE状态下的线程正在Java虚拟机中执行，但它可能正在等待来自于操作系统的其它资源，比如处理器。**这在源码注释中已有说明**。

- **BLOCKED**：阻塞状态，等待锁的释放。比如线程A进入了一个synchronized方法，线程B也想进入这个方法，但是这个方法的锁已经被线程A获取了，这个时候线程B就处于BLOCKED状态。

- **WAITING**：等待状态，处于等待状态的线程是由于执行了3个方法中的任意方法。      
    - Object的wait方法，并且没有使用timeout参数；  
    - Thread的join方法，没有使用timeout参数；  
    - LockSupport的park方法。
    处于waiting状态的线程会等待另外一个线程处理特殊的行为。 再举个例子，如果一个线程调用了一个对象的wait方法，那么这个线程就会处于waiting状态直到另外一个线程调用这个对象的notify或者notifyAll方法后才会解除这个状态。
    
- **TIMED_WAITING**：有等待时间的等待状态，比如调用了以下几个方法中的任意方法，并且指定了等待时间，线程就会处于这个状态。  
    - Thread.sleep方法；
    - Object的wait方法，带有时间；
    - Thread.join方法，带有时间；
    - LockSupport的parkNanos方法，带有时间 
    - LockSupport的parkUntil方法，带有时间。
    
- **TERMINATED**：线程中止的状态，这个线程已经完整地执行了它的任务。 
 
**Thread.State源码中的注释说明：**   
一个线程在一个给定的时间点只能处于一种状态。    
这些状态是虚拟机状态，它不反映任何操作系统的线程状态。  
这些线程状态在JDK1.5时引入了这些定义，因此，说有6种线程状态是对1.5及以后版本而言。  
**需要注意的是：**  
这里所谓的“Java线程状态”指的是虚拟机层面上暴露给我们的状态，这些状态是由枚举类Thread.State明确定义的。
### **操作系统线程状态** 
你可能听说过这样的说法，比如在Windows系统下，很多的虚拟机实现实际都把Java线程一一映射到操作系统的内核线程（kernel thread）上。  
> 除了1:1映射，还可能有N:1或者N:M映射。总之，世界很乱……

自然，操作系统的线程也有它自己的状态。Java有6种，Windows可能有N种，到了Linux系统，它可能有M种。
> 好吧，我也不知道具体有几种~且不说操作系统各种版本满天飞。

在这里我想说的是，你管它是N种还是M种！虚拟机层的存在，统一了这些差别。不管它是N还是M种，到了Java层面它们都被映射到了6种状态上来。自然，两个层面上有很多状态其实是大同小异的。至于具体差异，那是写虚拟机实现的那些家伙们去操心的事。
> 有可能操作系统中的两种状态在JVM中则统一成了一种状态，也可能操作系统中的一种状态在JVM中又细分成了两种状态，谁知道呢？你也不想去知道，反正我是不想去知道。

而很多关于操作系统上的书则常会提到有**5种进程（process）状态**：new，ready，running，waiting，terminated。  
示意图如下：
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/Process_State.png)
> 不幸的是，有很多人常常把这些进程状态，线程状态与Java线程状态混为一谈。
> 比如看到Java只有RUNNABLE（可运行的）状态，就觉得这还不够呀，应该还有Running（运行中）状态；
> 又或者觉得RUNNABLE就是Running，所以应该还有个Ready（就绪）状态。

### **Java线程状态 VS 操作系统线程状态** 
我们已经知道，Thread.State状态是虚拟机状态，它不反映任何操作系统的线程状态。
> 比如：操作系统内核线程中的状态可能有Running又有Ready，在虚拟机层面则可能统一映射成了RUNNABLE。
>
> 又比如：Java状态中的BLOCKED，WAITING，TIMED_WAITING三种状态，在操作系统内核线程中的状态则被笼统地称为blocked或者waiting。

附一张示意图：
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/ThreadStatus.png)
  







