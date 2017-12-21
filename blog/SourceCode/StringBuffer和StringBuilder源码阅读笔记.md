# **继承结构**  
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/StringBuffer_Builder_UML.png)  
# **类定义** 
```
public final class StringBuffer extends AbstractStringBuilder
    implements java.io.Serializable, CharSequence { 
    //略      
}
```
```
public final class StringBuilder extends AbstractStringBuilder
    implements java.io.Serializable, CharSequence {
    //略    
}
```
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
源码注释说明，这个字段是在调用StringBuffer的toString()方法时作为缓存使用的。  
看下StringBuffer中的**toString()方法**是如何使用这个字段的：
```
@Override
public synchronized String toString() {
    if (toStringCache == null) {
        toStringCache = Arrays.copyOfRange(value, 0, count);
    }
    return new String(toStringCache, true);
}
```
可以看出，如果多次连续调用toString方法的时候**由于这个字段的缓存就可以少了Arrays.copyOfRange()方法的操作。**  
最后调用String默认访问权限的构造方法，**返回的String对象共享toStringCache的值，又减少了Arrays.copyOf()方法的操作。**  
```
//String默认访问权限的构造方法
String(char[] value, boolean share) {
    // assert share : "unshared not supported";
    this.value = value;
}
```
toStringCache在StringBuffer对象被修改时，会首先将toStringCache设置为null。  
# **构造方法** 
StringBuffer和StringBuilder的构造方法基本一样，都是调用父类的构造方法或append()方法。  
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

@Override
public synchronized StringBuffer append(String str) {
    toStringCache = null;
    super.append(str);
    return this;
}
```
4、构造一个StringBuffer，它包含与指定的CharSequence相同的字符。
```
public StringBuffer(CharSequence seq) {
    this(seq.length() + 16);
    append(seq);
}

