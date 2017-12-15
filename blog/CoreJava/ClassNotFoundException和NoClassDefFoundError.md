ClassNotFoundException和NoClassDefFoundError都发生在当JVM无法找到classpath中的请求的类时。
虽然他们看起来很相似，但这两者之间有一些本质的区别。
###**ClassNotFoundException**
ClassNotFoundException是一个受检异常，它发生在当应用程序尝试通过其完全限定的名称加载类，并且在类路径中找不到它的定义时。
这主要发生在尝试使用Class.forName()，ClassLoader.loadClass()或ClassLoader.findSystemClass()加载类时。因此，在处理反射时，我们需要特别小心java.lang.ClassNotFoundException 。
例如，让我们尝试加载JDBC驱动程序类而不添加必要的依赖关系，这将使我们得到  ClassNotFoundException：
```java
public class ClassNotFoundExceptionDemo {
    @Test
    public void test() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
}
```
###**NoClassDefFoundError**
NoClassDefFoundError是一个致命的错误。当JVM或者ClassLoader实例在尝试执行以下操作而无法找到类的定义时，会发生这种情况：
1、通过使用new关键字来实例化一个类 
2、用方法调用加载一个类
该错误发生在编译器可以成功编译该类，但JVM运行时找不到该类文件。它通常发生在执行静态块或初始化类的静态字段时发生异常，所以类初始化失败。
用一个简单的方法来重现这个问题。ClassWithInitErrors 初始化抛出一个异常。所以，当我们尝试创建一个ClassWithInitErrors的对象时  ，它会抛出  ExceptionInInitializerError异常。 
如果我们尝试再次加载同一个类，就会得到NoClassDefFoundError：
```java
public class NoClassDefFoundErrorDemo {
    public ClassWithInitErrors getClassWithInitErrors() {
        ClassWithInitErrors test;
        try {
            test = new ClassWithInitErrors();
        } catch (Throwable t) {
            System.out.println(t);
        }
        test = new ClassWithInitErrors();
        return test;
    }
    @Test
    public void test() {
        NoClassDefFoundErrorDemo sample = new NoClassDefFoundErrorDemo();
        sample.getClassWithInitErrors();
    }
}

class ClassWithInitErrors {
    static int data = 1 / 0;
}
```
###**解决办法**
有时，诊断和修复这两个问题可能会相当耗时。这两个问题的主要原因是运行时类文件（在类路径中）不可用。
处理这些问题时可以考虑的几种方法：
1、我们需要确定包含该类的类或jar在类路径中是否可用。如果没有，我们需要添加它。
2、如果它在应用程序的类路径中可用，那么很有可能类路径被覆盖。为了解决这个问题，我们需要找到我们的应用程序使用的确切类路径。
3、另外，如果一个应用程序使用多个类加载器，则由一个类加载器加载的类可能不会被其他类加载器使用。为了排除故障，了解classloader如何在Java中工作是非常重要的。
###**总结**
```table
ClassNotFoundException(<)     |      NoClassDefFoundError(<)   
它的类型是java.lang.Exception        |      它的类型是java.lang.Error               
它发生在应用程序试图在运行时加载一个没有在类路径中更新的类        |      它发生在Java运行时系统没有找到一个类定义，它是在编译时出现，但在运行时丢失
它由应用程序本身抛出。它是由像Class.forName（），loadClass（）和findSystemClass（）方法抛出|它由Java运行时系统抛出
