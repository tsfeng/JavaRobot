在上一篇[Java8新特性系列（二）](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/Java8Feature/Java8%E6%96%B0%E7%89%B9%E6%80%A7%E7%B3%BB%E5%88%97(%E4%BA%8C).md)中，我们介绍了Java8新特性之函数式接口。    
本篇将介绍Java8新特性之**Lambda表达式**。  
### **函数式编程**  
介绍Lambda表达式之前，先了解一个概念:函数式编程。  
```
函数式编程（英语：functional programming）或称函数程序设计，又称泛函编程，是一种编程典范，它将电脑运算视为数学上的函数计算，并且避免使用程序状态以及易变对象。函数编程语言最重要的基础是λ演算（Lambda calculus）。而且λ演算的函数可以接受函数当作输入（引数）和输出（传出值）。
比起命令式编程，函数式编程更加强调程序执行的结果而非执行的过程，倡导利用若干简单的执行单元让计算结果不断渐进，逐层推导复杂的运算，而不是设计一个复杂的执行过程。【维基百科】
```
近年来随着技术的发展，函数式编程已经在实际生产中发挥巨大的作用了，越来越多的语言开始加入闭包，匿名函数等非常典型的函数式编程的特性，从某种程度上来讲，函数式编程正在逐步“同化”命令式编程，函数式编程俨然已成为技术的一个发展方向。  
在函数式编程语言中，函数是一等公民，它们可以独立存在，你可以将其赋值给一个变量，或将他们当做参数传给其他函数。函数式编程语言提供了一种强大的功能——闭包，相比于传统的编程方法有很多优势，闭包是一个可调用的对象，它记录了一些信息，这些信息来自于创建它的作用域。  
篇幅所限，就不过多的介绍函数式编程，想了解更多可以自行查找学习资料；不了解也没问题，不影响阅读下文。  
### **为什么引入Lambda表达式？**  
在Java中，对象是Java编程的基础，你没有办法定义一个独立存在的函数/方法，也没有办法将方法作为参数传递或返回该实例的方法体。它们是Class的一部分，我们需要使用类或对象来调用任何函数。  
在Java8之前，如果开发人员想编写函数式风格的代码，那么开发人员必须使用嵌套类型（通常是匿名内部类）来代替函数。  
例如:假设我们需要创建一个Runnable的实例。通常我们使用下面的匿名类来做。  
```
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
```
//示例2
Thread t2 = new Thread(() -> System.out.println("run2..."));
```
这就是Lambda表达式。当然，Lambda表达式绝不仅是为了让代码更简洁。**Lambda表达式为Java添加了缺失的函数式编程特点**，使我们能将函数当做一等公民看待。为我们进行可视化的函数式编程提供了一种途径。这正符合编程语言未来的发展趋势-未来的编程语言将逐渐融合各自的特性，取其精华，弃其糟粕。  
### **Lambda表达式语法格式**  
Lambda表达式的语法格式如下：  
```
(parameters) -> expression
或
(parameters) ->{ statements; }
```
以下是Lambda表达式的重要特征:  
```
1、一个 Lambda 表达式可以有零个、一个或多个参数；  
2、参数的类型可以显式声明，也可以从上下文中推断出来；  
3、空圆括号用于表示一组空参数；  
4、当只有一个参数，如果他的类型可推断，则圆括号可省略；  
5、Lambda表达式的主体可包含零条、一条或多条语句；  
6、如果Lambda表达式的主体只有一条语句，则大括号可省略，并且匿名函数的返回类型与正文表达式的返回类型相同；  
7、当主体中有多于一条语句时，必须用大括号（一个代码块）括起来，匿名函数的返回类型与代码块中返回值的类型相同，若没有返回则为void。  
```
让我们试着了解上面示例2的Lambda表达式中发生了什么。  
1、Runnable是一个函数式接口，这就是为什么我们可以使用Lambda表达式来创建它的实例。在Java中，Lambda表达式与函数式接口是不可分割的。Lambda表达式可以理解为**对于接口和其中的抽象方法的具体实现**，函数式接口中只有一个抽象方法，因此在将Lambda表达式应用于方法时不会出现混淆。  
2、由于run()方法没有参数，我们的Lambda表达式也没有参数。  
3、就像if-else块一样，我们可以避免大括号（{}），因为我们在方法体中有单个语句。对于多个语句，我们必须像使用其他方法一样使用大括号。  
### **Lambda表达式示例**  
1、打印给定数组的所有元素：  
```
//Old way:
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
for(Integer n: list) {
	System.out.println(n);
}
//New way:
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
list.forEach(n -> System.out.println(n));
```
2、使用Java内置Predicate函数接口  
```
public class LambdaDemo {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("打印所以数字:");
        evaluate(list, (n)->true);

        System.out.println("不打印数字:");
        evaluate(list, (n)->false);

        System.out.println("打印偶数:");
        evaluate(list, (n)-> n%2 == 0 );

        System.out.println("打印奇数:");
        evaluate(list, (n)-> n%2 == 1 );

        System.out.println("打印大于3的数字:");
        evaluate(list, (n)-> n > 3 );
    }

    public static void evaluate(List<Integer> list, Predicate<Integer> predicate) {
        for(Integer n: list)  {
            if(predicate.test(n)) {
                System.out.println(n + " ");
            }
        }
    }
}
```
### **Lambda表达式的好处**  
引入Lambda表达式，最直观的一个改进是，不用再写大量的匿名内部类。事实上，还有更多由于函数式编程本身特性带来的提升。比如：代码的可读性会更好、高阶函数引入了函数组合的概念。  
此外，因为Lambda的引入，集合操作也得到了极大的改善。比如，引入stream API，把map、reduce、filter这样的基本函数式编程的概念与Java集合结合起来。在大多数情况下，处理集合时，Java程序员可以告别for、while、if这些语句。  
随之而来的是，map、reduce、filter等操作都可以并行化，在一些条件下，可以提升性能。  




