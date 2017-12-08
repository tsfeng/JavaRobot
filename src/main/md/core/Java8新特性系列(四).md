Java8于2014年3月18日发布，Java8作为Java的一个重大版本，新增了非常多的特性；但很多Java开发人员由于项目原因，在工作中没机会去使用Java8的新特性。目前Java9已于2017年9月21日正式发布，如果你对Java8的新特性还不是很了解，但愿《Java8新特性系列》文章对你有所帮助。
本篇将介绍Java8新特性之**方法引用(Method References)**。
###**什么是方法引用**
在Java中，我们可以通过创建新对象来使用对象的引用：
```java
List list = new ArrayList();
```
或者通过使用现有的对象：
```java
List list2 = list;
```
但对于一个方法的引用呢？
Java8之前，如果我们只是在另一个方法中使用一个对象的方法，我们仍然必须传递完整的对象作为参数。Java8的Lambda表达式让我们可以像使用对象或原始值一样使用方法。

“方法引用”是Java8中提供的一种新功能。这是一个与Lambda Expression有关的功能。它允许我们在不执行它们的情况下引用构造函数或方法。方法引用和Lambda是相似的，它们都需要一个由兼容的函数式接口组成的目标类型。
###**方法引用的类型**
```table
类型(<)     |    语法(<) 
1.引用静态方法        |      Class::staticMethodName         
2.引用一个构造函数         |    	ClassName::new     
3.引用特定类型任意对象的实例方法 |      Class::instanceMethodName     
4.引用特定对象的实例方法 |      object::instanceMethodName       
```