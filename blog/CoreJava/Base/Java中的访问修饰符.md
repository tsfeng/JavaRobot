# **Java中的访问控制修饰符**
Java面向对象的基本思想之一是封装细节并且公开接口。    
Java语言采用访问控制修饰符来控制类、类的方法和变量的访问权限，从而向使用者暴露接口，但隐藏实现细节。    
访问控制分为四种级别：public、protected、default、private；类不能用private和protected来修饰。    
### **1、public：**      
用public修饰的类、类属变量及方法，包内及包外的任何类（包括子类和普通类）均可以访问；  
### **2、protected：**      
用protected修饰的类属变量及方法，包内的任何类及包外那些继承了该类的子类才能访问（此处稍后解释），**protected重点突出继承**；  
protected修饰的成员变量和方法也称为受保护的成员变量和方法；   
受保护的成员变量和方法可以在本类或同一个包中的其它类（包括子类）中通过类的实例进行访问；  
也可以被同一个包中的类或不同包中的类继承，但是不能在不同包中的其它类（包括子类）中通过类的实例进行访问。  
### **3、default：**   
如果一个类、类属变量及方法没有用任何修饰符（即没有用public、protected及private中任何一种修饰），则其访问权限为default（默认访问权限）。    
默认访问权限的类、类属变量及方法，包内的任何类（包括继承了此类的子类）都可以访问它，而对于包外的任何类都不能访问它（包括包外继承了此类的子类）。**default重点突出包。**  
### **4、private：**   
用private修饰的类属变量及方法，只有本类可以访问，而包内包外的任何类均不能访问它。

访问级别|  修饰符    |   同类  |   同包  |  子类  |  不同包    
:----:   |  :----:   | :----: | :----: | :----: | :----:   
公开    |  public    |   √    |    √   |   √   |   √    
受保护  |  protected |   √    |    √   |   √   |   --    
默认    |  无        |   √    |    √   |   --   |   --    
私有    |  private   |   √    |    --   |   --   |   --    

# **结合示例深入理解protected**  
我们已经清楚Object.clone()是protected方法；  
看下面示例1代码，  产生编译错误：
```java
// 示例1
public class ProtectedDemo {
    public static void main(String[] args) throws Throwable {
        // MyObject是Object的子类
        MyObject otherSubClass = new MyObject();
        //编译错误
        otherSubClass.clone();

        //ProtectedDemo也是Object的子类
        ProtectedDemo thisSubClass = new ProtectedDemo();
        //编译正确
        thisSubClass.clone();
        
        //基类
        Object superClass = new Object();
        //编译错误
        superClass.clone();
    }
}
class MyObject {
}  
```   
修改代码， 如下示例2，    
在MyObject中覆盖（Override）基类的clone()方法，编译正确：
```java
// 示例2
public class ProtectedDemo {
    public static void main(String[] args) throws Throwable {
        // MyObject是Object的子类
        MyObject otherSubClass = new MyObject();
        //编译正确
        otherSubClass.clone();

        //ProtectedDemo也是Object的子类
        ProtectedDemo thisSubClass = new ProtectedDemo();
        //编译正确
        thisSubClass.clone();
    }
}

class MyObject {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
```
示例1和示例2说明： 
当子类和基类不在同一个包时，  
**1、不能在一个子类中通过另一个子类的实例访问基类的protected方法，尽管这两个子类继承自同一个基类。**  
**2、不能在子类中通过基类的实例访问基类的protected方法。**  
**3、可以在子类自身中通过子类的实例访问基类的protected方法。**
# **protected总结**   
对于protected的成员或方法，要分子类和超类是否在同一个包中。  
与基类不在同一个包中的子类，只能访问**自身**从基类继承而来的受保护成员，而不能访问基类实例本身的受保护成员。   
在相同包时，protected和public是一样的。  