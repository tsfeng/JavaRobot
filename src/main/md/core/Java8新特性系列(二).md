Java8于2014年3月18日发布，Java8作为Java的一个重大版本，新增了非常多的特性；但很多Java开发人员由于项目原因，在工作中没机会去使用Java8的新特性。目前Java9已于2017年9月21日正式发布，如果你对Java8的新特性还不是很了解，但愿《Java8新特性系列》文章对你有所帮助。
本篇将介绍Java8新特性之**函数式接口**和**Lambda表达式**。
###**什么是函数式接口**
通过查看JavaDoc官方API帮助文档，我们可以总结如下:
1、**一个函数式接口只有一个抽象方法**。这也是判断一个接口是不是函数式接口的主要依据，不是唯一。
2、**一个函数式接口可以包含default方法**；由于default方法自带实现，它们不是抽象的，所以default方法不会计入接口的抽象方法数量中。
3、**一个函数式接口可以包含java.lang.Object的public方法**；如果一个接口声明的方法覆盖了java.lang.Object的任意一个public方法，那么这个方法也不会计入接口的抽象方法数量中，这是因为对于这个接口的任意一个实现类默认都继承了java.lang.Object类，从而具有来自java.lang.Object对这些方法的实现。
4、**一个函数式接口可以包含static方法**；当然static方法不是抽象的，所以static方法不会计入接口的抽象方法数量中。
5、**@FunctionalInterface**；我们可以使用@FunctionalInterface来将接口标记为函数式接口，但这并不是强制性的；编译器会将满足函数式接口定义的任何接口视为函数式接口，而不管接口声明中是否存在注解@FunctionalInterface。但开发中最好的做法是将其与函数式接口一起使用，以避免意外添加额外的方法。如果接口添加了@FunctionalInterface注解，并且我们尝试使用多个抽象方法，则会引发编译时错误。
###**如何理解函数式接口？**
我们通过一组代码示例来帮助理解Java8函数式接口的定义:
示例1、没有任何方法，产生编译时错误。
```java
//示例1-编译时错误
@FunctionalInterface
public interface FunctionalInterfaceDemo {
    
}
```
示例2、包含一个抽象方法，编译正常。
```java
//示例2-编译正常
@FunctionalInterface
public interface FunctionalInterfaceDemo {
    void hello();
}
```
示例3、包含2个抽象方法，产生编译时错误。
```java
//示例3-编译时错误
@FunctionalInterface
public interface FunctionalInterfaceDemo {
    void hello();
    void world();
}
```
示例4、包含一个抽象方法和一个default方法，编译正常。
```java
//示例4-编译正常
@FunctionalInterface
public interface FunctionalInterfaceDemo {
    void hello();
    default void world(){
        System.out.println("world"); 
    };
}
```
示例5、包含一个抽象方法和一个static方法，编译正常。
```java
//示例5-编译正常
@FunctionalInterface
public interface FunctionalInterfaceDemo {
    void hello();
    static void world(){
        System.out.println("world"); 
    };
}
```
示例6、包含一个java.lang.Object的public方法(toString)，产生编译时错误。
```java
//示例6-编译时错误
@FunctionalInterface
public interface FunctionalInterfaceDemo {
    String toString();
}
```
示例7、包含一个java.lang.Object的public方法和一个抽象方法，编译正常。
```java
//示例7-编译正常
@FunctionalInterface
public interface FunctionalInterfaceDemo {
    void hello();
    String toString();
}
```
示例8、包含一个java.lang.Object的protected方法(finalize)和一个抽象方法，产生编译时错误。
```java
//示例8-编译时错误
@FunctionalInterface
public interface FunctionalInterfaceDemo {
    void hello();
    void finalize();
}
```
到这里，对于Java8函数式接口，我想你应该有一个深刻的理解了。用这么长的篇幅来解释Java8函数式接口，一方面是帮助大家真正理解Java8函数式接口；另一方面也是因为网络上不乏“只包含一个方法就是函数式接口，包含多于一个方法就不是函数式接口”这种片面的错误认识。
###**为什么引入函数式接口**
在搞明白这个问题之前，我们可能需要先了解一下**函数式编程**。
我们从第一次学习Java就知道，Java的一大特性就是面向对象。这也意味着Java程序里的所有内容都围绕着Java对象。
如果我们了解过一些其他的编程语言，如C ++，JavaScript；他们被称为函数式编程语言是因为我们可以编写函数并在需要时使用它们。其中一些语言是即支持面向对象编程，也支持函数式编程的。而在Java中，没有单独存在的函数，它们是Class的一部分，我们需要使用类/对象来调用任何函数。
例如:假设我们必须创建一个Runnable的实例。通常我们使用下面的匿名类来做。
```java
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("run1...");
    }
};
```
如果你看看上面的代码,实际使用的是在run()方法中的代码。剩下的所有代码是因为java程序的结构化方式。
Java8引入函数式接口的概念，其实是为了更友好的支持Java8的另一个新特性:Lambda表达式。
