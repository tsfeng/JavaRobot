###**Java8默认方法**​
Java 8 新增了接口的默认方法。
简单说，默认方法就是接口可以有实现方法，而且不需要实现类去实现其方法。
我们只需在方法名前面加个default关键字即可实现默认方法。
###**为什么要有这个特性**​
首先，之前的接口是个双刃剑，好处是面向抽象而不是面向具体编程，缺陷是，当需要修改接口时候，需要修改全部实现该接口的类，目前的Java 8之前的集合框架没有foreach方法，通常能想到的解决办法是在JDK里给相关的接口添加新的方法及实现。然而，对于已经发布的版本，是没法在给接口添加新方法的同时不影响已有的实现。所以引进的默认方法。他们的目的是为了解决接口的修改与现有的实现不兼容的问题。
###**语法**​
默认方法语法格式如下：
```java
public interface vehicle {
   default void print(){
      System.out.println("我是一辆车!");
   }
}
```
###**多个默认方法**​
一个接口有默认方法，考虑这样的情况，一个类实现了多个接口，且这些接口有相同的默认方法，以下实例说明了这种情况的解决方法：
```java
public interface vehicle {
   default void print(){
      System.out.println("我是一辆车!");
   }
}
 
public interface fourWheeler {
   default void print(){
      System.out.println("我是一辆四轮车!");
   }
}
```
第一个解决方案是创建自己的默认方法，来覆盖重写接口的默认方法：
```java
public class car implements vehicle, fourWheeler {
   default void print(){
      System.out.println("我是一辆四轮汽车!");
   }
}
```
第二种解决方案可以使用 super 来调用指定接口的默认方法：
```java
public class car implements vehicle, fourWheeler {
   public void print(){
      vehicle.super.print();
   }
}
```
###**静态默认方法**​
Java 8 的另一个特性是接口可以声明（并且可以提供实现）静态方法。例如：
```java
public interface vehicle {
   default void print(){
      System.out.println("我是一辆车!");
   }
    // 静态方法
   static void blowHorn(){
      System.out.println("按喇叭!!!");
   }
}
```
###**语法**​

