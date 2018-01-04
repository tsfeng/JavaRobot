线程到底存在几种状态？网络上存在各种说法。  
在Thread类里有一个枚举类型State，定义了**Java线程**的几种状态，如下：
```java
/**
 * A thread can be in only one state at a given point in time.
 * These states are virtual machine states which do not reflect
 * any operating system thread states.
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
- **NEW**：线程创建之后，但是还没有启动，这时候它的状态就是NEW。

- **RUNNABLE**：正在Java虚拟机下跑任务的线程的状态。处于RUNNABLE状态下的线程正在Java虚拟机中执行，但它可能正在等待来自于操作系统的其它资源，比如处理器。这在源码注释中已有说明。

- **BLOCKED**：阻塞状态，等待锁的释放，比如线程A进入了一个synchronized方法，线程B也想进入这个方法，但是这个方法的锁已经被线程A获取了，这个时候线程B就处于BLOCKED状态。

- **WAITING**：等待状态，处于等待状态的线程是由于执行了3个方法中的任意方法。      
    1. Object的wait方法，并且没有使用timeout参数；  
    2. Thread的join方法，没有使用timeout参数；  
    3. LockSupport的park方法。
    处于waiting状态的线程会等待另外一个线程处理特殊的行为。 再举个例子，如果一个线程调用了一个对象的wait方法，那么这个线程就会处于waiting状态直到另外一个线程调用这个对象的notify或者notifyAll方法后才会解除这个状态。
    
- **TIMED_WAITING**：有等待时间的等待状态，比如调用了以下几个方法中的任意方法，并且指定了等待时间，线程就会处于这个状态。  
    1. Thread.sleep方法；
    2. Object的wait方法，带有时间；
    3. Thread.join方法，带有时间；
    4. LockSupport的parkNanos方法，带有时间 
    5. LockSupport的parkUntil方法，带有时间。
    
- **TERMINATED**：线程中止的状态，这个线程已经完整地执行了它的任务。

![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/Thread02.png)
Thread.State源码中的注释说明：  
**一个线程在一个给定的时间点只能处于一种状态。**  
**这些状态是虚拟机状态，它不反映任何操作系统的线程状态。**

  




