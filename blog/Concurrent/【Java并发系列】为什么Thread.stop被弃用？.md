## **为什么Thread.stop被弃用？**
因为它本质上是不安全的。  
（使用Thread.stop）停止一个线程会导致它解锁所有已锁定的监视器。（当ThreadDeath异常传播到堆栈时，监视器被解锁。）  
如果之前由这些监视器保护的任何对象处于不一致的状态，其他线程现在可以以不一致的状态查看这些对象。这种对象被称为受损对象。  
当线程操作受损对象时，可能导致任意行为。这种行为可能是微妙的，难以察觉，也可能会比较明显。  
与其他未经检查的异常不同，ThreadDeath静默杀死线程；因此，用户得不到程序可能会崩溃的警告。在发生实际损害后，崩溃现象可能会随时出现，甚至在未来数小时甚至数天。

## **难道我不能仅捕获ThreadDeath异常并修复受损对象？**  
在理论上，也许可以，但是这会使编写正确的多线程代码的任务大大复杂化。  
由于两个原因，这个任务几乎是无法完成的：  
- 线程几乎可以在任何地方抛出ThreadDeath异常 。考虑到这一点，所有同步的方法和代码块都必须进行非常详细的研究。  
- 一个线程可以在清理第一个异常（在catch或 finally子句中）的时候抛出第二个ThreadDeath异常。清理将不得不重复，直到成功。代码来确保这将是相当复杂的。
  
总之，这是不实际的。

## **那么Thread.stop(Throwable)呢？**
除了上面提到的所有问题之外，这个方法还可以用来生成目标线程不准备处理的异常（包括若非为实现此方法，线程不可能抛出的受检异常）。  
例如，下面的方法在行为上与Java的throw操作相同 ，但绕过了编译器试图保证调用方法已经声明了可能抛出的所有受检异常：
```
static void sneakyThrow(Throwable t) {
    Thread.currentThread().stop(t);
}
```
## **我应该用什么来代替Thread.stop？**
大部分stop的用法，应该被替换为只是简单地修改一些变量来指示目标线程应该停止运行的代码。  
目标线程应该定期检查这个变量，并且如果变量指示它将停止运行，则从其run方法有序地返回。  
为了确保停止请求的及时性，变量必须是volatile的（或者必须同步对变量的访问）。  
例如，假设你的applet包含以下start，stop和run方法：  
```
private Thread blinker;

public void start() {
    blinker = new Thread(this);
    blinker.start();
}

public void stop() {
    blinker.stop();  // UNSAFE!
}

public void run() {
    while (true) {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e){
        }
        repaint();
    }
}
```
为了避免使用 Thread.stop，你可以把applet的stop和run方法替换成：
```
private volatile Thread blinker;

public void stop() {
    blinker = null;
}

public void run() {
    Thread thisThread = Thread.currentThread();
    while (blinker == thisThread) {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e){
        }
        repaint();
    }
}
```
## **如何停止等待很长时间的线程（例如，输入）？**
这正是Thread.interrupt方法的用途。  
可以使用上面所示的相同的“基于状态”的信号机制，但是状态改变（blinker = null，在前面的例子中）后面可以跟随一个 Thread.interrupt调用，用来中断等待：  
```
public void stop() {
    Thread moribund = waiter;
    waiter = null;
    moribund.interrupt();
}
```
为了使这种技术起作用，关键在于，对于任何捕获了中断异常但不准备立即处理的方法，应重新断言异常。  
我们说**重新断言(reasserts)** 而不是**重新抛出(rethrows)**，因为它不总是可能重新抛出异常。  
如果捕获InterruptedException的方法没有被声明为抛出这个(受检)异常，那么它应该用下面的咒语“重新中断自己”：  
```
Thread.currentThread().interrupt();
```
这确保线程将InterruptedException尽快重新启动 。  

## **如果一个线程不响应Thread.interrupt呢？** 
在某些情况下，您可以使用特定于应用程序的技巧。  
例如，如果某个线程正在等待已知的socket，则可以关闭socket以使该线程立即返回。  
不幸的是，实际上没有任何可行的通用技术。  
**应该注意的是，在等待线程不响应Thread.interrupt的所有情况下，它也不会响应Thread.stop。**  
这种情况包括故意的拒绝服务攻击，以及Thread.stop和Thread.interrupt不能正常工作的I / O操作。
  
## **为什么Thread.suspend和Thread.resume被弃用？**
Thread.suspend本质上是容易出现死锁的。  
如果目标线程在保护关键系统资源的监视器上挂起了一个锁定，则其他线程无法访问此资源，直到目标线程恢复。  
如果要恢复目标线程的线程在调用resume之前试图锁定此监视器，则会导致死锁。这种死锁通常表现为“frozen”的过程。    

