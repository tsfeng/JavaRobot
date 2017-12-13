Java8于2014年3月18日发布，Java8作为Java的一个重大版本，新增了非常多的特性；但很多Java开发人员由于项目原因，在工作中没机会去使用Java8的新特性。目前Java9已于2017年9月21日正式发布，如果你对Java8的新特性还不是很了解，但愿《Java8新特性系列》文章对你有所帮助。
本篇将介绍Java8新特性之**流(Stream) **。
###**认识Stream**
首先，这里的Stream和I/O流不同，虽然Java8 Stream与Java I/O中的InputStream和OutputStream在名字上比较类似，但他们是完全不同的概念。
其次，它也不同于StAX（Streaming API for XML，缩写StAX）对XML解析的Stream，StAX是用于读写XML文档的应用程序接口。
再次，它也不是Amazon Kinesis对大数据实时处理的Stream。
Java8中的Stream是对集合（Collection）对象功能的增强，它专注于对集合对象进行各种非常便利、高效的聚合操作（aggregate operation），或者大批量数据操作 (bulk data operation)。Stream API借助于同样新出现的Lambda表达式，极大的**提高编程效率和程序可读性**。同时它提供串行和并行两种模式进行汇聚操作，并发模式能够充分利用多核处理器的优势，使用fork/join并行方式来拆分任务和加速处理过程。通常编写并行代码很难而且容易出错, 但使用Stream API无需编写一行多线程的代码，就可以**很方便地写出高性能的并发程序**。所以说，Java8中首次出现的java.util.stream是一个函数式语言+多核时代综合影响的产物。
###**Java流示例**
先来一个例子，看看Stream怎么应用到具体的代码中。
例如、统计列表中不为null的元素的数量，常规的写法如下：
```java
//示例1
public class StreamDemo {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, null, 3, 4, null, 6);
        int count = 0;
        for (Integer num : nums) {
            if (num != null) {
                count++;
            }
        }
        System.out.println(count);
    }
}
```
用Java8的流(Stream)则可以改写成如下：
```java
//示例2
public class StreamDemo {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, null, 3, 4, null, 6);
        long count = nums.stream().filter(num -> num != null).count();
        System.out.println(count);
    }
}
```
上面两种写法返回相同的结果，接下来看看下面这段关键代码都干了些什么？
```java
 long count = nums.stream().filter(num -> num != null).count();
```
看这段代码之前先了解一下Stream操作的分类。
###**Stream操作分类**
Stream的操作分为两种，分别为**中间操作**和**最终操作**。
中间操作的返回值还是一个Stream，因此可以通过链式调用将中间操作串联起来。中间操作又可以分为**无状态**的(Stateless)和**有状态**的(Stateful)。
无状态中间操作是指元素的处理不受前面元素的影响；而有状态的中间操作必须等到所有元素处理之后才知道最终结果。比如排序是有状态操作，在读取所有元素之前并不能确定排序结果；
最终操作只能返回void或者一个非stream的结果。最终操作又可以分为**短路操作**和**非短路操作**。
短路操作是指不用处理全部元素就可以返回结果，比如找到第一个满足条件的元素。之所以要进行如此精细的划分，是因为底层对每一种情况的处理方式不同。
```table
操作(-)    |   分类(<)   |   例子(<)  |  
中间操作 |   无状态    |  filter()、map()、flatMap()、 peek()等
中间操作 |   有状态    |  distinct()、sorted()、limit()、skip() 等
最终操作 |   非短路   |  forEach()、count()等
最终操作 |   短路      |  anyMatch()、findFirst()等
```
###**Stream的操作步骤**
结合示例2的分解代码和下面的图片，来分析Stream的操作步骤。
![](./_image/abc.jpg)
####1、创建Stream
在使用流之前，首先需要拥有一个数据源(如示例2中nums)，并获取该数据源的流对象。
```java
//示例3
public class StreamDemo {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, null, 3, 4, null, 6);
        //1、创建Stream
        Stream<Integer> stream = nums.stream();
    }
}
```
创建Stream的方式有多种，常见的总结如下:
- [x] 从 Collection 和数组
    - Collection.stream()
    - Collection.parallelStream()
    - Arrays.stream(T array)
- [x] 从Stream类的静态工厂方法
    - 如 Stream.of(Object[]) 等
- [x] 从 BufferedReader
    - java.io.BufferedReader.lines()
- [x] 其他方式
    - Random.ints()
    - BitSet.stream()
    - Pattern.splitAsStream(java.lang.CharSequence)
    - JarFile.stream()
