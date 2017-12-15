###**什么是自动装箱和拆箱**
自动装箱就是Java自动将原始类型值转换成对应的对象，比如将int的变量转换成Integer对象，这个过程叫做装箱，反之将Integer对象转换成int类型值，这个过程叫做拆箱。因为这里的装箱和拆箱是自动进行的非人为转换，所以就称作为自动装箱和拆箱。
###**代码看问题**
```java
public class IntegerDemo {
    public static void main(String[] args) {
        //打印true
        System.out.println(i1 == i2);
        Integer i3 = 200;
        Integer i4 = 200;
        //打印false
        System.out.println(i3 == i4);
        //打印true
        System.out.println(i1 == 100);
        //打印true
        System.out.println(i3 == 200);
    }
}
```
执行后，打印结果如注释。
###**原因分析**
Java1.5之后，`Integer i1 = 100;` 将进行自动装箱；
`System.out.println(i1 == 100);`将进行自动拆箱。
查看字节码文件，你会发现在装箱的时候自动调用的是Integer的valueOf方法；在拆箱的时候自动调用的是Integer的intValue方法。
```java
...
INVOKESTATIC java/lang/Integer.valueOf (I)Ljava/lang/Integer;
...
INVOKEVIRTUAL java/lang/Integer.intValue ()I
...
```
Integer的valueOf源码：
```java
public static Integer valueOf(int i) {
    if (i >= IntegerCache.low && i <= IntegerCache.high)
        return IntegerCache.cache[i + (-IntegerCache.low)];
    return new Integer(i);
}
```
```java
public int intValue() {
    return value;
}
```
未做特定处理的情况下： low = -128; high = 127;
在Java中，“==”比较的是对象的内存地址（reference），自动装箱后，i1、i2、i3、i4都是Integer对象；Integer是不可变对象，因为Integer的value是final的`private final int value;` -128到127之间的数据放到了IntegerCache中，IntegerCache是static的，因此将会放到常量池中作为缓存使用。
而i1、i2在IntegerCache范围内，自动装箱后是同一个对象，内存地址相等，所以i1==i2打印true；
i3、i4大于127，自动装箱后是不同的对象，所以i3==i4打印false.。
i1==100，自动拆箱后比较的是值，所以打印true。同理i3==200也打印true。
###**其他类型**
```java
public class DoubleDemo {
    public static void main(String[] args) {
        Double i1 = 100.0;
        Double i2 = 100.0;
        Double i3 = 200.0;
        Double i4 = 200.0;
        //打印false
        System.out.println(i1 == i2);
        //打印false
        System.out.println(i3 == i4);
    }
}
```
```java
public class BooleanDemo {
    public static void main(String[] args) {
        Boolean i1 = false;
        Boolean i2 = false;
        Boolean i3 = true;
        Boolean i4 = true;
        //打印true
        System.out.println(i1 == i2);
        //打印true
        System.out.println(i3 == i4);
    }
}
```
###**容易忽略的问题**
```java
public class IntegerDemo {
    public static void main(String[] args) {
        Integer sum = 0;
        for (int i = 1000; i < 5000; i++) {
            sum += i;
        }
    }
}
```
上面的代码sum+=i可以看成sum = sum + i，但是+这个操作符不适用于Integer对象，首先sum进行自动拆箱操作，进行数值相加操作，最后发生自动装箱操作转换成Integer对象。其内部变化如下
```
int result = sum.intValue() + i;
Integer sum = new Integer(result);
```
由于我们这里声明的sum为Integer类型，在上面的循环中会创建将近4000个无用的Integer对象，在这样庞大的循环中，会降低程序的性能并且加重了垃圾回收的工作量。因此在我们编程时，需要注意到这一点，正确地声明变量类型，避免因为自动装箱引起的性能问题。
###**总结**
1、Java中8种基本数据类型对应的封装类型都有各自的valueOf方法和xxValue方法；
2、除了Integer对象的IntegerCache，针对所有整数类型的类都有类似的缓存机制：ByteCache 、ShortCache 、LongCache 、CharacterCache ；
3、Byte，Short，Long 有固定范围： -128 到 127。对于 Character, 范围是 0 到 127。除了 Integer 可以通过参数改变范围外，其它的都不行，最大值 127 可以通过 JVM 的启动参数 -XX:AutoBoxCacheMax=size 修改。
4、Integer、Short、Byte、Character、Long这几个类的valueOf方法的实现是类似的。Double、Float的valueOf方法的实现是类似的。原因是：在某个范围内的整型数值的个数是有限的，而浮点数却不是。
5、因为自动装箱会隐式地创建对象，如果在一个循环体中引入不必要的自动装箱操作，会创建无用的中间对象，这样会增加GC压力，拉低程序的性能。
本文参考资料：
http://droidyue.com/blog/2015/04/07/autoboxing-and-autounboxing-in-java/