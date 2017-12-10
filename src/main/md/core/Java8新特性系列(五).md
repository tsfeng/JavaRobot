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