@Override
public synchronized StringBuffer append(CharSequence s) {
    toStringCache = null;
    super.append(s);
    return this;
}
```  
情况3、4中的初始容量是字符串的长度再加16，这意味着如果入参字符串长度为1，那么底层的数组容量为17。  
至于为什么是16，而不是其他数字，没找到相关资料，也许是当初设计Java语言的老人家们喜欢16吧。
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
StringBuffer和StringBuilder的append()方法，基本一样，都是调用父类的append()方法。  
对于append()方法，如果append()的参数是一个空指针的话则会添加'n'，'u'，'l'，'l'四个字符，如下：
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
类似appendNull()方法的还有：
```
public AbstractStringBuilder append(boolean b) {
    //略
}
```
# **AbstractStringBuilder类—扩容方法** 
上面的append()方法在对底层数组操作之前，都要调用ensureCapacityInternal()方法来判断数组容量是否足够：
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
# **AbstractStringBuilder类—setLength方法**
StringBuffer和StringBuilder可以调用setLength()方法设置容量，不同的是StringBuffer覆盖了父类的setLength()方法，而StringBuilder是直接调用父类的setLength()方法。   
如果当前字符数量小于 newLength，则用'\0'填充。'\0'是一个转义字符，是一个“空字符”常量，它表示一个字符串的结束。
```
//StringBuffer中的setLength()方法
@Override
public synchronized void setLength(int newLength) {
    toStringCache = null;
    super.setLength(newLength);
}
```
```
//AbstractStringBuilder中的setLength()方法
public void setLength(int newLength) {
    if (newLength < 0)
        throw new StringIndexOutOfBoundsException(newLength);
    ensureCapacityInternal(newLength);
    if (count < newLength) {
        Arrays.fill(value, count, newLength, '\0');
    }
    count = newLength;
}
```
# **AbstractStringBuilder类—反转方法**
反转字符串也是算法中经常出现的问题，常规的思想就是利用遍历，首尾交换字符实现字符串的反转。
源代码中的基本思路是一致的，同样采用遍历一半字符串，然后将每个字符与其对应的字符进行交换。  
但是有不同之处，就是要判断每个字符是否在Character.MIN_SURROGATE(\ud800)和Character.MAX_SURROGATE(\udfff)之间。如果发现整个字符串中含有这种情况，则再次从头至尾遍历一次，同时判断value[i]是否满足Character.isLowSurrogate()，如果满足的情况下，继续判断value[i+1]是否满足Character.isHighSurrogate()，如果也满足这种情况，则将第i位和第i+1位的字符互换。  
在Stack Overflow上关于这个问题的讨论：[What is a “surrogate pair” in Java?](https://stackoverflow.com/questions/5903008/what-is-a-surrogate-pair-in-java)
```
public AbstractStringBuilder reverse() {
    boolean hasSurrogates = false;
    int n = count - 1;
    for (int j = (n-1) >> 1; j >= 0; j--) {
        int k = n - j;
        char cj = value[j];
        char ck = value[k];
        value[j] = ck;
        value[k] = cj;
        if (Character.isSurrogate(cj) ||
            Character.isSurrogate(ck)) {
            hasSurrogates = true;
        }
    }
    if (hasSurrogates) {
        reverseAllValidSurrogatePairs();
    }
    return this;
}
```
```
/** Outlined helper method for reverse() */
private void reverseAllValidSurrogatePairs() {
    for (int i = 0; i < count - 1; i++) {
        char c2 = value[i];
        if (Character.isLowSurrogate(c2)) {
            char c1 = value[i + 1];
            if (Character.isHighSurrogate(c1)) {
                value[i++] = c1;
                value[i] = c2;
            }
        }
    }
}
```
# **AbstractStringBuilder类—插入方法**
查看StringBuffer源码，insert方法分为以下2类：
- 不带synchronized关键字
```
public StringBuffer insert(int offset, boolean b) {}
public StringBuffer insert(int offset, int i) {}
public StringBuffer insert(int offset, long l) {}
public StringBuffer insert(int offset, float f) {}
public StringBuffer insert(int offset, double d) {}
public StringBuffer insert(int dstOffset, CharSequence s){}
```
- 带有synchronized关键字：
```
public synchronized StringBuffer insert(int offset, String str) {}
public synchronized StringBuffer insert(int offset, char c) {}
public synchronized StringBuffer insert(int offset, char[] str) {}
public synchronized StringBuffer insert(int offset, Object obj) {}
public synchronized StringBuffer insert(int dstOffset, CharSequence s, int start, int end) {}
public synchronized StringBuffer insert(int index, char[] str, int offset,int len) {}
```
插入操作基本上是直接或间接的分为以下几个步骤：
- 判断相应索引是否越界
- 插入的字符串为空则替换为"null"
- 检查数组容量，不足则扩容
- System.arraycopy()方法复制数组
# **AbstractStringBuilder类—trimToSize方法**
尝试减少用于字符序列的存储空间。如果缓冲区比保留当前字符序列所需的缓冲区大，则可以调整缓冲区以变得更加节省空间。调用此方法可能会影响后续调用capacity()方法返回的值，但不是必需的。
```
public void trimToSize() {
    if (count < value.length) {
        value = Arrays.copyOf(value, count);
    }
}
```
# **其他方法**
- 常规方法
    - **int length()方法，** 获取字符串长度；
    - **int capacity()方法，** 获取数组value的容量；
    - **char charAt(int index)方法，** 查找指定索引处的值；
    - **void setCharAt(int index, char ch)，** 设置指定索引处的值；
    - **void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin)，** 从当前StringBuffer对象的索引号srcBegin开始，到srcEnd结束的子串，赋值到字符数组dst中，并且从dst的索引号dstBegin开始；
- 截取方法
    - **CharSequence subSequence(int start, int end)，** 返回一个新的字符序列，该字符序列是此序列的子序列
    - **String substring(int start) ，** 指定起始索引截取字符串；
    - **String substring(int start, int end) ，** 指定起始和终止索引截取字符串；
- 删除方法
    - **AbstractStringBuilder deleteCharAt(int index)，** 删除指定索引处的字符；
    - **AbstractStringBuilder delete(int start, int end)，** 指定起始和终止索引删除字符串；
- 查找方法（最终调用String类的查找方法）
    - **int indexOf(String str) ，** 
    - **int indexOf(String str, int fromIndex)，** 
    - **int lastIndexOf(String str) ，** 
    - **int lastIndexOf(String str, int fromIndex)，**
- 替换方法
    - **AbstractStringBuilder replace(int start, int end, String str)，** 
- codePoint相关方法（Unicode字符相关）
    - **int codePointAt(int index)，**
    - **int codePointBefore(int index)，**
    - **int codePointCount(int beginIndex, int endIndex)，**
    - **int offsetByCodePoints(int index, int codePointOffset)，**
    - **AbstractStringBuilder appendCodePoint(int codePoint)，**


