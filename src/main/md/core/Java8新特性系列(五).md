Java8于2014年3月18日发布，Java8作为Java的一个重大版本，新增了非常多的特性；但很多Java开发人员由于项目原因，在工作中没机会去使用Java8的新特性。目前Java9已于2017年9月21日正式发布，如果你对Java8的新特性还不是很了解，但愿《Java8新特性系列》文章对你有所帮助。
本篇将介绍Java8新特性之**流(Stream) **。
###**什么是Stream**
众所周知，集合操作非常麻烦，若要对集合进行筛选、投影，需要写大量的代码，而流是以声明的形式操作集合，它就像SQL语句，我们只需告诉流需要对集合进行什么操作，它就会自动进行操作，并将执行结果交给你，无需我们自己手写代码。

###**Java流示例**
例如：统计字符串列表中长度小于5的字符串的数量，常规写法如下
```java
//示例1
public class StreamDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("java");
        list.add("stream");
        int count = 0;
        for (String str : list) {
            if (str.length() < 5) {
                count++;
            }
        }
        System.out.println("长度小于5的字符串有：" + count);
    }
}
```
用Java8的流则可以改写成如下
```java
//示例2
public class StreamDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("java");
        list.add("stream");
        long count = list.stream().filter(str -> str.length() < 5).count();
        System.out.println("长度小于5的字符串有：" + count);
    }
}
```
###**两种写法的区别**
示例1中，通过遍历整个列表来查找长度小于5的字符串。这个代码没有并行性；
示例2中，stream()方法返回包含所有字符串的流，filter()方法返回长度小于5的字符串的流，count()方法将此流减少为结果。所有这些操作都是并行的，这意味着我们可以在流的帮助下并行化代码。**使用流的并行执行操作比不使用流的顺序执行要快**。
###**流如何工作**

###**中间操作(Intermediate operations)**
中间操作总是被懒惰地执行。换句话说，在达到最终操作之前，它们不会运行。

1、filter:filter操作返回满足作为参数传入操作的谓词的元素流。过滤器之前和之后的元素本身将具有相同的类型，但元素的数量可能会改变。
2、map:map操作在传入的函数作为参数处理后返回元素流。映射之前和之后的元素可能有不同的类型，但会有相同的元素总数。
3、distinct:distinct操作是过滤器操作的特例。Distinct返回一个元素流，使得每个元素在流中是唯一的，基于元素的equals方法。
下面的表格统计以下常用的中间操作
```table
Function(-)     |      保留数量(-)    |    保留类型(-)  |    保留订单(-) 
map        |      ✔       |    ✘       |          ✔
filter        |      ✘       |   ✔        |         ✔
distinct    |      ✘       |   ✔       |        ✔
sorted      |       ✔    |      ✔       |       ✘
peek        |       ✔    |      ✔       |       ✔
```
###**最终操作(Terminal operations)**
最终操作总是急切地执行。该操作将启动流中存在的所有先前惰性操作的执行。终端操作要么返回具体的类型，要么产生副作用。例如，调用Integer :: sum操作的reduce 操作将产生一个Optional，这是一个具体的类型。或者，forEach操作不返回一个具体的类型，但你可以添加一个副作用，如打印出每个元素。收集终端操作是一种特殊的减少类型，它从流中获取所有元素，并可以生成一个Set，Map或List。这是一个列表总结。

https://zeroturnaround.com/rebellabs/java-8-streams-cheat-sheet/
http://ifeve.com/stream/

