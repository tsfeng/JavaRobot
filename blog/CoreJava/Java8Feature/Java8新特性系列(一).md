Java8于2014年3月18日发布，Java8作为Java的一个重大版本，新增了非常多的特性；但很多Java开发人员由于项目原因，在工作中没机会去使用Java8的新特性。目前Java9已于2017年9月21日正式发布，如果你对Java8的新特性还不是很了解，但愿《Java8新特性系列》文章对你有所帮助。
本篇将介绍Java8新特性之interface中的default和static方法。  

###**Java8接口**  

我们知道，在Java8之前，接口不能具有方法体。当需要修改接口的时候，需要修改全部实现该接口的类。比如，Java8之前的集合框架没有foreach方法，通常能想到的解决办法是在JDK里给相关的接口添加新的方法及实现。然而，对于已经发布的版本，是没法在给接口添加新方法的同时不影响已有的实现。所以从Java8开始，接口被增强，我们可以在接口中使用**默认方法**和**静态方法**。他们的目的是为了解决接口的修改与现有的实现不兼容的问题。  

###**接口默认方法**

在Java8中，创建一个默认方法很简单，用default关键字修饰接口方法即可，例如：

```java
public interface Interface1 {
	void method1(String str);
	default void log(String str){
		System.out.println("I1 logging::"+str);
	}
}
```
上面代码中的log(String str)方法就是一个默认方法，当一个类实现接口Interface1时，并不强制为接口的默认方法提供实现。这个特性使我们能够以添加新方法的方式扩展接口，我们所需要做的只是为新方法提供一个默认的实现。
假设我们有另外一个接口如下：

```java
public interface Interface2 {
	void method2();
	default void log(String str){
		System.out.println("I2 logging::"+str);
	}
}
```
我们知道在Java中，类不支持多重继承，因为那样的话编译器无法知道该使用哪一个父类。对于接口默认方法，如果一个类同时实现了上面的接口Interface1和接口Interface2，而没有对默认方法log提供自己的实现，那么编译器同样不知道该选择哪一个。
所以为了确保这个问题不会在接口中出现，必须为通用的接口默认方法提供实现。所以如果一个类正在实现上述两个接口，则必须为log()方法提供实现，否则编译器会抛出编译时错误。
###**接口默认方法总结**  

1、Java8接口的默认方法帮助我们扩展接口，而不用担心破坏实现类。  
2、Java接口的默认方法帮助我们去除基类的实现类，我们可以提供默认的实现，实现类可以选择重写哪一个。  
3、如果层次结构中的任何类具有相同签名的方法，则默认方法变得不相关。比如：默认方法不能覆盖java.lang.Object中的方法。因为Object是所有java类的基类，所以即使我们把Object类的方法定义为接口中的默认方法，也是无用的，因为总是使用Object类的方法。  

###**接口静态方法**  
Java接口静态方法类似于默认方法，只是我们不能在实现类中重写它们。先看一个简单的例子：
```java
//接口
public interface MyData {
	default void print(String str) {
		if (!isNull(str)){
           System.out.println("MyData Print:" + str);
		}
	}
	static boolean isNull(String str) {
		System.out.println("Interface Null Check");
		return str == null ? true : "".equals(str) ? true : false;
	}
}
```
```java
//实现类
public class MyDataImpl implements MyData {
	public boolean isNull(String str) {
		System.out.println("Impl Null Check");
		return str == null ? true : false;
	}
	public static void main(String args[]){
		MyDataImpl obj = new MyDataImpl();
		obj.print("");
		obj.isNull("abc");
	}
}
```
这里需要注意的是，实现类MyDataImpl中的isNull方法只是一个普通的方法，它并不覆盖接口MyData中的isNull方法。如果我们将@Override注释添加到isNull方法，则会导致编译器错误。  
运行程序，打印结果：
```
Interface Null Check
Impl Null Check
```
如果把static改为default，运行程序，打印结果：
```
Impl Null Check
MyData Print:
Impl Null Check
```
###**接口静态方法总结**  
1、Java8接口的静态方法是接口的一部分，不允许实现类覆盖它们。  
2、Java接口的静态方法可以提供实用的方法，比如空检查，集合排序等。



