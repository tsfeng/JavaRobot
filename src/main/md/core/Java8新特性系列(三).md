Java8于2014年3月18日发布，Java8作为Java的一个重大版本，新增了非常多的特性；但很多Java开发人员由于项目原因，在工作中没机会去使用Java8的新特性。目前Java9已于2017年9月21日正式发布，如果你对Java8的新特性还不是很了解，但愿《Java8新特性系列》文章对你有所帮助。
本篇将介绍Java8新特性之**Lambda表达式**。

###**为什么引入Lambda表达式？**
介绍Lambda表达式之前，最好先了解一下函数式编程，了解了函数式编程后再来理解Lambda表达式，就更容易了。
如果我们了解过一些其他的编程语言，如JavaScript；他们被称为函数式编程语言是因为我们可以编写函数并在需要时使用它们。您可以将一个函数分配给一个变量，并将它们作为参数传递给其他函数。
而在Java中，没有办法定义一个独立于Java的函数/方法，也没有办法将方法作为参数传递或返回该实例的方法体。它们是Class的一部分，我们需要使用类或对象来调用任何函数。
在Java8之前，如果开发人员想编写函数式风格的代码，那么开发人员必须使用嵌套类型（通常是匿名内部类）来代替函数。
例如:假设我们需要创建一个Runnable的实例。通常我们使用下面的匿名类来做。
```java
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("run1...");
    }
};
```
但上面的代码，实际有用的仅是run()方法中的代码。剩下的所有代码是因为Java程序的结构化方式。

介绍Lambda表达式之前
函数式编程的特点之一是存在强大的抽象，它隐藏了许多日常操作的细节（比如迭代）。
Lambda表达式是Java8中包含的一个新的重要功能。它提供了一种清晰简洁的方式来使用表达式来表示一个方法接口。Lambda表达式也改进了Collection库，使得它更容易遍历，过滤和提取数据Collection。另外，新的并发功能可以提高多核环境的性能。
###**为什么引入Lambda表达式**

http://www.runoob.com/java/java8-lambda-expressions.html
http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html#section1
https://www.tutorialspoint.com/java8/java8_lambda_expressions.htm
http://www.baeldung.com/java-8-lambda-expressions-tips
http://blog.oneapm.com/apm-tech/226.html
http://viralpatel.net/blogs/Lambda-expressions-java-tutorial/
https://dzone.com/articles/why-we-need-lambda-expressions