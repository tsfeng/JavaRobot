Java8于2014年3月18日发布，Java8作为Java的一个重大版本，新增了非常多的特性；但很多Java开发人员由于项目原因，在工作中没机会去使用Java8的新特性。目前Java9已于2017年9月21日正式发布，如果你对Java8的新特性还不是很了解，但愿《Java8新特性系列》文章对你有所帮助。
本篇将介绍Java8新特性之**方法引用(Method References)**。
###**什么是方法引用**
在以前的文章中，我们介绍了函数式接口和Lambda表达式。已经知道Java8之前，如果我们只是在另一个方法中使用一个对象的方法，我们仍然必须传递完整的对象作为参数。而Java8的Lambda表达式让我们可以像使用对象或原始值一样使用方法。
“方法引用”是Java8中提供的一种新功能。这是一个与Lambda表达式有关的功能。
例如，以下Lambda表达式：
```java
list.forEach(e -> System.out.println(e));
```
可以用“方法引用”替换成如下：
```java
list.forEach(System.out::println);
```
方法引用**使用::运算符将类或对象与方法名称分开**(只需要写方法名，不需要写括号)。
###**方法引用的类型**
```table
类型(<)     |    语法(<) 
1、引用静态方法        |      Class::staticMethodName         
2、引用构造函数        |    	ClassName::new     
3、引用特定类型任意对象的实例方法|Class::instanceMethodName     
4、引用特定对象的实例方法 |      object::instanceMethodName       
```
###**1、引用静态方法**
```java
public class StaticMethodReference {
    public static void main(String args[]) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        //方法引用
        list.forEach(StaticMethodReference::print);
        //Lambda表达式
        list.forEach(number -> StaticMethodReference.print(number));
        //Java8之前
        for (int number : list) {
            StaticMethodReference.print(number);
        }
    }
    public static void print(final int number) {
        System.out.println(number);
    }
}
```
###**2、引用构造函数**
```java
public class ConstructorMethodReference {
    public static void main(String args[]) {
        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        //方法引用
        copyElements(list, ArrayList::new);
        //Lambda表达式
        copyElements(list, () -> new ArrayList<>());
    }
    private static void copyElements(final List<Integer> list, final Supplier<Collection<Integer>> targetCollection) {
        list.forEach(targetCollection.get()::add);
    }
}
```
###**3、引用特定类型任意对象的实例方法**
```java
public class ArbitraryInstanceMethodReference {
    public static void main(String args[]) {
        final List<Person> people = Arrays.asList(new Person("张三"), new Person("李四"));
        //方法引用
        people.forEach(Person::printName);
        //Lambda表达式
        people.forEach(person -> person.printName());
        //Java8之前
        for (final Person person : people) {
            person.printName();
        }
    }
    private static class Person {
        private String name;
        public Person(final String name) {
            this.name = name;
        }
        public void printName() {
            System.out.println(name);
        }
    }
}
```
###**4、引用特定对象的实例方法**
```java
public class ParticularInstanceMethodReference {
    public static void main(String args[]) {
        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        final MyComparator myComparator = new MyComparator();
        // 方法引用
        Collections.sort(list, myComparator::compare);
        // Lambda表达式
        Collections.sort(list, (a, b) -> myComparator.compare(a, b));
    }
    private static class MyComparator {
        public int compare(final Integer a, final Integer b) {
            return a.compareTo(b);
        }
    }
}
```
###**总结**
**方法引用使用::运算符将类或对象与方法名称分开**。如果可以使用Lambda表达式来简化代码，则可以通过使用方法引用使其更简短。方法引用可以使语言的构造更紧凑简洁，减少冗余代码。
需要注意的是：**方法引用只能用来替换单条语句的Lambda表达式**。