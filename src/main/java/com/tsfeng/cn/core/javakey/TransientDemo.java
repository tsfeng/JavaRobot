package com.tsfeng.cn.core.javakey;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * https://stackoverflow.com/questions/910374/why-does-java-have-transient-fields
 * @author tsfeng
 * @version 创建时间 2017/11/27 17:55
 * 1、transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。
 * 2、被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化。
 * 3、一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。
 * 也可以认为在将持久化的对象反序列化后，被transient修饰的变量将按照普通类成员变量一样被初始化。
 *
 * transient 关键字只能修饰变量，不能修饰类、方法。
 * 被 transient 关键字修饰的变量不能被自动序列化（实现Serializable接口）。
 * 被 static 关键字修饰的变量，无论是否被 transient 关键字修饰，都不能被自动序列化。
 * 被 static 关键字修饰的变量，在反序列化时，看似被得到了之前序列化对象的值了，其实不然，是 JVM 方法区中的动态属性，非反序列化后的变量。
 * 一个类若实现了 Externalizable 接口，它的属性无论是被 static 还是 transient 修饰，只要该属性被应用到Externalizable接口的writeExternal、readExternal方法中，都能够实现序列化，不过这是手动序列化。
 */
public class TransientDemo {

    public static void main(String[] args) throws Exception {
        Person p = new Person();
        p.setName("张三");
        p.setGender("男");
        p.setInfo("架构师");
        p.setAge(31);
        ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream("to1.txt"));
        out1.writeObject(p);
        //释放资源
        out1.close();
        //为静态变量做验证，在反序列化之前，先修改p的gender和info属性的值
        p.setGender("女");
        p.setInfo("搬砖工");
        ObjectInputStream in1 = new ObjectInputStream(new FileInputStream("to1.txt"));
        Person p2 = (Person) in1.readObject();
        //释放资源
        in1.close();
        System.out.println(p2.getName());
        System.out.println(p2.getGender());
        System.out.println(p2.getInfo());
        System.out.println(p2.getAge());



        Book book = new Book();
        book.name = "Thinking in Java";
        book.content = "Java is a world-wide programming language";
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("to.txt"));
        out.writeObject(book);
        out.close();
        // 对静态化变量做验证，防止误认为反序列化成功了
        book.name = "Netty in Action";
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("to.txt"));
        Book book2 = (Book) in.readObject();
        in.close();
        System.out.println(book2.name);
        System.out.println(book2.content);
    }
}
