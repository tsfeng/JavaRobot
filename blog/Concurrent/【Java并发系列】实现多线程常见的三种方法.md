Java中实现多线程常见的有三种方法：
- 继承Thread类
- 实现Runnable接口
- 通过Callable和Future
# **继承Thread类** 
（1）定义Thread类的子类，并重写该类的run方法，该run方法的方法体就代表了线程要完成的任务。因此把run()方法称为执行体。  
（2）创建Thread子类的实例，即创建了线程对象。  
（3）调用线程对象的start()方法来启动该线程。  
```java
public class Thread01Demo01 {
    public static void main(String[] args) {
        MyThread0101 t1 = new MyThread0101();
        MyThread0101 t2 = new MyThread0101();
        MyThread0101 t3 = new MyThread0101();
        t1.start();
        t2.start();
        t3.start();
    }
}
class MyThread0101 extends Thread {
    private int ticket = 5;
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + " ticket = " + ticket--);
            }
        }
    }
}
```
# **实现Runnable接口** 
（1）定义runnable接口的实现类，并重写该接口的run()方法，该run()方法的方法体同样是该线程的线程执行体。  
（2）创建 Runnable实现类的实例，并依此实例作为Thread的target来创建Thread对象，该Thread对象才是真正的线程对象。  
（3）调用线程对象的start()方法来启动该线程。  
```java
public class Thread01Demo02 {
    public static void main(String[] args) {
        MyThread0102 mt = new MyThread0102();
        // 启动3个线程t1,t2,t3(它们共用一个Runnable对象)，这3个线程一共卖5张票！
        Thread t1 = new Thread(mt);
        Thread t2 = new Thread(mt);
        Thread t3 = new Thread(mt);
        t1.start();
        t2.start();
        t3.start();
    }
}
class MyThread0102 implements Runnable {
    private int ticket = 5;
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + " ticket = " + ticket--);
            }
        }
    }
}
```
# **通过Callable和Future** 
（1）创建Callable接口的实现类，并实现call()方法，该call()方法将作为线程执行体，并且有返回值。  
（2）创建Callable实现类的实例，使用FutureTask类来包装Callable对象，该FutureTask对象封装了该Callable对象的call()方法的返回值。  
（3）使用FutureTask对象作为Thread对象的target创建并启动新线程。  
（4）调用FutureTask对象的get()方法来获得子线程执行结束后的返回值。  
```java
public class Thread01Demo03 {
    public static void main(String[] args) {
        MyThread0103 myThread0103 = new MyThread0103();
        FutureTask<Integer> ft = new FutureTask<>(myThread0103);
        Thread t1 = new Thread(ft);
        t1.start();
        try {
            Integer i = ft.get();
            System.out.println("返回值：" + i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
class MyThread0103 implements Callable {
    private int ticket = 5;
    @Override
    public Object call() throws Exception {
        for (int i = 0; i < 10; i++) {
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + " ticket = " + ticket--);
            }
        }
        return null;
    }
}
```
# **创建线程的三种方式对比**  
1. 采用实现Runnable、Callable接口的方式创见多线程时，线程类只是实现了Runnable 接口或Callable接口，还可以继承其他类。  
2. 采用实现Runnable、Callable接口的方式创见多线程时，多个线程可以共享同一个target对象，所以非常适合多个相同线程类处理同一份资源的情况。  
3. 使用继承Thread类的方式创建多线程时，编写简单，如果需要访问当前线程，则无需使用Thread.currentThread()方法，直接使用this即可获得当前线程。  
4. Callable接口类似于Runnable，但是Runnable不会返回结果，并且无法抛出返回结果的异常。而Callable功能更强大一些，被线程执行后，Future可以拿到异步执行任务的返回值，且Callable会在任务无法计算结果时抛出异常。  

