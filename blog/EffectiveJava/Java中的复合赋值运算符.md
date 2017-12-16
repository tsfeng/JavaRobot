### **赋值运算符**  
基本赋值运算符:"="  
复合赋值运算符:"+="、"-="、"*="、"/="、"%="、 "<<="、 ">>="等等；  
### **不同之处**  
先看一段代码:  
```java
public class OperatorDemo {
    public static void main(String[] args) {
       short x = 3;
        double y= 4.6;
        //编译正确
        x += y;
        //编译正确
        x = (short)(x + y);
        //编译错误
        x = x + y;
    }
}
```
代码中x += y;能够正确运行的情况下，结果是7。  
### **总结**  
Q1、x += y;编译正确， x = x + y;编译错误，why？  
A:复合赋值表达式E1 op= E2相当于E1 = (T)((E1) op (E2))，其中T是E1的类型。  
Q2、为什么x += y;结果是7，而不是8？  
A:因为它是cast，不是舍入，相当于是截取。  
更多见JLS:15.26.2. Compound Assignment Operators  