## **我应该用什么来代替Thread.suspend和 Thread.resume？**
同Thread.stop，谨慎的方法是让“目标线程”轮询一个指示线程所需状态的变量（活动或挂起）。  
当期望的状态被挂起时，线程等待使用Object.wait。当线程恢复时，使用Object.notify通知目标线程。  
例如，假设您的applet包含以下mousePressed事件句柄，该事件句柄用来切换一个被称为blinker的线程的状态：  
```
private boolean threadSuspended;

Public void mousePressed(MouseEvent e) {
    e.consume();

    if (threadSuspended)
        blinker.resume();
    else
        blinker.suspend();  // DEADLOCK-PRONE!

    threadSuspended = !threadSuspended;
}
```
要避免使用Thread.suspend和Thread.resume，你可以把上述事件句柄替换为：
```
public synchronized void mousePressed(MouseEvent e) {
    e.consume();

    threadSuspended = !threadSuspended;

    if (!threadSuspended)
        notify();
}
```
并将下面的代码添加到“运行循环”中：
```
synchronized(this) {
    while (threadSuspended)
        wait();
}
```
该wait方法抛出InterruptedException，所以它必须在一个try...catch子句中。使用sleep方法时，也可以将其放入同样的语句中。  
检查应该在sleep方法后（而不是先于），以便当线程恢复的时候窗口被立即重绘。  
修改后的run方法如下：
```
public void run() {
    while (true) {
        try {
            Thread.sleep(interval);

            synchronized(this) {
                while (threadSuspended)
                    wait();
            }
        } catch (InterruptedException e){
        }
        repaint();
    }
}
```
请注意，mousePressed方法中的notify和run方法中的wait都是在 synchronized语句块中的。  
这是语言所要求的，并确保wait和notify被正确串行化执行。  
实际上，这可以消除可能导致“暂停”线程错过notify并无限期中断的竞态条件。  
虽然Java中同步的成本随着平台的成熟而下降，但它永远不会免费。  
一个简单的技巧可以用来消除我们已经添加到“运行循环”的每个迭代中的同步。被添加的同步块被稍微更复杂的一段代码所取代，只有当该线程实际上被挂起时才进入同步块：  
```
if (threadSuspended) {
    synchronized(this) {
        while (threadSuspended)
            wait();
    }
}
```
在没有显式同步的情况下，threadSuspended必须被设置为volatile，以确保挂起请求的及时性。  
由此产生的run方法是：
```
private volatile boolean threadSuspended;

public void run() {
    while (true) {
        try {
            Thread.sleep(interval);

            if (threadSuspended) {
                synchronized(this) {
                    while (threadSuspended)
                        wait();
                }
            }
        } catch (InterruptedException e){
        }
        repaint();
    }
}
```
## **我可以结合使用这两种技术来生成一个可以安全“停止”或“暂停”的线程吗？**
是的，这是相当简单的。  
其中一个微妙之处在于，当另一个线程试图停止目标线程时，目标线程可能已经被挂起。如果stop方法只是将状态变量（blinker）设置为null，则目标线程将保持挂起状态（等待在监视器上），而不是像原来那样优雅地退出。如果applet重新启动，多个线程可能最终同时在监视器上等待，导致不稳定的行为。  
为了纠正这种情况，stop方法必须确保目标线程在挂起时立即恢复。一旦目标线程恢复，它必须立即识别出它已经被停止，并正常退出。  
下面是修改过的run和stop方法：  
```
public void run() {
    Thread thisThread = Thread.currentThread();
    while (blinker == thisThread) {
        try {
            Thread.sleep(interval);

            synchronized(this) {
                while (threadSuspended && blinker==thisThread)
                    wait();
            }
        } catch (InterruptedException e){
        }
        repaint();
    }
}

public synchronized void stop() {
    blinker = null;
    notify();
}
```
如果stop方法调用Thread.interrupt，如上所述，它也不需要调用notify，但它仍然必须被同步。这确保目标线程不会由于竞态条件而错过中断。  

## **那么Thread.destroy呢？**
Thread.destroy从未被实现，并已被弃用。  
如果被实现的话，就会像Thread.suspend一样容易出现死锁。（实际上，它大致相当于Thread.suspend没有后续的Thread.resume。）  

## **为什么Runtime.runFinalizersOnExit被弃用？**
因为它本质上是不安全的。  
这可能会导致在活动对象上调用finalizers，而其他线程正在并发操作这些对象，从而导致不稳定的行为或死锁。  
如果正在被终结对象的类被编码为“防御”这种调用，那么这个问题是可以避免的，但是大多数程序员不会这么做。他们假设一个对象在调用finalizer的时候已经死了。  
此外，从设置VM全局标志的意义上说，这个调用不是“线程安全的”。这迫使每个带有终结器的类防御活动对象的终结！  


[原文链接](https://docs.oracle.com/javase/8/docs/technotes/guides/concurrency/threadPrimitiveDeprecation.html)