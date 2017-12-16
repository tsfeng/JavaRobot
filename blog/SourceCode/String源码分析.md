# **继承结构**  
![](https://github.com/tsfeng/JavaRobot/raw/master/blog/CommonFile/diagram_String.png)
# **String类定义**  
Java程序中的所有字符串文字，例如"abc"，都被实现为这个类的实例。   
实现了Serializable接口，可序列化；  
实现了Comparable接口，对应需要实现compareTo方法；  
实现了CharSequence接口，  
# **成员变量**  
value，存放当前字符串包含的字符，final修饰，创建后不能改变。  
```
private final char value[];
```

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
# **其他方法** 