####2、转换Stream：
转换Stream其实就是把一个Stream通过**一系列中间操作**转换生成一个新的Stream。每次转换原有Stream对象不改变。
例如:当执行下面代码(示例2的分解前2步)的时候，并不会在控制台打印filter部分相应的内容，源数据nums也没有变化，stream和integerStream也不是同一个对象。
```java
//示例4
public class StreamDemo {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, null, 3, 4, null, 6);
        //1、创建Stream
        Stream<Integer> stream = nums.stream();
        //2、转换Stream
        Stream<Integer> integerStream = nums.stream().filter(s -> {
            System.out.println("filter: " + s);
            return s != null;
        });
        System.out.println("中间操作之后：" + JSON.toJSONString(nums));
        System.out.println(stream.equals(integerStream));
    }
}
```
执行代码，打印结果:
```
中间操作之后：[1,null,3,4,null,6]
false
```
####3、聚合：
执行聚合操作后本次流结束，你将获得一个执行结果(void或者一个非stream的结果)。这些操作被称为最终操作。
```java
//示例5
public class StreamDemo {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, null, 3, 4, null, 6);
        //1、创建Stream
        Stream<Integer> stream = nums.stream();
        //2、转换Stream
        Stream<Integer> integerStream = nums.stream().filter(s -> {
            System.out.println("filter: " + s);
            return s != null;
        });
        System.out.println("中间操作之后：" + JSON.toJSONString(nums));
        System.out.println(stream.equals(integerStream));
        //3、最终操作(暂时把count改为forEach)
        integerStream.forEach(System.out::println);
        System.out.println("最终操作之后：" + JSON.toJSONString(nums));
    }
}
```
执行代码，打印结果:
```
中间操作之后：[1,null,3,4,null,6]
false
filter: 1
1
filter: null
filter: 3
3
filter: 4
4
filter: null
filter: 6
6
最终操作之后：[1,null,3,4,null,6]
```
这个奇怪的结果解释了**Stream中间操作总是惰性的**、**流不能改变数据源**。
再把示例2中统计数量的代码加上，如下
```java
//示例6
public class StreamDemo {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, null, 3, 4, null, 6);
        //1、创建Stream
        Stream<Integer> stream = nums.stream();
        //2、转换Stream
        Stream<Integer> integerStream = nums.stream().filter(s -> {
            System.out.println("filter: " + s);
            return s != null;
        });
        System.out.println("中间操作之后：" + JSON.toJSONString(nums));
        System.out.println(stream.equals(integerStream));
        //3、最终操作(暂时把count改为forEach)
        integerStream.forEach(System.out::println);
        System.out.println("最终操作之后：" + JSON.toJSONString(nums));
        //再次最终操作
        long count = integerStream.count();
        System.out.println(count);
    }
}
```
执行代码后，会有异常产生，如下
```
Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
```
这个异常解释了**一个流只能有一次最终操作**。
###**Stream特性总结**
####1、流不是数据结构，没有内部存储：
流不是存储元素的数据结构；而是通过计算操作的流水线，传递来自诸如数据结构，数组，生成器函数或I/O通道等来源(source)的元素。
####2、流不能改变数据源：
流上的操作会产生结果，但不会修改其来源。比如Stream的filter操作会产生一个不包含被过滤元素的新Stream，而不是从source删除那些元素。
####3、惰性执行：
许多流操作（例如过滤，映射或重复删除）可以被懒惰地实现，从而为优化提供机会。Stream中间操作总是惰性的。
####4、流可以是无限大的：
集合类持有的所有元素都是存储在内存中的，非常巨大的集合类会占用大量的内存；而Stream的元素却是在访问的时候才被计算出来，占用内存很少。短路最终操作可以对无限的Stream进行运算并很快完成。
####5、一个流只能有一次最终操作：
我们可以把流想象成一条流水线，流水线的源头是我们的数据源(一个集合)，数据源中的元素依次被输送到流水线上，我们可以在流水线上对元素进行各种操作。一旦元素走到了流水线的另一头，那么这些元素就被“消费掉了”，我们无法再对这个流进行操作。当然，我们可以从数据源那里再获得一个新的流重新遍历一遍。
####6、并发高效：
Stream并发模式能够充分利用多核处理器的优势，使用fork/join并行方式来拆分任务和加速处理过程。无需编写一行多线程的代码，就可以**很方便地写出高性能的并发程序**。
