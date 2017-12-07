Java8于2014年3月18日发布，Java8作为Java的一个重大版本，新增了非常多的特性；但很多Java开发人员由于项目原因，在工作中没机会去使用Java8的新特性。目前Java9已于2017年9月21日正式发布，如果你对Java8的新特性还不是很了解，但愿《Java8新特性系列》文章对你有所帮助。
本篇将介绍Java8新特性之**Lambda表达式**。
###**函数式编程**
介绍Lambda表达式之前，先了解一个概念:函数式编程。
```
函数式编程（英语：functional programming）或称函数程序设计，又称泛函编程，是一种编程典范，它将电脑运算视为数学上的函数计算，并且避免使用程序状态以及易变对象。函数编程语言最重要的基础是λ演算（Lambda calculus）。而且λ演算的函数可以接受函数当作输入（引数）和输出（传出值）。
比起命令式编程，函数式编程更加强调程序执行的结果而非执行的过程，倡导利用若干简单的执行单元让计算结果不断渐进，逐层推导复杂的运算，而不是设计一个复杂的执行过程。
```
上面这段引用摘自“维基百科“。
近年来随着技术的发展，函数式编程已经在实际生产中发挥巨大的作用了，越来越多的语言开始加入闭包，匿名函数等非常典型的函数式编程的特性，从某种程度上来讲，函数式编程正在逐步“同化”命令式编程，函数式编程俨然已成为技术的一个发展方向。
篇幅所限，就不过多的介绍函数式编程，想了解更多可以自行查找学习资料；不了解也没问题，不影响阅读下文。
###**为什么引入Lambda表达式？**
如果我们研究过一些其他的编程语言，如JavaScript、Scala等；他们被称为函数式编程语言是因为我们可以编写函数并在需要时使用它们。您可以将一个函数分配给一个变量，并将它们作为参数传递给其他函数。
而在Java中，对象是Java编程的基础，你没有办法定义一个独立存在的函数/方法，也没有办法将方法作为参数传递或返回该实例的方法体。它们是Class的一部分，我们需要使用类或对象来调用任何函数。
在Java8之前，如果开发人员想编写函数式风格的代码，那么开发人员必须使用嵌套类型（通常是匿名内部类）来代替函数。
例如:假设我们需要创建一个Runnable的实例。通常我们使用下面的匿名类来做。
```java
//示例1
Thread t1 = new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("run1...");
    }
});
```
但上面的代码，实际有用的仅是run()方法中的代码。剩下的所有代码是因为Java语言的结构限制。
那么有没有一种更简洁的方式呢？Java8提供了一种简洁的实现，如下:
```java
//示例2
Thread t2 = new Thread(() -> System.out.println("run2..."));
```
这就是Lambda表达式。
###**Lambda表达式语法格式**
Lambda表达式的语法格式如下：
```
(parameters) -> expression
或
(parameters) ->{ statements; }
```
以下是Lambda表达式的重要特征:
```
可选类型声明：不需要声明参数类型，编译器可以从参数的值推断。
可选的参数圆括号：一个参数时圆括号可省略，但多个参数或无参数时需要定义圆括号。
可选的大括号：如果主体包含了一个语句，就不需要使用大括号。
可选的返回关键字：如果主体只有一个表达式返回值则编译器会自动返回值，指定表达式返回了一个数值需要使用大括号。
```
让我们试着了解上面示例2的Lambda表达式中发生了什么。
1、Runnable是一个函数式接口，这就是为什么我们可以使用Lambda表达式来创建它的实例。
2、由于run()方法没有参数，我们的Lambda表达式也没有参数。
3、就像if-else块一样，我们可以避免大括号（{}），因为我们在方法体中有单个语句。对于多个语句，我们必须像使用其他方法一样使用大括号。
###**Lambda表达式示例**
###**Lambda表达式与函数式接口**
在Java中，Lambda表达式与函数式接口是不可分割的。上面示例2的Lambda表达式和示例1的匿名类最终结果是一致的，lambda表达式我们可以理解**对于接口和其中的抽象方法的具体实现**，函数式接口中只有一个抽象方法，因此在将Lambda表达式应用于方法时不会出现混淆。
###**Lambda表达式的优点**
1、减少代码行
使用Lambda表达式的一个明显的好处是减少了代码量，我们已经看到，使用Lambda表达式而不是使用匿名类来创建函数式接口的实例是多么容易。Lambda 表达式免去了使用匿名方法的麻烦，并且给予Java简单但是强大的函数化的编程能力。
2、顺序和并行执行支持
使用Lambda表达式的另一个好处是我们可以从Stream API顺序和并行操作支持中受益。
3、将行为传递给方法

4、
，在Java面向对象的世界中，Lambda表达式为我们进行可视化的函数式编程提供了一种途径

http://www.runoob.com/java/java8-Lambda-expressions.html
http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html#section1
https://www.tutorialspoint.com/java8/java8_Lambda_expressions.htm
http://www.baeldung.com/java-8-Lambda-expressions-tips
http://blog.oneapm.com/apm-tech/226.html
http://viralpatel.net/blogs/Lambda-expressions-java-tutorial/
https://dzone.com/articles/why-we-need-Lambda-expressions