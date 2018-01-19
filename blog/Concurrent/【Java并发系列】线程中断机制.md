> 任务和线程的启动很容易。在大多数时候，我们都会让它们运行直到结束，或者让它们自行停止。然而，有时候我们希望提前结束任务或线程，或许是因为用户取消了操作，或者应用程序需要被快速关闭。  
> 要使任务和线程能安全、快速、可靠地停止下来，并不是一件容易的事。Java没有提供任何机制来安全地终止线程。但它提供了中断（interruption），这是一种协作机制，能够使一个线程终止另一个线程当前的工作。  
> 这种协作式的方法是必要的，我们很少希望某个任务、线程或服务立即停止，因为这种立即停止会使共享的数据结构处于不一致的状态。相反，在编写任务和服务时可以使用一种协作的方式：当需要停止时，它们首先会清除当前正在执行的工作，然后再结束。这提供了更好的灵活性，因为任务本身的代码比发出取消请求的代码更清楚如何执行清除工作。——《Java并发编程实战》  

Java中，启动一个线程，调用该线程的start方法即可。  
对应的，终止一个线程，通常情况下，有以下几种方法：  
- **线程自行停止**  
- **Thread.stop**   
   此方法已废弃，本篇不做过多介绍。
- **通过标记控制**  
- **通过线程中断机制**  
# **线程自行停止**
```java
public class Thread03Demo01 {
    private static Thread sThread;
    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("2==>" + sThread.getState());
            }
        });
        System.out.println("1==>" + sThread.getState());
        sThread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3==>" + sThread.getState());
    }
}
```
这段代码最终运行完成后，输入到控制台的信息如下：
```
1==>NEW 
2==>RUNNABLE 
3==>TERMINATED

```
通过输出信息我们验证了：
```
1、当我们new出一个Thread对象，该对象就进入了NEW状态。 
2、当调用start方法之后，线程就进入了RUNNABLE状态。 
3、当线程的工作任务，即run()方法中的内容执行完毕，线程就进入了TERMINATED状态。
```
也许有刚开始接触Java的朋友会好奇在调用sThread.start之后，为什么通过sleep让线程休眠了2秒。   
这其实也是值得一说的，那就是一定要明白RUNNABLE只是代表可执行，而并非立马执行。   
简单的说，也就是RUNNABLE状态下的线程仅仅是具备执行资格而已，到底轮不轮到它执行，还得看CPU大哥翻不翻你的牌子。   
所以，我们也可以记住这样一点：即使当前对线程调用了start方法，也不要以为就会即刻开始该线程run()方法的执行。  
不信我们可以试着将让线程sleep的代码删除，然后再次运行程序，会发现输出结果多半会变为：  
```
1==>NEW
3==>RUNNABLE
2==>RUNNABLE
```
道理很简单，调用了sThread.start，该线程就进入了RUNNABLE状态。可惜的是CPU没有翻它的牌子，而是选择了继续执行“main thread”的内容。 
于是我们看见先打印了标记为”3==>”的内容，这个时候主线程的内容执行完了，Cpu切换，才执行sThread，从而输出”2==>…”。
# **通过标记控制**
```java
public class Thread03Demo02 {
    private static Thread sThread;
    private static boolean KEEP_RUNNING = true;
    private static int position;

    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (KEEP_RUNNING) {
                    try {
                        if (++position < 5) {
                            Thread.sleep(2000);
                        } else {
                            KEEP_RUNNING = false;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        sThread.start();

        while (sThread.getState() != Thread.State.TERMINATED) {
            System.out.println("sThead's state is ==>" + sThread.getState());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(sThread.getState());
    }
}
```
运行该段代码的输出结果如下：
```
sThead’s state is ==>TIMED_WAITING 
sThead’s state is ==>TIMED_WAITING 
sThead’s state is ==>TIMED_WAITING 
sThead’s state is ==>TIMED_WAITING 
TERMINATED
```
我们来分析一下发生了什么：  
首先，sThread的run()方法中是一个while循环，**控制循环的标记是一个布尔型的变量KEEP_RUNNING。**   
然后，我们每一次循环我们都会对一个int型的变量position进行自增并且判断，当小于5，我们就会让线程休眠2秒。否则就修改循环标记。   
与此同时，我们在主线程当中也定义了一个while循环，判断的条件是只要sThread的状态不为TERMINATED，就在循环内输出sThread的状态。

