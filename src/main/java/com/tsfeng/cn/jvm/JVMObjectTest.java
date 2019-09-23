package com.tsfeng.cn.jvm;

import com.javamex.classmexer.MemoryUtil;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author admin
 * @title: JVMObjectTest
 * @projectName JavaRobot
 * @description: TODO
 * @date 2019/9/2314:43
 */

/**
 * ① 将下载的 classmexer.jar 加入当前项目的classpath中
 * ② 启动Main是添加启动项：-javaagent:${classmexer_path}/classmexer.jar
 * -javaagent:  XXX/classmexer.jar
 *
 * ③ JVM 参数：
 * -XX:+UseCompressedOops   (默认启用）
 * -XX:+CompactFields   (默认启用）
 * -XX:FieldsAllocationStyle=1      （默认为1）
 */
public class JVMObjectTest {

    private static Unsafe UNSAFE;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ● 对象头：mark word(8 bytes) + class pointer(4 bytes) = 12 bytes
     * 因为在JDK 8 中"UseCompressedOops"选项是默认启用的，因此class pointer只占用了4个字节。
     * 同时，从属性'a'在内存中的偏移量为12也能说明，对象头仅占用了12bytes（属性a的分配紧跟在对象头后）
     *
     * ● 实例数据：int (4 bytes)
     *
     * ● 对齐填充：0 bytes
     * 因为'对象头' + '对齐填充' 已经满足为8的倍数，因此无需填充
     *
     * 对象占用内存大小：对象头(12) + 实例数据(4) + 对齐填充(0) = 16
     */
    private static void testObj_1 () throws NoSuchFieldException {
        JVMObjectTest_1 obj = new JVMObjectTest_1();
        // memoryUsage : 16
        System.out.println("memoryUsage : " + MemoryUtil.memoryUsageOf(obj));

        // a field offset : 12
        System.out.println("a field offset : " + UNSAFE.objectFieldOffset(JVMObjectTest_1.class.getDeclaredField("a")));
    }


    /**
     * ● 对象头：mark word(8 bytes) + class pointer(4 bytes) = 12 bytes
     * 因为在JDK 8 中"UseCompressedOops"选项是默认启用的，因此class pointer只占用了4个字节。
     *
     * ● 实例数据：long (8 bytes) + long (8 bytes)
     *
     * ● 对齐填充：4 bytes
     *
     * 对象占用内存大小：对象头(12) + 实例数据(16) + 对齐填充(4) = 32
     * 这里请注意，padding的填充不是在最后面的，即，不是在实例数据分配完后填充了4个字节。
     * 而是在对象头分配完后填充了4个字节。这从属性'a'字段的偏移量为16，也能够说明填充的部分是对象头后的4个字节空间。
     *
     * 这是为什么了？
     * 是这样的，在64位系统中，CPU一次读操作可读取64bit（8 bytes）的数据。如果，你在对象头分配后就进行属性 long a字
     * 段的分配，也就是说从偏移量为12的地方分配8个字节，这将导致读取属性long a时需要执行两次读数据操作。因为第一次读取
     * 到的数据中前4字节是对象头的内存，后4字节是属性long a的高4位（Java 是大端模式），低4位的数据则需要通过第二次读取
     * 操作获得。
     */
    private static void testObj_2 () throws NoSuchFieldException {
        JVMObjectTest_2 obj = new JVMObjectTest_2();
        // memoryUsage : 32
        System.out.println("memoryUsage : " + MemoryUtil.memoryUsageOf(obj));

        // a field offset : 16
        System.out.println("a field offset : " + UNSAFE.objectFieldOffset(JVMObjectTest_2.class.getDeclaredField("a")));

        // b field offset : 24
        System.out.println("b field offset : " + UNSAFE.objectFieldOffset(JVMObjectTest_2.class.getDeclaredField("b")));
    }



    /**
     * ● 对象头：mark word(8 bytes) + class pointer(4 bytes) = 12 bytes
     * 因为在JDK 8 中"UseCompressedOops"选项是默认启用的，因此class pointer只占用了4个字节。
     *
     * ● 实例数据：long (8 bytes) + int (4 bytes)
     *
     * ● 对齐填充：0 bytes
     *
     * 对象占用内存大小：对象头(12) + 实例数据(12) + 对齐填充(0) = 24
     *
     * 在前面的理论中，我们说过基本变量类型在内存中的存放顺序是从大到小的（顺序：longs/doubles、ints、
     * shorts/chars、bytes/booleans）。所以，按理来说，属性int b应该被分配到了属性long a的后面。但是，从属性位置
     * 偏移量的结果来看，我们却发现属性int b被分配到了属性long a的前面，这是为什么了？
     * 是这样的，因为JVM启用了'CompactFields'选项，该选项运行分配的非静态（non-static）字段被插入到前面字段的空隙
     * 中，以提供内存的利用率。
     * 从前面的实例中，我们已经知道，对象头占用了12个字节，并且再次之后分配的long类型字段不会紧跟在对象头后面分配，而是
     * 在新一个8字节偏移量位置处开始分配，因此对象头和属性long a直接存在了4字节的空隙，而这个4字节空隙的大小符合（即，
     * 大小足以用于）属性int b的内存分配。所以，属性int b就被插入到了对象头与属性long a之间了。
     */
    private static void testObj_3 () throws NoSuchFieldException {
        JVMObjectTest_3 obj = new JVMObjectTest_3();

        // memoryUsage : 24
        System.out.println("memoryUsage : " + MemoryUtil.memoryUsageOf(obj));

        // a field offset : 16
        System.out.println("a field offset : " +  UNSAFE.objectFieldOffset(JVMObjectTest_3.class.getDeclaredField("a")));

        // b field offset : 12
        System.out.println("b field offset : " + UNSAFE.objectFieldOffset(JVMObjectTest_3.class.getDeclaredField("b")));
    }


