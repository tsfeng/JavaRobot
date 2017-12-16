### **写在前面**  
这个列表总结了Java开发人员经常犯的10个错误。  
#### 1、将数组转换为数组列表  
为了将数组转换为ArrayList，开发人员经常这样做：  
```
List<String> list = Arrays.asList(arr);
```
Arrays.asList()将返回一个ArrayList，它是数组内部的一个私有静态类，它不是java.util.ArrayList类。java.util.Arrays.ArrayList类有set()、get()、contains()方法，但是没有任何添加元素的方法，所以它的大小是固定的。要创建一个真正的ArrayList，您应该这样做：  
```
ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(arr));
```
ArrayList的构造方法可以接受一个集合类型，它也是java.util.Arrays.ArrayList的超类。  
#### 2、检查一个数组是否包含一个值  
开发人员常常这样做：  
```
Set<String> set = new HashSet<String>(Arrays.asList(arr));
return set.contains(targetValue);
```
这段代码可以工作，但是不需要先将一个列表转换为一个集合。将一个列表转换为一个集合需要额外的时间。它可以很简单：  
```
Arrays.asList(arr).contains(targetValue);
```
或者  
```
for(String s: arr){
	if(s.equals(targetValue))
		return true;
}
return false;
```
第一个比第二个更容易读懂。  
#### 3、从循环中的列表中删除一个元素  
考虑以下代码在迭代过程中删除元素：  
```
ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));
for (int i = 0; i < list.size(); i++) {
	list.remove(i);
}
System.out.println(list);
```
输出是：  
```
[b, d]
```
这个方法有一个严重的问题。当删除一个元素时，列表的大小会收缩，索引也会发生变化。因此，如果您想通过使用索引删除循环中的多个元素，那么这将无法正常工作。  
您可能知道使用迭代器是删除循环中的元素的正确方法，而且您知道，在Java中，每个循环都像迭代器一样工作，但实际上不是这样。考虑下面的代码：  
```
ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));
for (String s : list) {
	if (s.equals("a"))
		list.remove(s);
}
```
它将抛出ConcurrentModificationException。  
相反，以下是可以的：  
```
ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));
Iterator<String> iter = list.iterator();
while (iter.hasNext()) {
	String s = iter.next();
	if (s.equals("a")) {
		iter.remove();
	}
}
```
.next()必须在.remove()之前调用。在foreach循环中，编译器将使. next()在删除元素之后被调用，这导致ConcurrentModificationException。您可能想看一下ArrayList.iterator()的源代码。  
#### 4、Hashtable vs HashMap  
根据算法的惯例，Hashtable是数据结构的名字。但在Java中，数据结构的名字是HashMap。Hashtable和HashMap的一个关键区别是，Hashtable是同步的。所以，你通常不需要Hashtable，而是应该使用HashMap。  
#### 5、使用原生态类型集合  
在Java中，原生态类型和无界通配符类型很容易混合在一起。例如，Set是原生态类型，而Set<?>是无界通配符类型。  
考虑下面的代码，它使用原生态类型List作为参数：  
```
public static void add(List list, Object o){
	list.add(o);
}
public static void main(String[] args){
	List<String> list = new ArrayList<String>();
	add(list, 10);
	String s = list.get(0);
}
```
这段代码将抛出一个异常：  
```
Exception in thread "main" java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String at ...
```
使用原生态类型集合是危险的，因为原生态类型集合跳过了泛型类型检查而不安全。Set，Set<?>和Set<Object>之间有很大的不同。  
#### 6、访问级别  
开发人员经常使用public修饰类字段。通过直接引用来获得字段值很容易，但这是一个非常糟糕的设计。经验法则是为成员提供尽可能低的访问级别。  
#### 7、ArrayList vs LinkedList  
当开发人员不知道ArrayList和LinkedList之间的区别时，他们经常使用ArrayList，因为它看起来很熟悉。然而，它们之间存在着巨大的性能差异。简单地说，如果有大量的添加/删除操作，并且没有大量的随机访问操作，那么应该优先使用LinkedList。看看ArrayList和LinkedList，如果这对你来说是新的，你可以获得更多关于他们性能的信息。  
#### 8、可变 vs 不可变  
不可变对象有许多优点，比如简单性、安全性等等，但是它需要一个单独的对象来处理每个不同的值，而太多的对象可能会导致大量的垃圾收集。在可变和不可变之间选择时应该有一个平衡。  
一般来说，可变对象被用来避免产生太多的中间对象。一个经典的例子是连接大量的字符串。如果您使用的是不可变字符串，那么您将会生成大量的对象，这些对象可以立即获得垃圾收集。这浪费了CPU上的时间和精力，使用一个可变对象是正确的解决方案（例如StringBuilder）。  
```
String result="";
for(String s: arr){
	result = result + s;
}
```
在其他情况下，可变对象是可取的。例如，将可变对象传递到方法中可以让您收集多个结果，而不需要跳过太多的语法障碍。另一个例子是排序和过滤：当然，您可以创建一个获取原始集合的方法，并返回一个已排序的集合，但是对于更大的集合来说，这将变得非常浪费。  
#### 9、超类和子类的构造方法  
这个编译错误发生是因为默认的父类构造方法没有定义。在Java中，如果一个类没有定义构造方法，那么编译器将默认为这个类插入一个默认的无参数构造方法。如果在父类中定义了构造方法，在这个例子中是Super(String s)，编译器将不会插入默认的无参数构造方法。这是上面的父类的情况。  
子类的构造方法，不管是带参数的，还是无参数的，将调用无参数的父类构造方法。由于编译器试图将super()插入子类中的2个构造方法，但是没有定义父类的默认构造方法，编译器会报告错误信息。  
要解决这个问题，只需  
1）在父类中添加一个Super()构造方法，如下：  
```
public Super(){
    System.out.println("Super");
}
```
2）删除自定义的父类构造方法  
3）向子类构造方法添加super(value)。  
#### 10、""或者构造方法  
字符串可以通过两种方式创建：  
```
//1. use double quotes
String x = "abc";
//2. use constructor
String y = new String("abc");
```
区别是什么？  
下面的例子可以快速的提供一个答案：  
```
String a = "abcd";
String b = "abcd";
System.out.println(a == b);  // True
System.out.println(a.equals(b)); // True

String c = new String("abcd");
String d = new String("abcd");
System.out.println(c == d);  // False
System.out.println(c.equals(d)); // True
```