我们需要明白这段程序运行的过程究竟是怎么样的，简单来说就是：   
在postion小于5的时候，因为在main thread与sThead中都有调用sleep方法，所以cpu资格会来回切换。   
也就是说，main thread和sThead会来回执行，每次position自增运算一次然后休眠，随之主线程输出一次sThead的状态。   
由此我们同时验证了另外一点，就是**调用带有超时参数的sleep方法，线程则会进入TIMED_WAITING状态。**  
当position自增运算到不再小于5，KEEP_RUNNING标记将被修改为false，这时sThread内的循环就将结束。   
这其实也就意味着sThread的线程任务执行完毕，于是线程就完成了中断。  
# **标记控制并非万能**
到目前为止，我们已经掌握了一些让线程中断的操作。  
通常来说，它们足够使用了。但是，正如你所见，这仅仅指通常来说。 
而编程的工作往往就有很多不通常的情况出现。那么，我们接着来看这样一段代码：  
```java
public class Thread03Demo03 {
    private static Thread sThread;
    private static boolean KEEP_RUNNING = true;

    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (KEEP_RUNNING) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i = 1; i <= 5; i++) {
                        System.out.println(i + "==>the thread is running...");
                    }
                }
            }
        });
        sThread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stopThread();

    }

    private static void stopThread() {
        System.out.println("stop sThread...");
        KEEP_RUNNING = false;
    }
}
```
输出结果如下：
```
stop sThread...
1==>the thread is running...
2==>the thread is running...
3==>the thread is running...
4==>the thread is running...
5==>the thread is running...
```
我们先不去分析这个结果出现的原因，至少从这个输出结果我们发现一件事情，那就是：  
**虽然我们已经在外部将循环标记KEEP_RUNNING设置为false了，却发现sThread在之后并未被中断，仍然执行了一次任务。**  

这个结果出现的原因实际上也不是那么难理解，说到底仍然是cpu执行权切换造成的。   
当sThread执行了start()，接着主线程首先sleep 2s。于是sThread开始执行，这时判断标记为true，进入循环，此时sThread则会sleep，休眠2秒。   
那么这个时候cpu便切换到了主线程运行，于是stopThread()方法得以执行，输出对应信息，然后设置KEEP_RUNNING为false。   
理想状态下，这个时候sThread就应该中断了才对。**但遗憾的是恢复之后线程并不是从头开始循环判断，而是接着sThread休眠之前的位置开始执行。**  
那么就造成了sThread恢复之后，会跳过while循环，接着上次sleep的代码之后执行，于是for循环开始，输出对应信息。  

于是我们发现了：  
**如果想要操作的线程当前处于阻塞的状态，那么通过标记的方式并不能够确保一定能让指定线程立刻中断。**  
所以很显然，我们需要另外一种方式来确保线程的中断了。  
这个时候如果去网上查资料或者自己查看方法列表，   
多半就有这样一个方法出现在你的眼前，那就是”interrupt“。
# **线程中断机制**
Java提供了线程中断机制，在Thread类中包含了中断线程以及查询线程中断状态的方法，如下：
- **public void interrupt()**
- **public static boolean interrupted()** 
- **public boolean isInterrupted()**

> 每个线程都有一个boolean类型的中断状态。当中断线程时，这个线程的中断状态将被设置为true。  
> interrupt方法能中断目标线程，  
> 而isInterrupted方法能返回目标线程的中断状态。    
> 静态的interrupted方法将清除当前线程的中断状态，并返回它之前的值，这也是清除中断状态的唯一方法。——《Java并发编程实战》  

这个时候，似乎一切就变得很容易了。但实际上通过使用，你会发现并没有那么容易。   
我们看到interrupt方法的命名，会很容易有一种错觉，那就是：如果我们想在某个时候中断一个线程的执行，那么就在外部调用这个方法。   
看上去十分的有理有据，但我们要做的，自然就是通过代码来验证一下是否真的行得通：  
```java
public class Thread03Demo04 {
    private static Thread sThread;
    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println("the thread is running");
                    sThread.interrupt();
                }
            }
        });
        sThread.start();
    }
}
```
在以上代码中，按照我们的推断来说，程序应该在输出一次”the thread is running”后，则被中断。   
但实际情况并非如此，我们会发现程序仍然将无限的输出该信息。这是怎么回事呢？  

