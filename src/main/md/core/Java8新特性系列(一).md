Java8于2014年3月18日发布，Java8作为Java的一个重大版本，新增了非常多的特性；但很多Java开发人员由于项目原因，在工作中没机会去使用Java8的新特性。目前Java9已于2017年9月21日正式发布，如果你对Java8的新特性还不是很了解，但愿《Java8新特性系列》文章对你有所帮助。
本篇将介绍Java8新特性之interface中的default和static方法。
###**Java8接口**
我们知道，在Java8之前，接口不能具有方法体。当需要修改接口时候，需要修改全部实现该接口的类。
###**接口默认方法**
###**接口静态方法**
###**接口默认方法**
从Java 8开始，接口被增强，我们可以在接口中使用**默认方法**和**静态方法**。我们可以使用default和static关键字来创建具有默认实现的接口。Java8中的Iterable接口代码如下，新增的forEach和spliterator方法都带有默认实现。
```java
public interface Iterable<T> {
    Iterator<T> iterator();
    
    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }
    
    default Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
}
```
我们知道在Java中，类不支持多重继承，因为那样的话
