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
    - -1（32个1）左移29位，即高3位为111，低29位全部为0
- **SHUTDOWN**
    - RUNNING状态下调用shutdown方法后进入此状态
    - 不接受新任务，但会处理队列任务
    - 0（32个0）左移29位，，即高3位为000，低29位全部为0
- **STOP** 
    - RUNNING/SHUTDOWN状态下调用shutdownNow方法后进入此状态
    - 不接受新任务，不会处理队列任务，而且会中断正在处理过程中的任务
    - 1（31个0和1个1）左移29位，即高3位为001，低29位全部为0
- **TIDYING**
    - SHUTDOWN/STOP状态会过渡到此状态
    - 所有的任务已结束，工作线程数为0，将会执行terminated()方法
    - 2（30个0和10）左移29位，即高3位为010，低29位全部为0
- **TERMINATED**
    - TIDYING状态下，线程池执行完terminated()方法后进入此状态
    - 线程池已完全终止
    - 3（30个0和11）左移29位，即高3位为011，低29位全部为0
    
**由于有5种状态，最少需要3位表示，所以采用的AtomicInteger的高3位来表示。**  
ThreadPoolExecutor线程池状态变化如下图所示：
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/ThreadPoolExecutor_State.jpg)  
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
该属性用来控制是否允许核心线程超时退出，默认值为false。如果为false，core线程在空闲时依然存活；如果为true，则core线程等待工作，直到时间超时至keepAliveTime。
```
private final HashSet<Worker> workers = new HashSet<Worker>();
```
workers是包含线程池中所有工作线程worker的集合，仅仅当拥有mainLock锁时才能访问它。
```
private int largestPoolSize;
```
该变量记录了线程池在整个生命周期中曾经出现的最大线程个数。为什么说是曾经呢？因为线程池创建之后，可以调用setMaximumPoolSize()改变运行的最大线程的数目。仅仅当拥有mainLock锁时才能访问它。
```
private long completedTaskCount;
```
该变量记录已完成的任务数量。只有在worker线程终止时才更新。仅仅当拥有mainLock锁时才能访问它。
# **嵌套类**
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/ThreadPoolExecutor_InnerClass.png)
```
ThreadPoolExecutor.Worker 
```
Worker类是ThreadPoolExecutor的一个非常重要的内部类，它是对工作线程的封装，Worker继承了AQS抽象类并且实现了Runnable接口。所以其既是一个可执行的任务，又可以达到锁的效果。
```
private final class Worker
    extends AbstractQueuedSynchronizer
    implements Runnable
{
    /**
     * This class will never be serialized, but we provide a
     * serialVersionUID to suppress a javac warning.
     */
    private static final long serialVersionUID = 6138294804551838833L;

    /** Thread this worker is running in.  Null if factory fails. */
    final Thread thread;
    /** Initial task to run.  Possibly null. */
    Runnable firstTask;
    /** Per-thread task counter */
    volatile long completedTasks;

    /**
     * Creates with given first task and thread from ThreadFactory.
     * @param firstTask the first task (null if none)
     */
    Worker(Runnable firstTask) {
        //在调用runWorker()前，禁止interrupt中断
        setState(-1); // inhibit interrupts until runWorker
        this.firstTask = firstTask;
        this.thread = getThreadFactory().newThread(this);
    }

    /** Delegates main run loop to outer runWorker  */
    public void run() {
        runWorker(this);
    }

    // Lock methods
    //
    // The value 0 represents the unlocked state.
    // The value 1 represents the locked state.

    protected boolean isHeldExclusively() {
        return getState() != 0;
    }

    protected boolean tryAcquire(int unused) {
        if (compareAndSetState(0, 1)) {
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }

    protected boolean tryRelease(int unused) {
        setExclusiveOwnerThread(null);
        setState(0);
        return true;
    }

    public void lock()        { acquire(1); }
    public boolean tryLock()  { return tryAcquire(1); }
    public void unlock()      { release(1); }
    public boolean isLocked() { return isHeldExclusively(); }

    void interruptIfStarted() {
        Thread t;
        if (getState() >= 0 && (t = thread) != null && !t.isInterrupted()) {
            try {
                t.interrupt();
            } catch (SecurityException ignore) {
            }
        }
    }
}
```
之所以Worker自己实现Runnable，并创建Thread，在firstTask外包一层，是因为要通过Worker控制中断，而firstTask这个工作任务只是负责执行业务。
**Worker控制中断主要有以下几方面：**
- 初始AQS状态为-1，此时不允许中断interrupt()，只有在worker线程启动了，执行了runWoker()，将state置为0，才能中断；
- 为了防止某种情况下，在运行中的worker被中断，runWorker()每次运行任务时都会lock()上锁，而shutdown()这类可能会终止worker的操作需要先获取worker的锁，这样就防止了中断正在运行的线程
    - shutdown()线程池时，会对每个worker尝试tryLock()上锁，而Worker类这个AQS的tryAcquire()方法是固定将state从0->1，故初始状态state==-1时tryLock()失败，不能interrupt()；
    - shutdownNow()线程池时，不用tryLock()上锁，但调用worker.interruptIfStarted()终止worker，interruptIfStarted()也有state>=0才能interrupt的逻辑。

