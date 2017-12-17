# **继承结构**  
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/diagram_String.png)
# **String类定义**  
Java程序中的所有字符串文字，例如"abc"，都被实现为这个类的实例。   
实现了Serializable接口，可序列化；  
实现了Comparable接口，对应需要实现compareTo方法；  
实现了CharSequence接口。[CharSequence与String区别](https://github.com/tsfeng/JavaRobot/blob/master/blog/Interview/CharSequence%E4%B8%8EString%E7%9A%84%E5%8C%BA%E5%88%AB.md)
# **成员变量**  
value，存放当前字符串包含的字符，final修饰，创建后不能改变。  
```
private final char value[];
```
hash，存放String的哈希值。   
```
private int hash; // Default to 0
```
# **构造方法**  
String构造方法主要分以下几类：  
#### 1、无参构造器；  
初始化一个新创建的String对象，以便它表示一个空的字符序列。请注意，使用这个构造函数是不必要的，因为字符串是不可变的。  
```
public String() {
    this.value = "".value;
}
```
#### 2、String参数；  
初始化新创建的String对象，以便它表示与参数相同的字符序列; 换句话说，新创建的字符串是参数字符串的副本。除非需要明确的副本，否则original使用此构造函数是不必要的，因为字符串是不可变的。
```
public String(String original) {
    this.value = original.value;
    this.hash = original.hash;
}
```
#### 3、和byte[]相关的；  
```
public String(byte bytes[]);
public String(byte bytes[], int offset, int length);
```
```
public String(byte bytes[], Charset charset);
public String(byte bytes[], int offset, int length, Charset charset);
```
```
public String(byte bytes[], String charsetName);
public String(byte bytes[], int offset, int length, String charsetName);
```
#### 4、和char[]相关的；   
```
public String(char value[]);
public String(char value[], int offset, int count)；
```
####5、和int[]相关的；   
```
public String(int[] codePoints, int offset, int count)
```
#### 6、和StringBuilder和StringBuffer相关的。  
```
public String(StringBuffer buffer) {
    synchronized(buffer) {
        this.value = Arrays.copyOf(buffer.getValue(), buffer.length());
    }
}

public String(StringBuilder builder) {
    this.value = Arrays.copyOf(builder.getValue(), builder.length());
}
```
#### 7、默认访问权限构造方法。
String还提供了一个默认类型的构造方法String(char[] value, boolean share)，与String(char[] value)区别在于多了一个没用的参数，以便重载构造方法，而且实现时直接将参数的数组赋值给当前String对象的value属性，而不是复制数组，也就是说这个方法构造出来的String和参数传过来的char[]共享同一个数组，并不安全，这样的设计是出于性能和节约内存的考虑，因此这个方法是包私有的。
```
String(char[] value, boolean share) {
    // assert share : "unshared not supported";
    this.value = value;
}
```
# **其他方法** 
## **静态工厂方法**
```
String valueOf(Object obj);
```
调用Object的toString()方法。
```
String valueOf(char data[]);
String copyValueOf(char data[]);
```
调用String(char value[])构造器。
```
String valueOf(char data[], int offset, int count);
String copyValueOf(char data[], int offset, int count);
```
调用String(char value[], int offset, int count)构造器。
```
String valueOf(boolean b);
```
返回”true”或”false”。
```
String valueOf(char c);
```
调用String(char[] value, true)构造器。
```
String valueOf(int i);
String valueOf(long l);
String valueOf(float f);
String valueOf(double d);
```
调用参数对应包装类的toString()方法。 
## **intern()方法**
```
public native String intern();
```
该方法返回一个字符串对象的内部化引用。 众所周知：String类维护一个初始为空的字符串的对象池，当intern方法被调用时，如果对象池中已经包含这一个相等的字符串对象则返回对象池中的实例，否则添加字符串到对象池并返回该字符串的引用。
## **对“+”的重载**
String对“+”的支持其实就是使用了StringBuilder以及他的append()、toString()le两个方法。
## **常规方法**
```
boolean isEmpty();
```
判断length是否为0。
```
char charAt(int index);
```
判断越界，然后直接从value数组取值。
```
int codePointAt(int index);
int codePointBefore(int index);
int codePointCount(int beginIndex, int endIndex);
int offsetByCodePoints(int index, int codePointOffset);
```
判断越界然后调用Character对应静态方法。
```
byte[] getBytes(String charsetName);
byte[] getBytes(Charset charset);
byte[] getBytes();
```
调用StringCoding.encode()编码返回。
```
String substring(int beginIndex);
String substring(int beginIndex, int endIndex);
CharSequence subSequence(int beginIndex, int endIndex);
```
调用String的构造方法String(char value[], int offset, int count)，将会将原来的char[]中的值逐一复制到新的String中，两个数组并不是共享的，虽然这样做损失一些性能，但是有效地避免了内存泄露。
```
String concat(String str);
```
先将原来数据用Arrays.copyOf()复制到一个char数组中，然后调用getChars()将str的值复制到char数组后面，最后调用共享char[]的构造方法将char数组构造成新的String对象并返回。
```
boolean matches(String regex);
```
调用Pattern.matches()方法。
```
boolean contains(CharSequence s);
```
调用indexOf()进行判断，只要返回索引大于-1即包含。
```
String[] split(String regex, int limit);
String[] split(String regex);
```
后者调用前者；如果regex长度为1而且不包含".$|()[{^?*+\\"，或者regex长度为2而且以"\\"开头且第二个字符非数字字母（总而言之分割的正则其实只有一个字符），则创建一个List，遍历value，读取匹配到regex的时候，切取分隔符前面的子字符串，放入List中，最后一段也放入List，最后根据limit创建一个子List转换为String[]并返回；否则调用Pattern.compile(regex).split()进行计算并返回。
```
String join(CharSequence delimiter, CharSequence... elements);
String join(CharSequence delimiter, Iterable<? extends CharSequence> elements)
```
调用StringJoiner的add()和toString()方法进行拼接。
```
String toLowerCase(Locale locale);
String toLowerCase();
String toUpperCase(Locale locale);
String toUpperCase();
```
涉及到多语言的实现，实现起来比较复杂，没仔细看。
```
String trim()
```
分别从头和尾开始遍历找到首次不为空字符的位置，取子字符串返回。
```
char[] toCharArray()
```
创建一个同样长度的char数组，调用System.arraycopy()复制并返回，避免安全性问题。
```
String format(String format, Object... args);
String format(Locale l, String format, Object... args);
```
调用Formatter的format()方法进行计算并返回。

## **替代方法**
```
String replace(char oldChar, char newChar);
```
如果新旧字符一样则直接返回this好了，否则先遍历，找到第一次出现oldChar的下标，如果没找到也是返回this，找到则将该下标之前的值循环复制到新数组，此下标之后的值复制到新数组的时候先判断是否oldChar，是的话复制newChar到新数组；最后用新数组构造一个String并返回。
```
String replaceFirst(String regex, String replacement);
```
调用Pattern.matcher()找到匹配之后，再调用Matcher.replaceFirst()来替换首次出现。
```
String replaceAll(String regex, String replacement);
```
调用Pattern.matcher()找到匹配之后，再调用Matcher.replaceAll()来替换全部。
```
String replace(CharSequence target, CharSequence replacement);
```
同样调用Pattern.matcher().replaceAll()，支持单个字符。
## **比较方法**
```
boolean equals(Object anObject);
```
先判断是否this，再判断是否String对象，再判断长度是否相等，最后逐个char进行对比。
```
boolean contentEquals(StringBuffer sb);
```
调用contentEquals(CharSequence cs)，该方法判断如果是StringBuffer则加同步去执行nonSyncContentEquals(AbstractStringBuilder sb)，否则（StringBuilder的情况）不加同步直接执行。 而nonSyncContentEquals(AbstractStringBuilder sb)中具体的比较流程与equals基本一致。
```
boolean equalsIgnoreCase(String anotherString);
```
先后判断是否this、是否null、长度是否相同，然后调用boolean regionMatches()。
```
boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len);
```
比较this和other是否相等，先判断越界，再逐个字符比较，相同则继续，不同则根据ignoreCase参数，如果true则先将比较双方转成大写进行相等判断，还不相等则转成小写（针对格鲁吉亚语）进行判断。
```
boolean startsWith(String prefix, int toffset), boolean startsWith(String prefix)
```
后者调用前者，从指定偏移量开始，逐个字符进行判断是否相等，判断次数为prefix的长度。
```
boolean endsWith(String suffix);
```
调用startsWith(suffix, value.length - suffix.value.length)，判断this的后面N（suffix的长度）个字符是否与suffix相等。

## **哈希方法**
```
int hashCode();
```
hash属性初始化为0，如果调用hashCode()的时候发现hash为0则开始计算哈希值（懒加载）；由于String不可变，则hash计算一次即可。哈希算法核心为h = 31 * h + val[i];，遍历所有字符，循环地加上乘以31的哈希值作为新的哈希值，相当于val[0]*31^(n-1) + val[1]*31^(n-2) + ... + val[n-1]；而选用31，可能时出于i*31== (i<<5)-1的考虑。

## **查找方法**
```
int indexOf(int ch);
int indexOf(int ch, int fromIndex);
```
前者调用后者（fromIndex=0），先判断越界，然后有两种情况就是ch对应单字节和双字节，单字节则直接从fromIndex开始遍历对比查找，双字节则调用int indexOfSupplementary(int ch, int fromIndex)遍历查找的时候同时判断两个字节。
```
int lastIndexOf(int ch);
int lastIndexOf(int ch, int fromIndex);
int lastIndexOfSupplementary(int ch, int fromIndex);
```
与indexOf()系列类似，只是遍历查找的起点和方向不同。
```
int indexOf(String str);
int indexOf(String str, int fromIndex);
int indexOf(char[] source, int sourceOffset, int sourceCount, String target, int fromIndex);
int indexOf(char[] source, int sourceOffset, int sourceCount, char[] target, int targetOffset, int targetCount, int fromIndex);
```
前三者调用最后一个方法。处理完越界和特殊情况后，开始遍历，遍历过程中每次先找到this中出现target的第一个字符（减少判断），找到后开始从当前下标开始，this的值与target的值逐个比较，判断到不相等的值或者到target的结尾则退出判断，然后如果退出判断时的下标等于开始判断下标+target长度，那么就是找到了，返回开始判断的下标，否则继续外面的循环。并没有用KMP算法。
```
int lastIndexOf(String str);
int lastIndexOf(String str, int fromIndex);
int lastIndexOf(char[] source, int sourceOffset, int sourceCount, String target, int fromIndex);
int lastIndexOf(char[] source, int sourceOffset, int sourceCount, char[] target, int targetOffset, int targetCount, int fromIndex);
```
与indexOf()系列类似，只是查找的方向以及起始位置不一样了。