    /**
     * ● 对象头：mark word(8 bytes) + class pointer(4 bytes) = 12 bytes
     * 因为在JDK 8 中"UseCompressedOops"选项是默认启用的，因此class pointer只占用了4个字节。
     *
     * ● 实例数据：long (8 bytes) + int (4 bytes) + oops (4 bytes)
     *
     * ● 对齐填充：4 bytes
     *
     * 对象占用内存大小：对象头(12) + 实例数据(16) + 对齐填充(4) = 32
     *
     * 从属性 int a、long b，以及对象引用 str 的偏移量可以发现，对象引用是在基本变量分配完后才进行的分配的。这是通过
     * JVM选项'FieldsAllocationStyle=1'决定的，FieldsAllocationStyle的值为1，说明：先放入基本变量类型（顺序：
     * longs/doubles、ints、shorts/chars、bytes/booleans），然后放入oops（普通对象引用指针）
     *
     */
    private static void testObj_4 () throws NoSuchFieldException {
        JVMObjectTest_4 obj = new JVMObjectTest_4();

        // memoryUsage : 32
        System.out.println("memoryUsage : " + MemoryUtil.memoryUsageOf(obj));

        // a field offset : 12
        System.out.println("a field offset : " +  UNSAFE.objectFieldOffset(JVMObjectTest_4.class.getDeclaredField("a")));

        // b field offset : 16
        System.out.println("b field offset : " + UNSAFE.objectFieldOffset(JVMObjectTest_4.class.getDeclaredField("b")));

        // str field offset : 24
        System.out.println("str field offset : " + UNSAFE.objectFieldOffset(JVMObjectTest_4.class.getDeclaredField("str")));
    }




    /**
     * memoryUsageOf方法仅计算了对象本身的大小，并未包含引用对象的内存大小（注意，memoryUsageOf方法计算的是引用指针
     * 的对象，而非引用对象占用的内存大小）。
     * deepMemoryUsageOf方法则会将引用对象占用的内存大小也计算进来。
     *
     * 注意，deepMemoryUsageOf(Object obj)默认只会包含non-public的引用对象的大
     * 小。如果你想将public引用对象的大小也计算在内，可通过deepMemoryUsageOf重载方法
     * deepMemoryUsageOf(Object obj, VisibilityFilter referenceFilter)，VisibilityFilter参数传入
     * 'VisibilityFilter.ALL'来实现。
     */
    private static void testObj_5 () throws NoSuchFieldException {
        JVMObjectTest_5 obj = new JVMObjectTest_5();

        // TheObjectMemory memoryUsage : 16
        System.out.println("TheObjectMemory memoryUsage : " + MemoryUtil.memoryUsageOf(obj));

        // TheInnerObject memoryUsage : 16
        JVMObjectTest_5.TheInnerObject innerObj = new JVMObjectTest_5.TheInnerObject();
        System.out.println("TheInnerObject memoryUsage : " + MemoryUtil.memoryUsageOf(innerObj));

        // TheObjectMemory deepMemoryUsageOf : 32
        System.out.println("TheObjectMemory deepMemoryUsageOf : " + MemoryUtil.deepMemoryUsageOf(obj));
    }


    /**
     * 数组对象自身占用的内存大小 = 对象头 + 数组长度 * 元素引用指针/基本数据类型大小 + 对齐填充
     *
     * ● 对象头：mark word(8 bytes) + class pointer(4 bytes) + length(4 bytes) = 16 bytes
     * 因为在JDK 8 中"UseCompressedOops"选项是默认启用的，因此class pointer只占用了4个字节。
     *
     * ● 实例数据：数组长度(1) * 对象引用指针(4 bytes) = 4 bytes
     *
     * ● 对齐填充：4 bytes
     *
     * 对象占用内存大小：对象头(16) + 实例数据(4) + 对齐填充(4) = 24
     *
     * deepMemoryUsageOf = array memoryUsage + array_length（数组长度） * item_deepMemoryUsage (元素占用
     * 的全部内存)
     *
     * 注意，这里的数组是一个对象数组，因此memoryUsage中计算的是对象引用指针的大小。如果是一个基本数据类型的数组，如，
     * int[]，则，memoryUsage计算的就是基本数据类型的大小了。也就是说，如果是基本数据类型的数组的话，memoryUsage
     * 的值是等于deepMemoryUsageOf的值的。
     *
     */
    private static void testObj_6 () throws NoSuchFieldException {
        JVMObjectTest_6 obj = new JVMObjectTest_6();

        JVMObjectTest_6[] objArray = new JVMObjectTest_6[1];
        objArray[0] = obj;

        // memoryUsage : 24
        System.out.println("objArray memoryUsage : " + MemoryUtil.memoryUsageOf(objArray));

        // deepMemoryUsageOf : 104
        System.out.println("objArray deepMemoryUsageOf : " + MemoryUtil.deepMemoryUsageOf(objArray));

        // obj memoryUsage : 24
        System.out.println("obj memoryUsage : " + MemoryUtil.memoryUsageOf(obj));
        // obj deepMemoryUsageOf : 80
        System.out.println("obj deepMemoryUsageOf : " + MemoryUtil.deepMemoryUsageOf(obj));

        // first item offset（数组第一个元素的内存地址偏移量） : 16
        System.out.println("first item offset : " + UNSAFE.arrayBaseOffset(objArray.getClass()));
    }


    public static void main(String[] args) throws NoSuchFieldException {
        testObj_6();
    }

}
