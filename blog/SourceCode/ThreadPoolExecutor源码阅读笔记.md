# **继承结构**  
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/ThreadPoolExecutor_UML.png)  
# **类定义** 
```
public class ThreadPoolExecutor extends AbstractExecutorService {
    //略
}
```
ThreadPoolExecutor继承自AbstractExecutorService，AbstractExecuetorService提供了ExecutorService执行方法的默认实现。
# **成员变量**
```
private static final int COUNT_BITS = Integer.SIZE - 3;
```
一个代表数字29的常量。
```
private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
```
AtomicInteger类型的ctl代表了ThreadPoolExecutor中的控制状态；用来表示线程池的运行状态（整型的高3位）和运行的线程数量（低29位））。
```
private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
```
代表线程数量的上限，ThreadPoolExecutor中理论上的最大活跃线程数。
```
// runState is stored in the high-order bits
private static final int RUNNING    = -1 << COUNT_BITS;
private static final int SHUTDOWN   =  0 << COUNT_BITS;
private static final int STOP       =  1 << COUNT_BITS;
private static final int TIDYING    =  2 << COUNT_BITS;
private static final int TERMINATED =  3 << COUNT_BITS;
```
线程池的5种运行状态：
- **RUNNING**
    - 线程池的初始状态
    - 接受新任务，并处理队列任务
    - -1（32个1）左移29位，即高3位为111，低29位全部为0，负数
- **SHUTDOWN**
    - RUNNING状态下调用shutdown方法后进入此状态
    - 不接受新任务，但会处理队列任务
    - 0（32个0）左移29位，，即高3位为000，低29位全部为0，正数
- **STOP** 
    - RUNNING/SHUTDOWN状态下调用shutdownNow方法后进入此状态
    - 不接受新任务，不会处理队列任务，而且会中断正在处理过程中的任务
    - 1（31个0和1个1）左移29位，即高3位为001，低29位全部为0，正数
- **TIDYING**
    - SHUTDOWN/STOP状态会过渡到此状态
    - 所有的任务已结束，工作线程数为0，将会执行terminated()方法
    - 2（30个0和10）左移29位，即高3位为010，低29位全部为0，正数
- **TERMINATED**
    - TIDYING状态下，线程池执行完terminated()方法后进入此状态
    - 线程池已完全终止
    - 3（30个0和11）左移29位，即高3位为011，低29位全部为0，正数
    
**由于有5种状态，最少需要3位表示，所以采用的AtomicInteger的高3位来表示。**  
**只有RUNNING状态下，ThreadPoolExecutor的ctl为负数，所以有了以下通过比较大小来判断运行状态的方法。**
```
private static boolean isRunning(int c) {
    return c < SHUTDOWN;
}
```
```
private final BlockingQueue<Runnable> workQueue;
private volatile ThreadFactory threadFactory;
private volatile RejectedExecutionHandler handler;
private volatile long keepAliveTime;
private volatile int corePoolSize;
private volatile int maximumPoolSize;

private static final RejectedExecutionHandler defaultHandler = new AbortPolicy();
```
构造方法相关成员变量。
```
private volatile boolean allowCoreThreadTimeOut;
```
默认值为false，如果为false，core线程在空闲时依然存活；如果为true，则core线程等待工作，直到时间超时至keepAliveTime；
# **嵌套类**
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/ThreadPoolExecutor_InnerClass.png)
```
ThreadPoolExecutor.Worker 
```
Worker类是ThreadPoolExecutor的一个非常重要的内部类，它是对工作线程的封装，Worker继承了AQS抽象类并且实现了Runnable接口。
```
ThreadPoolExecutor.AbortPolicy 
```
处理被拒绝任务的策略，抛出RejectedExecutionException；
```
ThreadPoolExecutor.CallerRunsPolicy
```
处理被拒绝任务的策略，它直接在execute方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务；
```
ThreadPoolExecutor.DiscardOldestPolicy
```
处理被拒绝任务的策略，它丢弃最旧的未处理请求，然后重试execute；如果执行程序已关闭，则会丢弃该任务；
```
ThreadPoolExecutor.DiscardPolicy
```
处理被拒绝任务的策略，默认情况下它将丢弃被拒绝的任务；
# **构造方法** 
1、采用默认ThreadFactory和RejectedExecutionHandler调用构造方法4
```
ThreadPoolExecutor(int, int, long, TimeUnit, BlockingQueue<Runnable>){
}
```
2、采用默认ThreadFactory调用构造方法4
```
ThreadPoolExecutor(int, int, long, TimeUnit, BlockingQueue<Runnable>, RejectedExecutionHandler) {
}
```
3、采用默认RejectedExecutionHandler调用构造方法4
```
ThreadPoolExecutor(int, int, long, TimeUnit, BlockingQueue<Runnable>, ThreadFactory) {
}
```
4、前3种构造方法最终都调用这个构造方法
```
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {
    if (corePoolSize < 0 ||
        maximumPoolSize <= 0 ||
        maximumPoolSize < corePoolSize ||
        keepAliveTime < 0)
        throw new IllegalArgumentException();
    if (workQueue == null || threadFactory == null || handler == null)
        throw new NullPointerException();
    this.corePoolSize = corePoolSize;
    this.maximumPoolSize = maximumPoolSize;
    this.workQueue = workQueue;
    this.keepAliveTime = unit.toNanos(keepAliveTime);
    this.threadFactory = threadFactory;
    this.handler = handler;
}
```
参数说明：
- **corePoolSize**
    - 核心线程数
    - 如果线程池中线程数量小于corePoolSize，即便现有线程有空闲也会创建新线程来运行新任务
