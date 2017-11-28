###**认识transient**​
Java中的关键字transient用来表示一个字段不应该被序列化。
在Java Language Specification, Java SE 7 Edition, Section 8.3.1.3，描述到:

    Variables may be marked transient to indicate that they are not part of the persistent state of an object.
翻译过来也就是:变量可能被标记transient为表示它们不是对象持久状态的一部分。
###**怎么理解**​
在理解transient关键字之前，必须理解序列化的概念。
###**什么是序列化**​
序列化是使对象的状态持久化的过程。这意味着对象的状态被转换成一个字节流并存储在一个文件中。以同样的方式，我们可以使用反序列化从字节中取回对象的状态。这是Java编程中的重要概念之一，因为序列化主要用于网络编程。需要通过网络传输的对象必须转换为字节。为此，每个类或接口都必须实现Serializable接口。这是一个没有任何方法的标记接口。
###**transient的作用**​
默认情况下，所有对象的变量都被转换为持久状态。在某些情况下，您可能希望避免持久化一些变量，因为您不需要保留这些变量。此时你可以声明这些变量为transient。如果变量声明为transient，那么它将不会被持久化。这是transient关键字的主要目的。
看代码:
```java
public class Student implements Serializable {
    private Long id;
    private transient String name;
    private transient static int age;
    private static String sex;
   //省略get、set方法
}
```
```java
public class TransientDemo {
    public static void main(String[] args) throws Exception {
        Student stu = new Student();
        stu.setId(1L);
        stu.setName("张三");
        stu.setAge(18);
        stu.setSex("男");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("a.txt"));
        out.writeObject(stu);
        out.close();
        //在反序列化之前，修改stu的static变量的属性值
        stu.setAge(20);
        stu.setSex("女");
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("a.txt"));
        Student stu2 = (Student) in.readObject();
        //释放资源
        in.close();
        System.out.println(stu2.getId());
        System.out.println(stu2.getName());
        System.out.println(stu2.getAge());
        System.out.println(stu2.getSex());
    }
}
```
执行代码后，打印结果:
```
1
null
20
女
```
###**transient总结**​
基于以上代码分析，总结如下:
1、transient关键字只能修饰变量，不能修饰类、方法。但本地变量(局部变量)是不能被transient关键字修饰的。
2、transient关键字只能伴随Serializable使用，虽然Externalizable对象中使用transient关键字也不报错，但不起任何作用。 
3、transient关键字修饰的变量不能被自动序列化（实现Serializable接口）。
4、static关键字修饰的变量，无论是否被transient关键字修饰，都不能被自动序列化。因为序列化用于序列化实例，而不是类。static字段是类定义的一部分。
5、static关键字修饰的变量，在反序列化时，看似得到了之前序列化对象的值，但其不是自动序列化的结果。在反序列化过程中，static变量是从类级初始化开始的，static变量在类被加载后立即存在，因为它们是类定义的一部分。反序列化后，static变量的值取决于在对象被反序列化的VM中已经设置的值，如果在对象反序列化时第一次初始化类，则静态字段将处于初始状态。例如，上面代码序列化到文件后，再一次仅执行反序列化代码，打印结果:
```
1
null
0
null
```

