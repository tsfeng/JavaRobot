CharSequence和String是Java中两个不同的基本概念。  
本篇将介绍它们之间的差异与共性。  
# **CharSequence**   
CharSequence是一个表示字符序列的接口。 
这个接口不强制实现可变性；因此，可变类和不可变类都实现了这个接口。  
String、StringBuilder和StringBuffer都实现了这个接口。
当然，一个接口不能直接实例化，它需要一个实现来实例化一个变量：  
```
CharSequence charSequence = "hello";
```
在这里，charSequence被一个String实例化。 实例化其他实现：
```
CharSequence charSequence = new StringBuffer("hello");
CharSequence charSequence = new StringBuilder("hello");
```
CharSequence接口定义了四种方法：  
- char charAt(int)：返回指定位置的字符。  
- int length()：返回序列的长度。  
- subSequence(int start, int end)：返回由开始和结束参数指示的子字符串。  
- toString()：返回String序列的表示。  
如果使用CharSequence作为一个方法的参数类型，则对于这个方法，可以传递String、StringBuilder和StringBuffer作为参数。
# **String**   
Java中，String类代表字符串。
它是一个不可变的类，也是Java中最常用的类型之一。  
这个类实现了CharSequence，Serializable和Comparable<String>接口。  
**请记住，当你看到只带有引号的源代码时，编译器正在将它转换成一个String对象。** 
# **差异与共性**    
我们来比较一下CharSequence和String的差异和共性。  
它们都位于同一个名为java.lang的包中。   
但CharSequence是一个接口，String是一个具体的类。   
CharSequence与String都能用于定义字符串。  
但CharSequence的值是可读可写序列，而String的值是只读序列，即String类是不可变的。  
```java
public class CharSequenceDemo {
    public static void main(String[] args) {
        CharSequence obj = "hello";
        String str = "hello";
        System.out.println("Type of obj: " + obj.getClass().getSimpleName());
        System.out.println("Type of str: " + str.getClass().getSimpleName());
        System.out.println("Value of obj: " + obj);
        System.out.println("Value of str: " + str);
        System.out.println("Is obj a String? " + (obj instanceof String));
        System.out.println("Is obj a CharSequence? " + (obj instanceof CharSequence));
        System.out.println("Is str a String? " + (str instanceof String));
        System.out.println("Is str a CharSequence? " + (str instanceof CharSequence));
        System.out.println("Is \"hello\" a String? " + ("hello" instanceof String));
        System.out.println("Is \"hello\" a CharSequence? " + ("hello" instanceof CharSequence));
        System.out.println("str.equals(obj)? " + str.equals(obj));
        System.out.println("(str == obj)? " + (str == obj));
    }
}
```
