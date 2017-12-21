### **认识final**​
在Java中，final关键字可以用来修饰类、方法和变量（包括成员变量和局部变量）。  
在JLS, Java SE 8 Edition, Section 4.12.4，final Variables，描述到：  
> 一个变量可以被声明为final，一个final变量只能被赋值一次，如果一个final变量被赋值会导致编译时错误，除非这个final变量在被赋值之前确定是未赋值的。

在JLS, Java SE 8 Edition, Section 8.4.3.3，final Methods，描述到：  
> 一个方法可以被声明为final以防止子类覆盖或隐藏它。试图覆盖或隐藏一个final方法会导致编译时错误。

在JLS, Java SE 8 Edition, Section 8.1.1.2，final Classes，描述到：  
> 一个类可以被声明为final如果它的定义是完整的并且不需要任何子类。如果某个final类的名称出现在另一个类声明的 extends子句中，则会导致编译时错误；这意味着一个final类不能有任何子类。如果一个类同时被声明为final和abstract，则会导致编译时错误；因为这个类的实现不可能完成。
### **怎么理解**​  
先看一段代码，类FinalDemo定义了一个构造方法和一个“setList”方法；  
```java
public class FinalDemo {
    //声明final变量
    private final List<String> finalList;
    public FinalDemo() {
        finalList = new ArrayList<>();
        finalList.add("hello");
    }
    public void setList(List list) {
        //编译时错误：Cannot assign a value to final variable 'foo'
        //this.finalList = list;
    }
    public static void main(String[] args) {
        FinalDemo f = new FinalDemo();
        f.finalList.add("world");
        //编译时错误：Cannot assign a value to final variable 'foo'
        //f.finalList = new ArrayList<>();
        System.out.println(JSON.toJSONString(f.finalList));
       
        String a = "hello world";
        final String b = "hello";
        String c = b + " world";
        System.out.println("a == c：" + (a == c));

        final String b2 = getHello();
        String c2 = b2 + " world";
        System.out.println("a == c2：" + (a == c2));

        String b3 = "hello";
        String c3 = b3 + " world";
        System.out.println("a == c3：" + (a == c3));
    }
    public static String getHello() {
        return "hello";
    }
}
```
执行代码后，打印结果：  
```
["hello","world"]
a == c：true
a == c2：false
a == c3：false
```
1、在上面这段代码中，finalList是一个实例变量，当我们创建FinalDemo类的对象时，实例变量finalList会被复制到FinalDemo类的对象中。如果我们在构造方法内对finalList赋值，那么编译器就知道构造方法只会被调用一次，所以在构造方法内对final变量finalList赋值没有问题。  
如果我们在一个方法内部对finalList赋值，编译器知道一个方法可以被多次调用，这意味着该值有可能被多次改变，这是final变量所不允许的。所以通过setList方法对final变量赋值会导致编译时错误。  
2、从上面代码执行结果知道，final变量finalList的内容是可以改变的，这是我们要说的另一个问题。  
调用存储在final变量中的对象的方法与final的语义无关，换句话说：**final只是关于引用本身，而不是关于引用对象的内容。**  
**值类型**：int、double等基本数据类型，它将确保值不能改变；  
**引用类型**：对于引用对象，final确保引用永远不会改变，这意味着它将始终引用同一个对象。它不保证被引用的对象内部的值保持不变。  
因此，final确保变量finalList始终引用相同的列表对象，但列表对象的内容可能发生变化。  
3、修改代码如下：  
```
private static final List<String> finalList = new ArrayList<>();
```
现在finalList是一个静态变量，当我们创建FinalDemo类的对象时，变量finalList不会被复制到FinalDemo类的对象中，因为它是静态的。现在finalList不是不是每个对象的对立属性，他是FinalDemo类的一个属性，可以被多个对象看到；那么在创建多个对象的时候，如果每个对象都是使用new关键字创建的，这意味着最终调用构造方法时该值有可能被多次改变。这是会导致编译时错误的。  
4、上面代码a==c打印true，其他打印false；  
final修饰的变量，如果在编译期间能知道它的确切值，则编译器会把它当做编译期常量使用。也就是说在用到该final变量的地方，相当于直接访问的这个常量，不需要在运行时确定。但只有在编译期间能确切知道final变量值的情况下，编译器才会进行这样的优化，这也是a==c2打印false的原因。  
### **final总结**​  
1、final关键字可以用来修饰类、方法和变量（包括成员变量和局部变量）；  
2、final成员变量必须在声明的时候初始化或者在构造器中初始化，否则就会报编译错误；(局部变量只需要保证在使用之前被初始化赋值即可)  
3、final变量不能再次赋值；  
4、final方法不能被重写；  
5、final类不能被继承；  
6、如果一个类同时被声明为final和abstract，则会导致编译时错误；  
7、final修饰引用类型变量，确保的是引用不会变化，不保证被引用的对象内部的值保持不变。  