- **maximumPoolSize**
    - 最大线程数
    - 如果线程池中线程数量大于corePoolSize并且任务队列满时会创建新线程来运行新任务
- **keepAliveTime**
    - 线程存活时间
    - 如果线程池中线程数量大于corePoolSize，则多余的线程在空闲时间超过keepAliveTime后会退出
- **unit**
    - keepAliveTime参数的时间单位
- **workQueue**
    - 执行任务之前用于保存任务的队列
    -  当运行的线程数少于corePoolSize时，在有新任务时直接创建新线程来执行任务而无需再进队列
    - 当运行的线程数等于或多于corePoolSize，在有新任务添加时则先加入队列，不直接创建线程
    - 当队列满时，在有新任务时就创建新线程
- **threadFactory**
    - 创建新线程时使用的工厂
    - 默认使用DefaultThreadFactory创建线程
- **handler**
    - 线程池数量已达上限，队列也已满时，处理被拒绝任务的策略
    - 默认使用AbortPolicy
# **execute方法** 
```
public void execute(Runnable command) {
    if (command == null)
        throw new NullPointerException();
    /*
     * Proceed in 3 steps:
     *
     * 1. If fewer than corePoolSize threads are running, try to
     * start a new thread with the given command as its first
     * task.  The call to addWorker atomically checks runState and
     * workerCount, and so prevents false alarms that would add
     * threads when it shouldn't, by returning false.
     *
     * 2. If a task can be successfully queued, then we still need
     * to double-check whether we should have added a thread
     * (because existing ones died since last checking) or that
     * the pool shut down since entry into this method. So we
     * recheck state and if necessary roll back the enqueuing if
     * stopped, or start a new thread if there are none.
     *
     * 3. If we cannot queue task, then we try to add a new
     * thread.  If it fails, we know we are shut down or saturated
     * and so reject the task.
     */
    int c = ctl.get();
    if (workerCountOf(c) < corePoolSize) {
        if (addWorker(command, true))
            return;
        c = ctl.get();
    }
    if (isRunning(c) && workQueue.offer(command)) {
        int recheck = ctl.get();
        if (! isRunning(recheck) && remove(command))
            reject(command);
        else if (workerCountOf(recheck) == 0)
            addWorker(null, false);
    }
    else if (!addWorker(command, false))
        reject(command);
}
```
源码注释中说明，execute方法进行了以下3步：
- 如果运行的线程数量小于corePoolSize，则尝试使用用户定义的Runnalbe对象创建一个新的线程。调用addWorker方法会原子性的检查runState和workCount，通过返回false来防止在不应该添加线程时添加了线程。
- 如果一个任务能够成功进入队列，我们仍需要双重检查是否应该增加一个线程。因为在上一次检查之后该线程死亡了；或者当进入到此方法时，线程池已经shutdown了，所以需要再次检查状态。如有必要，当停止时还需要回滚入队列操作，或者当线程池没有线程时需要创建一个新线程。
- 如果无法进入队列，那么尝试增加一个新线程。如果此操作失败，那么就意味着线程池已经shut down或者已经饱和了，所以拒绝任务。
# **addWorker方法** 
```
private boolean addWorker(Runnable firstTask, boolean core) {
    retry:
    for (;;) {
        int c = ctl.get();
        int rs = runStateOf(c);

        // Check if queue empty only if necessary.
        //如果线程池状态至少为STOP，返回false，不接受任务。
        //如果线程池状态为SHUTDOWN，并且firstTask不为null或者任务队列为空，同样不接受任务。
        if (rs >= SHUTDOWN &&
            ! (rs == SHUTDOWN &&
               firstTask == null &&
               ! workQueue.isEmpty()))
            return false;

        for (;;) {
            int wc = workerCountOf(c);
            //CAPACITY为(1<<29)-1，这是线程池中线程数真正的上界，绝不允许超过。
            //否则根据参数中是否以corePoolSize为上界进行判断，如果超过，则新增worker失败。
            if (wc >= CAPACITY ||
                wc >= (core ? corePoolSize : maximumPoolSize))
                return false;
            // 任务添加成功，增加任务数，跳出整个循环往下走
            if (compareAndIncrementWorkerCount(c))
                break retry;
            // 任务数添加有竞争，添加失败，重新获取ctl的值 
            c = ctl.get();  // Re-read ctl
            //本次线程运行状态与上次获取的状态不相同，重试外层循环
            if (runStateOf(c) != rs)
                continue retry;
            // else CAS failed due to workerCount change; retry inner loop
        }
    }

    boolean workerStarted = false;
    boolean workerAdded = false;
    Worker w = null;
    try {
        w = new Worker(firstTask);
        final Thread t = w.thread;
        if (t != null) {
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                // Recheck while holding lock.
                // Back out on ThreadFactory failure or if
                // shut down before lock acquired.
                int rs = runStateOf(ctl.get());

                if (rs < SHUTDOWN ||
                    (rs == SHUTDOWN && firstTask == null)) {
                    if (t.isAlive()) // precheck that t is startable
                        throw new IllegalThreadStateException();
                    workers.add(w);
                    int s = workers.size();
                    if (s > largestPoolSize)
                        largestPoolSize = s;
                    workerAdded = true;
                }
            } finally {
                mainLock.unlock();
            }
            if (workerAdded) {
                t.start();
                workerStarted = true;
            }
        }
    } finally {
        if (! workerStarted)
            addWorkerFailed(w);
    }
    return workerStarted;
}
```
源码注释说明：
- 检查根据当前线程池的状态是否允许添加一个新的Worker，如果可以，调整worker count
# **其他方法** 
```
public void shutdown()
```
继续运行之前提交到阻塞队列中的任务，不再接受新任务。
```
public List<Runnable> shutdownNow()
```
尝试停止所有正在执行的任务，停止等待任务的处理，并返回正在等待执行的任务的列表。
```
public boolean isShutdown()
```
如果executor已shut down，则返回true。
```
public boolean isTerminating()
```
如果此执行程序正处于shutdown()，shutdownNow()终止之后但尚未完全终止，则返回true。
```
public boolean isTerminated()
```
如果在shutdown后所有任务已完成，则返回true。请注意，除非是shutdown或shutdownNow先被调用，否则isTerminated永远不会是true。
```
public boolean awaitTermination(long timeout, TimeUnit unit)
```
阻塞，直到所有任务在关闭请求之后完成执行，或发生超时，或当前线程中断，以先发生者为准。



