那么，我们究竟应该怎么来使用interrupt方法呢？实际上我们只需要明白一个原理：   
**那就是该方法虽然名为“中断”，但实际上它做的工作并不是真的中断线程，而是去设置一种“中断状态”。**   
所以说，与其说interrupt是去中断线程，不如说它是在设置一个标识，告诉线程当前应该或者说可以被中断了。    
这个时候我们发现，如果是这样说来的话，那么这与我们之前说的循环标记似乎没什么区别啊？  
确实是这样的，因为通常我们都可以这样类似下面这样去使用它：  
```java
public class Thread03Demo05 {
    private static Thread sThread;
    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!sThread.isInterrupted()) {
                    System.out.println("the thread is running");
                    sThread.interrupt();
                }
            }
        });
        sThread.start();
    }
}
```
与之前我们通过循环标记控制线程中断一样，不同在于之前我们将标记设为false，这里我们调用interrupt方法。  

这个时候我们的疑问就在于，如果是这样，那么之前我们通过循环标记来控制中断可能遇到的问题，现在不是依然还会遇到吗？   
看上去的确如此，但毕竟实践才能出真知。所以我们将之前“标记控制并非万能”一节中的用例中的代码修改如下：  
```java
public class Thread03Demo06 {
    private static Thread sThread;
    private static boolean KEEP_RUNNING = true;

    public static void main(String[] args) {
        sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (KEEP_RUNNING) {
                    System.out.println("Thread is beginning");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i = 1; i <= 5; i++) {
                        System.out.println(i + "==>the thread is running...");
                    }
                }
            }
        });
        sThread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stopThread();

    }

    private static void stopThread() {
        System.out.println("stop sThread...");
        sThread.interrupt();
    }
}
```
执行代码，如下：
```
Thread is beginning
stop sThread...
1==>the thread is running...
2==>the thread is running...
3==>the thread is running...
4==>the thread is running...
5==>the thread is running...
Thread is beginning
java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at com.tsfeng.cn.concurrency.Thread03Demo06$1.run(Thread03Demo06.java:18)
	at java.lang.Thread.run(Thread.java:748)
1==>the thread is running...
```
由此我们注意到：当我们对线程调用interrupt方法后，抛出了一个异常。   
别急，我们现在回忆一下之前通过循环标记控制线程中断的时候情况如何？  
是的，正常情况下我们是可以实现目的的。   
而出现意外的情况是怎么样的呢？  
**没错，正是我们在修改循环标记的时候，目标线程正处于阻塞状态当中。**   
那么，什么时候，线程会进入到阻塞状态当中呢？  
通过之前对线程状态的分析我们知道情况很多，例如线程调用了sleep，wait，join等方法。   
很显然，Java的设计者们自然考虑到了这一点，既然意外的情况就出现在线程为阻塞的情况下，那么针对意外的情况作出针对就行了。   
于是**当我们调用interrupt方法去中断线程，Java会判断该线程是否处于阻塞，如果是则会抛出InterruptedException，并且清除之前设置的中断状态。**   
这自然也就是为什么，我们在代码中使用sleep，wait，join等方法时，编译器必须要求我们处理编译时异常InterruptedException的原因了。  

到了现在，我们要做的实际上就很简单了。既然通过标记控制中断，会在线程阻塞时出现意外；   
但是，如果对处于阻塞状态的线程调用interrupt方法又会抛出异常，那么，我们只要在异常中作出处理让线程中断就搞定了。    
修改异常处理的代码：  
```
try {
    Thread.sleep(5000);
} catch (InterruptedException e) {
    return;
}
```
再次运行程序，我们查看输出结果，就会发现线程已经正常中断了：
```
Thread is beginning
stop sThread...
```
于是，我们在这里总结一下**interrupt最常见的两种使用方式：**
- **通过isInterrupted()作为while循环的条件，配合interrupt()可以控制线程的中断。**
- **而如果想要强行将某个处于阻塞状态的线程从阻塞状态中唤醒，实际上也可以通过interrupt()方法，不过记得处理异常。**

