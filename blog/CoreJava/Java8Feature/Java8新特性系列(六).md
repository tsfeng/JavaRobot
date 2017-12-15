在上一篇Java8新特性系列（五）中，我们介绍了Java8新特性之流(Stream)。
本篇将介绍Java8新特性之**Date-Time API**。
### **为什么引入新的Java Date Time API**
1、Java日期时间类没有被一致的定义，我们在包java.util和java.sql包中都有Date类。java.text包中定义了格式化和解析类。
2、java.util.Date包含日期和时间，而java.sql.Date只包含日期。拥有这个java.sql包是没有意义的。而且这两个类都有相同的名称，这本身是一个非常糟糕的设计。
3、没有明确定义的时间，时间戳，格式化和解析类。我们有java.text.DateFormat抽象类来解析和格式化需要。通常SimpleDateFormat类用于解析和格式化。
4、所有的Date类都是可变的，所以它们不是线程安全的。这是Java Date和Calendar类最大的问题之一。
5、Date类不提供国际化，没有时区支持。所以java.util.Calendar，java.util.TimeZone类被引入，但他们也有上面列出的所有问题。
在Date和Calendar类中定义的方法还有其他一些问题，但上面的问题清楚地表明Java中需要一个强大的Date Time API。这就是为什么Joda Time作为Java Date Time要求的质量替代者发挥了关键作用。
### **Java8 Date**
Java 8 Date Time API是JSR-310的实现。它旨在克服遗留日期时间实现中的所有缺陷。新的Date Time API的一些设计原则是：
**1、不可变性：**新Date Time API中的所有类都是不可变的，并且适用于多线程环境。
**2、分离问题：**新的API将人类可读的日期时间和机器时间（Unix时间戳）分开。它为Date，Time，DateTime，Timestamp，Timezone等定义了单独的类
**3、清晰度：**方法明确定义，并在所有的类中执行相同的操作。例如，要获取当前的实例，我们有now()方法。在所有这些类中定义了format（）和parse（）方法，而不是为它们分别设置一个类。
所有的类使用工厂模式和战略模式更好地处理。一旦你使用了其中一个类的方法，与其他类一起工作并不困难。
**4、实用操作：**所有新的Date Time API类都带有执行常见任务的方法，例如加号，减号，格式，解析，获取日期/时间等的单独部分。
**5、可扩展：**新的日期时间API适用于ISO-8601日历系统，但我们也可以将其与其他非ISO日历一起使用。
### **Java8日期时间API包**
Java 8 Date Time API由以下软件包组成。
**1、java.time包：**这是新的Java Date Time API的基础包。所有主要的基类是该计划的一部分，比如LocalDate，LocalTime，LocalDateTime，Instant，Period，Duration等所有这些类是不可变的和线程安全的。大多数情况下，这些类将足以满足一般要求。
**2、java.time.chrono包：**此包为非ISO日历系统定义通用API。我们可以扩展AbstractChronology课程来创建我们自己的日历系统。
**3、java.time.format包：**此包中包含用于格式化和分析日期时间对象的类。大多数情况下，我们不会直接使用它们，因为java.time包中的原则类提供了格式化和解析方法。
**4、java.time.temporal包：**这个包包含时态对象，我们可以使用它来查找与日期/时间对象相关的特定日期或时间。例如，我们可以使用这些查找月份的第一天或最后一天。您可以轻松识别这些方法，因为它们总是具有“withXXX”格式。
**5、java.time.zone包：**这个包包含支持不同时区及其规则的类。


