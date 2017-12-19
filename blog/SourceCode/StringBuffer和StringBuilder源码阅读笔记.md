# **继承结构**  
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/StringBuffer_Builder_UML.png)  
# **类定义** 
StringBuffer和StringBuilder类的继承结构是相同的，如下： 
- 都被final修饰，不能再派生子类；
- 实现了Serializable接口，可序列化；  
- 实现了CharSequence接口，和String一样；
- 继承了AbstractStringBuilder抽象类。 
 
重点是AbstractStringBuilder抽象类，查看StringBuffer和StringBuilder类的源码，发现StringBuffer和StringBuilder类的大部分操作都是调用父类AbstractStringBuilder的相关方法。   
不同的是，StringBuffer是线程安全的，StringBuilder是线程非安全的，体现在代码上就是StringBuffer中的方法绝大多数都比StringBuilder中的方法多了一个synchronized修饰符。
# **成员变量** 
比较StringBuffer和StringBuilder类的源码，除去serialVersionUID，StringBuilder没有定义自己的成员变量，而StringBuffer定义了一个私有的成员变量toStringCache，如下：
```
private transient char[] toStringCache;
```
源码注释说明，这个字段是在调用StringBuffer的toString方法时作为缓存使用的。  
看下StringBuffer中的toString方法是如何使用这个字段的：
```
@Override
public synchronized String toString() {
    if (toStringCache == null) {
        toStringCache = Arrays.copyOfRange(value, 0, count);
    }
    return new String(toStringCache, true);
}
```
可以看出，如果多次连续调用toString方法的时候**由于这个字段的缓存就可以少了Arrays.copyOfRange的操作。**  
toStringCache在StringBuffer对象被修改时，会首先将toStringCache设置为null。  
最后调用String默认访问权限的构造方法，**返回的String对象共享toStringCache的值，又减少了Arrays.copyOf的操作。**  
```
String(char[] value, boolean share) {
    // assert share : "unshared not supported";
    this.value = value;
}
```
# **构造方法** 
StringBuffer和StringBuilder的构造方法基本一样，都是调用父类的构造方法或append方法。  
下面以StringBuffer的构造方法作为参考：  
1、构造一个不带字符的StringBuffer，其初始容量为16个字符。
```
public StringBuffer() {
    super(16);
}
```
2、指定初始容量，构造一个不带字符的StringBuffer。
```
public StringBuffer(int capacity) {
    super(capacity);
}
```
3、构造一个StringBuffer，并将其内容初始化为指定的字符串内容。
```
public StringBuffer(String str) {
    super(str.length() + 16);
    append(str);
}
```
4、构造一个StringBuffer，它包含与指定的CharSequence相同的字符。
```
public StringBuffer(CharSequence seq) {
    this(seq.length() + 16);
    append(seq);
}
```  
情况3、4中的初始容量是字符串的长度再加16，这意味着如果入参字符串长度为1，那么底层的数组长度为17。  
至于为什么是16，没找到相关资料，也许是当初设计Java语言的老人家们喜欢16吧。
# **AbstractStringBuilder类—成员变量**  
value，存放当前字符串包含的字符；与String不同，没有final修饰，因此是可变的。
```
/**
 * The value is used for character storage.
 */
char[] value;
```
count，字符串包含的字符的个数，也即字符串的长度；  
StringBuffer和StringBuilder的length()方法返回这个值。
```
/**
 * The count is the number of characters used.
 */
int count;
```
# **AbstractStringBuilder类—构造方法**  
1、默认类型无参构造方法；源码注释说明，这个无参的构造方法对子类的序列化是必要的。
```
AbstractStringBuilder() {
}
```
2、指定初始容量的构造方法；StringBuffer和StringBuilder的构造方法都间接调用此构造方法。
```
AbstractStringBuilder(int capacity) {
    value = new char[capacity];
}
```
# **AbstractStringBuilder类—append方法** 
对于append方法，如果append的参数是一个空指针的话则会添加'n','u','l','l'四个字符，如下：
```
public AbstractStringBuilder append(String str) {
    if (str == null)
        return appendNull();
    int len = str.length();
    ensureCapacityInternal(count + len);
    str.getChars(0, len, value, count);
    count += len;
    return this;
}
```
```
private AbstractStringBuilder appendNull() {
    int c = count;
    ensureCapacityInternal(c + 4);
    final char[] value = this.value;
    value[c++] = 'n';
    value[c++] = 'u';
    value[c++] = 'l';
    value[c++] = 'l';
    count = c;
    return this;
}
```
# **AbstractStringBuilder类—扩容方法** 
上面的append方法在对底层数组操作之前，都要调用ensureCapacityInternal()方法来判断数组长度是否足够：
```
private void ensureCapacityInternal(int minimumCapacity) {
    // overflow-conscious code
    if (minimumCapacity - value.length > 0) {
        value = Arrays.copyOf(value,
                newCapacity(minimumCapacity));
    }
}
```
如果数组长度不足，则调用newCapacity()方法进行扩容：
```
private int newCapacity(int minCapacity) {
    // overflow-conscious code
    int newCapacity = (value.length << 1) + 2;
    if (newCapacity - minCapacity < 0) {
        newCapacity = minCapacity;
    }
    return (newCapacity <= 0 || MAX_ARRAY_SIZE - newCapacity < 0)
        ? hugeCapacity(minCapacity)
        : newCapacity;
}
```
看上面源代码，扩容过程为：  
1、将容量扩充为原容量的2倍+2；  
2、如果扩充后容量依旧不足，则直接扩充到需要的容量大小；  
但这个扩容不能超过最大容量限制，也不能溢出后为负数。
```
private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
```
至于为什么是这个值，源码注释中说明，一些JVM在数组中存储header信息，试图分配较大的数组可能导致VM OutOfMemoryError。  
在Stack Overflow上关于这个问题的讨论：[Why the maximum array size of ArrayList is Integer.MAX_VALUE - 8?](https://stackoverflow.com/questions/35756277/why-the-maximum-array-size-of-arraylist-is-integer-max-value-8)