**Worker实现了一个简单的不可重入的互斥锁，而不是用ReentrantLock可重入锁**
- 因为我们不想让在调用比如setCorePoolSize()这种线程池控制方法时可以再次获取锁(重入)；
- setCorePoolSize()时可能会interruptIdleWorkers()，在对一个线程interrupt时会要w.tryLock()；
- 如果可重入，就可能会在对线程池操作的方法中中断线程；
- 类似其他方法setMaximumPoolSize()、setKeppAliveTime()、allowCoreThreadTimeOut等。
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
    //线程池是Running运行状态，并且可以把当前任务加入到队列
    if (isRunning(c) && workQueue.offer(command)) {
        int recheck = ctl.get();
         //线程池不是Running运行状态，并且可以从队列移除
        if (! isRunning(recheck) && remove(command))
            reject(command);
        //线程池没有可用线程，创建一个新线程
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

线程池的大致处理流程如下图所示：
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/ThreadPoolExecutor_Flow.png)
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
            //否则根据参数中是否以corePoolSize或maximumPoolSize为上界进行判断，如果超过，则新增worker失败。
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
    //运行到此处时，线程池运行的线程数已经成功+1
    boolean workerStarted = false;
    boolean workerAdded = false;
    Worker w = null;
    try {
        // 初始化worker-用当前firstTask创建一个Worker对象
        w = new Worker(firstTask);
        final Thread t = w.thread;
        if (t != null) {
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                // Recheck while holding lock.
                // Back out on ThreadFactory failure or if
                // shut down before lock acquired.
                // 由于获取锁之前线程池状态可能发生了变化，这里需要重新读一次状态。
                int rs = runStateOf(ctl.get());
                // 当线程池是RUNNING运行状态或者线程池是SHUTDOWN状态，任务是null，往下执行
                if (rs < SHUTDOWN ||
                    (rs == SHUTDOWN && firstTask == null)) {
                    // 线程必须保证是没有start的，这里做参数校验
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
            // 成功增加worker后，启动该worker线程。
            if (workerAdded) {
                t.start();
                workerStarted = true;
            }
        }
    } finally {
        // worker线程如果没有成功启动，回滚worker集合和worker计数器的变化。
        if (! workerStarted)
            addWorkerFailed(w);
    }
    return workerStarted;
}
```
执行流程说明：
- 检查当前线程池的状态是否允许添加一个新的Worker，不可以则直接返回false；如果可以，执行下一步；
    - 线程池状态>shutdown，可能为stop、tidying、terminated，不能添加worker线程；
    - 线程池状态==shutdown，firstTask不为空，不能添加worker线程，因为shutdown状态的线程池不接收新任务；
    - 线程池状态==shutdown，firstTask==null，workQueue为空，不能添加worker线程，因为firstTask为空是为了添加一个没有任务的线程再从workQueue获取task，而workQueue为空，说明添加无任务线程已经没有意义；
- 检查线程池当前线程数量是否超过上限，超过则返回false；没超过则对workerCount+1，继续下一步；
- 在线程池的ReentrantLock保证下，向Workers Set中添加新创建的worker实例，添加完成后解锁，并启动worker线程。如果这一切都成功了，return true；如果添加worker入Set失败或启动失败，调用addWorkerFailed()方法。
# **runWorker方法**
```
final void runWorker(Worker w) {
    Thread wt = Thread.currentThread();
    Runnable task = w.firstTask;
    w.firstTask = null;
    // new Worker()时state为-1，此处是调用Worker类的tryRelease()方法，将state置为0， 而interruptIfStarted()中只有state>=0才允许调用中断
    w.unlock(); // allow interrupts
    boolean completedAbruptly = true;
    try {
        while (task != null || (task = getTask()) != null) {
            //加锁，为了在shutdown()时不终止正在运行的worker
            w.lock();
            // If pool is stopping, ensure thread is interrupted;
            // if not, ensure thread is not interrupted.  This
            // requires a recheck in second case to deal with
            // shutdownNow race while clearing interrupt
            if ((runStateAtLeast(ctl.get(), STOP) ||
                 (Thread.interrupted() &&
                  runStateAtLeast(ctl.get(), STOP))) &&
                !wt.isInterrupted())
                wt.interrupt();
            try {
                beforeExecute(wt, task);
                Throwable thrown = null;
                try {
                    task.run();
                } catch (RuntimeException x) {
                    thrown = x; throw x;
                } catch (Error x) {
                    thrown = x; throw x;
                } catch (Throwable x) {
                    thrown = x; throw new Error(x);
                } finally {
                    afterExecute(task, thrown);
                }
            } finally {
                task = null;
                w.completedTasks++;
                w.unlock();
            }
        }
        completedAbruptly = false;
    } finally {
        processWorkerExit(w, completedAbruptly);
    }
}
``` 
执行流程说明：
- Worker线程启动后，通过Worker类的run()方法调用runWorker(this)；
- 执行任务之前，首先worker.unlock()，将AQS的state置为0，允许中断当前worker线程；
- 执行当前task或者通过getTask()从阻塞队列中获取任务；
- 获取到任务后，w.lock()加锁，执行beforeExecute()、task.run()、afterExecute()方法，加锁是为了防止在任务运行时被线程池一些中断操作中断；在执行完任务后会解锁，已完成任务数量加1；
- 执行过程中一旦出现异常，都会导致worker线程终止，进入processWorkerExit()处理worker退出的流程；
- 正常执行完当前task后，会通过getTask()从阻塞队列中获取新任务，继续循环；
- 没有新任务时，也进入processWorkerExit()处理worker退出的流程。
# **getTask方法** 
```
private Runnable getTask() {
    boolean timedOut = false; // Did the last poll() time out?

    for (;;) {
        int c = ctl.get();
        int rs = runStateOf(c);

        // Check if queue empty only if necessary.
        if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
            decrementWorkerCount();
            return null;
        }

        int wc = workerCountOf(c);

        // Are workers subject to culling?
        boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;

        if ((wc > maximumPoolSize || (timed && timedOut))
            && (wc > 1 || workQueue.isEmpty())) {
            if (compareAndDecrementWorkerCount(c))
                return null;
            continue;
        }

        try {
            Runnable r = timed ?
                workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
                workQueue.take();
            if (r != null)
                return r;
            timedOut = true;
        } catch (InterruptedException retry) {
            timedOut = false;
        }
    }
}
```
执行流程说明：
- 首先判断线程池状态是否可以满足从workQueue中获取任务的条件，不满足return null
    - 线程池状态为shutdown，且workQueue为空，没有新任务，故return null；
    - 线程池状态为stop（shutdownNow()会导致变成STOP），此时不接受新任务，中断正在执行的任务，故return null；
- 如果满足获取任务条件，根据是否需要定时获取调用不同方法：
    - workQueue.poll()：如果在keepAliveTime时间内，阻塞队列还是没有任务，返回null；
    - workQueue.take()：如果阻塞队列为空，当前线程会被挂起等待；当队列中有任务加入时，线程被唤醒，take方法返回任务；
- 在阻塞从workQueue中获取任务时，可以被interrupt()中断，代码中捕获了InterruptedException，重置timedOut为初始值false，再次循环，从头开始判断。
# **其他方法** 
```
public void shutdown() {
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
        checkShutdownAccess();
        advanceRunState(SHUTDOWN);
        interruptIdleWorkers();
        onShutdown(); // hook for ScheduledThreadPoolExecutor
    } finally {
        mainLock.unlock();
    }
    tryTerminate();
}
```
继续运行之前提交到阻塞队列中的任务，不再接受新任务。
首先会检查是否具有shutdown的权限，然后设置线程池的控制状态为SHUTDOWN，之后中断空闲的worker，最后尝试终止线程池。
```
public List<Runnable> shutdownNow() {
    List<Runnable> tasks;
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
        checkShutdownAccess();
        advanceRunState(STOP);
        interruptWorkers();
        tasks = drainQueue();
    } finally {
        mainLock.unlock();
    }
    tryTerminate();
    return tasks;
}
```
尝试停止所有正在执行的任务，停止等待任务的处理，并返回正在等待执行的任务的列表。
```
public boolean isShutdown() {
    return ! isRunning(ctl.get());
}
```
如果executor已shut down，则返回true。
```
public boolean isTerminating() {
    int c = ctl.get();
    return ! isRunning(c) && runStateLessThan(c, TERMINATED);
}
```
如果此执行程序正处于shutdown()，shutdownNow()终止之后但尚未完全终止，则返回true。
```
public boolean isTerminated() {
    return runStateAtLeast(ctl.get(), TERMINATED);
}
```
如果在shutdown后所有任务已完成，则返回true。请注意，除非是shutdown或shutdownNow先被调用，否则isTerminated永远不会是true。
```
public boolean awaitTermination(long timeout, TimeUnit unit)
    throws InterruptedException {
    long nanos = unit.toNanos(timeout);
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
        for (;;) {
            if (runStateAtLeast(ctl.get(), TERMINATED))
                return true;
            if (nanos <= 0)
                return false;
            nanos = termination.awaitNanos(nanos);
        }
    } finally {
        mainLock.unlock();
    }
}
```
阻塞，直到所有任务在关闭请求之后完成执行，或发生超时，或当前线程中断，以先发生者为准。



















