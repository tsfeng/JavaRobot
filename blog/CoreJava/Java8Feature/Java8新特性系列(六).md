在上一篇[Java8新特性系列（五）](https://github.com/tsfeng/JavaRobot/blob/master/blog/CoreJava/Java8Feature/Java8%E6%96%B0%E7%89%B9%E6%80%A7%E7%B3%BB%E5%88%97(%E4%BA%94).md)中，我们介绍了Java8新特性之流(Stream)。    
本篇将介绍Java8新特性之**Date-Time API**。  
为了方便描述，本文暂且把Java8之前的日期时间API称为旧版API；相对的，把Java8的日期时间API称为新版API。
# **了解GMT、UTC和CST**
GMT代表格林尼治标准时间：
> 格林尼治标准时间（英语：Greenwich Mean Time，GMT）是指位于英国伦敦郊区的皇家格林尼治天文台当地的标准时间，因为本初子午线被定义为通过那里的经线。
自1924年2月5日开始，格林尼治天文台负责每隔一小时向全世界发放调时信息。
理论上来说，格林尼治标准时间的正午是指当太阳横穿格林尼治子午线时（也就是在格林尼治上空最高点时）的时间。但由于地球在它的椭圆轨道里的运动速度不均匀，这个时刻可能与实际的太阳时有误差，最大误差达16分钟。原因在于地球每天的自转是有些不规则的，而且正在缓慢减速，因此格林尼治时间基于天文观测本身的缺陷，已经不再被作为标准时间使用。现在的标准时间，是由原子钟报时的协调世界时（UTC）来决定。-维基百科

UTC又称协调世界时：
> 协调世界时（英语：Coordinated Universal Time，简称UTC）是最主要的世界时间标准，其以原子时秒长为基础，在时刻上尽量接近于格林尼治标准时间。
协调世界时是世界上调节时钟和时间的主要时间标准，它与0度经线的平太阳时相差不超过1秒[4]，并不遵守夏令时。协调世界时是最接近格林威治标准时间(GMT)的几个替代时间系统之一。对于大多数用途来说，UTC时间被认为能与GMT时间互换，但GMT时间已不再被科学界所确定。-维基百科

CST通常指中国标准时间（China Standard Time）：
> 北京时间，又名中国标准时间，是中国大陆的标准时间，比世界协调时快八小时（即UTC+8），与香港、澳门、台北、吉隆坡、新加坡等地的标准时间相同。
北京时间并不是北京市的地方平太阳时间（东经116.4°），而是东经120°的地方平太阳时间，二者相差约14.5分钟[1]。北京时间由位于中国版图几何中心位置陕西临潼的中国科学院国家授时中心的9台铯原子钟和2台氢原子钟组通过精密比对和计算实现报时，并通过人造卫星与世界各国授时部门进行实时比对。-维基百科

# **旧版API的弊端**
**1、非线程安全**   
- 旧版API中，最常用的类莫过于java.util.Date、Calendar和SimpleDateFormat这几个类，而这几个类都是可变的，不是线程安全的。

**2、设计糟糕**    
- 旧版API中，Java的日期/时间类的定义并不一致，在java.util和java.sql的包中都有日期类，此外用于格式化和解析的类在java.text包中定义。java.util.Date同时包含日期和时间，而java.sql.Date仅包含日期，将其纳入java.sql包并不合理。另外这两个类都有相同的名字，这本身就是一个非常糟糕的设计。  
- 旧版API中，java.util.Date是“万能的”，包含日期、时间，还有毫秒数；如果你只想用java.util.Date存储日期，或者只存储时间，  
- 旧版API中，java.util.Date月份从0开始，一月是0，十二月是11；这不太直观。
  
**3、时区处理麻烦**
- 旧版API中，Date类不提供国际化，没有时区支持，你必须编写额外的逻辑处理时区。

    API设计和易用性: 由于Date和Calendar的设计不当你无法完成日常的日期操作
java.util.Date是一个“万能接口”，它包含日期、时间，还有毫秒数，如果你只想用java.util.Date存储日期，或者只存储时间，那么，只有你知道哪些部分的数据是有用的，哪些部分的数据是不能用的。

旧版API还有其他一些问题，但以上问题清晰地表明Java中需要一个更健壮的Date Time API。
# **新版API的优点**  
java.time.LocalDate月份和星期都改成了enum，就不可能再用错了。
Java 8 Date Time API是JSR-310的实现。它旨在克服遗留日期时间实现中的所有缺陷。新的Date Time API的一些设计原则是：  
**1、不可变性：** 
新Date Time API中的所有类都是不可变的，并且适用于多线程环境。  
**2、分离问题：** 
新的API将人类可读的日期时间和机器时间（Unix时间戳）分开。它为Date，Time，DateTime，Timestamp，Timezone等定义了单独的类。  
**3、清晰度：** 
方法明确定义，并在所有的类中执行相同的操作。例如，要获取当前的实例，我们有now()方法。在所有这些类中定义了format（）和parse（）方法，而不是为它们分别设置一个类。  
所有的类使用工厂模式和战略模式更好地处理。一旦你使用了其中一个类的方法，与其他类一起工作并不困难。  
**4、实用操作：**
所有新的Date Time API类都带有执行常见任务的方法，例如加号，减号，格式，解析，获取日期/时间等的单独部分。  
**5、可扩展：**
新的日期时间API适用于ISO-8601日历系统，但我们也可以将其与其他非ISO日历一起使用。  
# **新版API常用类**  
- **java.time.LocalDate；** 只包含日期
- **java.time.LocalTime；** 只包含时间
- **java.time.LocalDateTime；** 包含日期和时间
- **java.time.DateTimeFormatter；** 日期、时间格式化
- **java.time.ZonedDateTime；** 包含日期、时间和时区
- **java.time.Instant；** 机器的日期和时间，从Unix纪元时间（1970年1月1日午夜UTC）
- **java.time.Duration；**  以秒和纳秒为单位测量时间。
- **java.time.Period；**  测量年，月，日的时间。
- **java.time.TemporalAdjuster；** 调整日期。

如以下示例代码：
```java
public class DateTimeApiDemo {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println("LocalDate.now()：" + localDate);

        LocalTime localTime = LocalTime.now();
        System.out.println("LocalTime.now()：" + localTime);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("LocalDateTime.now()：" + localDateTime);

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("ZonedDateTime.now()：" + zonedDateTime);

        System.out.println("DateTimeFormatter zonedDateTime：" + zonedDateTime.format(DATE_TIME_FORMATTER));

        Instant instant = Instant.now();
        System.out.println("Instant.now()：" + instant);

        Duration duration = Duration.ofDays(30);
        System.out.println("duration, 30天秒数：" + duration.getSeconds());

        LocalDate next = localDate.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println("TemporalAdjusters，firstDayOfNextMonth：" + next);

        Period between = Period.between(localDate, next);
        System.out.println("Period：" + between.getDays());
    }
}
```
打印结果：
```
LocalDate.now()：2017-12-22
LocalTime.now()：16:32:03.305
LocalDateTime.now()：2017-12-22T16:32:03.305
ZonedDateTime.now()：2017-12-22T16:32:03.305+08:00[Asia/Shanghai]
DateTimeFormatter zonedDateTime：2017-12-22 16:32:03
Instant.now()：2017-12-22T08:32:03.306Z
duration, 30天秒数：2592000
TemporalAdjusters，firstDayOfNextMonth：2018-01-01
Period：10
```
# **获取当前日期时间** 
- 旧版API
    - new Date()；
    - Calendar.getInstance()；
- 新版API
    - LocalDateTime.now()；
    - LocalDate.now()；
# **日期比较** 
-   旧版API
    - Date.compareTo()；
    - Date.before()，Date.after()和Date.equals()；
    - Calender.before()，Calender.after()和Calender.equals()；
- 新版API
    - isBefore()，isAfter()，isEqual()和compareTo() ；
# **日期格式化**  
- 旧版API
    - SimpleDateFormat；
- 新版API
    - DateTimeFormatter；
```java
public class DateTimeApiDemo {
    private static final DateFormat DATE_SDF = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static void main(String[] args) throws ParseException {
        Date date1 = DATE_SDF.parse("2018-01-01");
        System.out.println("date1 : " + DATE_SDF.format(date1));
        
        LocalDate date5 = LocalDate.parse("2017-12-17", DATE_FORMATTER);
        System.out.println("date5 : " + DATE_FORMATTER.format(date5));
    }
}
```
# **日期加减**
- 旧版API
    - Calendar.getInstance().add()；
- 新版API（加、减法）
    - plusYears()，minusYears()；
    - plusMonths()，minusMonths()；
    - plusWeeks()，minusWeeks()；
    - plusDays()，minusDays()；
    - plusHours()，minusHours()；
    - plusMinutes()，minusMinutes()；
    - plusSeconds()，minusSeconds()；
    - plusNanos()，minusNanos()；
# **计算已用时间/执行时间**
- 旧版API
    - System.nanoTime()；
    - System.currentTimeMillis()；
    - Date().getTime()；
- 新版API
    - Instant.now().toEpochMilli()；
# **计算日期差值**
- 旧版API
    - 用Calendar类自己实现
- 新版API
    - Duration.between()，相差秒数或毫秒数；
    - Period.between()，相差年、月或日数；
    - ChronoUnit枚举类，如ChronoUnit.YEARS.between()；
# **不同时区日期时间转换**
- 旧版API
    - SimpleDateFormat的setTimeZone()方法；
- 新版API
    - ZonedDateTime的withZoneSameInstant()方法；
```java
public class ZonedDateTimeDemo {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static void main(String[] args) throws ParseException {
        //旧版API
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        Date now = new Date();
        System.out.println("本地当前时间" + formatter.format(now));
        formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        System.out.println("纽约当前时间" + formatter.format(now));

        //新版API
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT);
        ZoneId newYokZoneId = ZoneId.of("America/New_York");
        ZonedDateTime zonedDateTime1 = ZonedDateTime.now();
        System.out.println("本地当前时间" + dateTimeFormatter.format(zonedDateTime1));
        ZonedDateTime zonedDateTime2 = zonedDateTime1.withZoneSameInstant(newYokZoneId);
        System.out.println("纽约当前时间" + dateTimeFormatter.format(zonedDateTime2));
    }
}
```
打印结果：
```
本地当前时间2017-12-17 18:28:02
纽约当前时间2017-12-17 05:28:02
本地当前时间2017-12-17 18:28:02
纽约当前时间2017-12-17 05:28:02
```
# **SQL对应**  
Java8中JDBC已经支持这些新的类型  

ANSI SQL           |  Java8
 :---                    |  :----------|  
DATE                 |   LocalDate
TIME                  |   LocalTime
TIMESTAMP      |   LocalDateTime
TIME WITH TIMEZONE  |   OffsetTime
TIMESTAMP WITH TIMEZONE  |   OffsetDateTime

