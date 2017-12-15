在上一篇[Java8新特性系列（一）](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/Java8Feature/Java8%E6%96%B0%E7%89%B9%E6%80%A7%E7%B3%BB%E5%88%97(%E4%B8%80).md)中，我们介绍了Java8新特性之默认方法和静态方法。  
本篇将介绍Java8新特性之**函数式接口**。
### **什么是函数式接口**  
通过查看JavaDoc官方API帮助文档，我们可以总结如下:  
#### **1、一个函数式接口只有一个抽象方法**    
这也是判断一个接口是不是函数式接口的主要依据。  
#### **2、一个函数式接口可以包含default方法**  
由于default方法自带实现，它们不是抽象的，所以default方法不会计入接口的抽象方法数量中。  
#### **3、一个函数式接口可以包含java.lang.Object的public方法**  
如果一个接口声明的方法覆盖了java.lang.Object的任意一个public方法，那么这个方法也不会计入接口的抽象方法数量中，这是因为对于这个接口的任意一个实现类默认都继承了java.lang.Object类，从而具有来自java.lang.Object对这些方法的实现。  
#### **4、一个函数式接口可以包含static方法**  
当然static方法不是抽象的，所以static方法不会计入接口的抽象方法数量中。  
#### **5、@FunctionalInterface**  
我们可以使用@FunctionalInterface来将接口标记为函数式接口，但这并不是强制性的；编译器会将满足函数式接口定义的任何接口视为函数式接口，而不管接口声明中是否存在注解@FunctionalInterface。但开发中最好的做法是将其与函数式接口一起使用，以避免意外添加额外的方法。如果接口添加了@FunctionalInterface注解，并且我们尝试使用多个抽象方法，则会引发编译时错误。  
#### **6、一个函数式接口可以继承多个父接口**  
当一个函数式接口继承多个父接口时，每个父接口中都只能存在一个抽象方法，且有相同的方法签名。  
### **如何理解函数式接口？**
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
示例9、继承多个父接口，父接口包含一个有相同方法签名的抽象方法，编译正常。
```java
//示例9-编译正常
@FunctionalInterface
public interface FunctionalInterfaceDemo extends SuperInterface1, SuperInterface2{

}
interface SuperInterface1 {
    void hello();
}

interface SuperInterface2 {
    void hello();
}
```
到这里，对于Java8函数式接口，我想你应该有一定的了解了。用这么长的篇幅来解释Java8函数式接口，一方面是帮助大家真正理解Java8函数式接口；另一方面也是因为网络上不乏“只包含一个方法就是函数式接口，包含多于一个方法就不是函数式接口”这种片面的错误认识。
### **Java内置的函数式接口**  
Java8之前已有的函数式接口：  
java.lang.Runnable、java.util.Comparator等等。  
Java8新增加的函数式接口：  
java.util.function
java.util.function中包含了很多类，用来支持Java的函数式编程。
### **为什么引入函数式接口**   
Java8引入函数式接口的概念，其实是为了更友好的支持Java8的另一个新特性:Lambda表达式。  
篇幅所限，这部分内容将在下一篇文章中讲到。
