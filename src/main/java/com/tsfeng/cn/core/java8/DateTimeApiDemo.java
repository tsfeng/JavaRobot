package com.tsfeng.cn.core.java8;

import javax.sound.midi.Soundbank;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/14 23:23
 * Date-Time API
 * 1、LocalDate
 * 2、LocalTime
 * 3、Instant
 * 4、LocalDateTime
 * 5、ZonedDateTime
 */
public class DateTimeApiDemo {

    private static final DateFormat DATE_SDF = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat DATE_TIME_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws ParseException {
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

        OffsetTime offsetTime = OffsetTime.now();
        System.out.println(offsetTime);

//
//        System.out.println(new Date());

        //日期比较
        Date date1 = DATE_SDF.parse("2018-01-01");
        Date date2 = DATE_SDF.parse("2017-12-17");
        System.out.println("date1 : " + DATE_SDF.format(date1));
        System.out.println("date2 : " + DATE_SDF.format(date2));
//
//        //Date.compareTo()
//        System.out.println("====================Date.compareTo()");
//        if (date1.compareTo(date2) > 0) {
//            System.out.println("date1 is after date2");
//        } else if (date1.compareTo(date2) < 0) {
//            System.out.println("date1 is before date2");
//        } else if (date1.compareTo(date2) == 0) {
//            System.out.println("date1 is equal to date2");
//        }
//
//        //Date.before()，Date.after()和Date.equals()
//        System.out.println("====================Date.before()，Date.after()和Date.equals()");
//        if (date1.after(date2)) {
//            System.out.println("date1 is after date2");
//        }else if (date1.before(date2)) {
//            System.out.println("date1 is before date2");
//        }else if (date1.equals(date2)) {
//            System.out.println("date1 is equal date2");
//        }
//
//        //Calender.before()，Calender.after()和Calender.equals()
//        System.out.println("====================Calender.before()，Calender.after()和Calender.equals()");
//        Calendar cal1 = Calendar.getInstance();
//        Calendar cal2 = Calendar.getInstance();
//        cal1.setTime(date1);
//        cal2.setTime(date2);
//        if (cal1.after(cal2)) {
//            System.out.println("date1 is after date2");
//        } else if (cal1.before(cal2)) {
//            System.out.println("date1 is before date2");
//        } else if (cal1.equals(cal2)) {
//            System.out.println("date1 is equal date2");
//        }
//        System.out.println("====================分隔Java8");
//        //Java8
        LocalDate date3 = LocalDate.of(2018, 01, 01);
        LocalDate date4 = LocalDate.of(2017, 12, 17);
        LocalDate date5 = LocalDate.parse("2017-12-17", DATE_FORMATTER);
        System.out.println("date3 : " + DATE_FORMATTER.format(date3));
        System.out.println("date4 : " + DATE_FORMATTER.format(date4));
        System.out.println("date5 : " + DATE_FORMATTER.format(date5));
//
//        System.out.println("====================Java8 isBefore(), isAfter(), isEqual()");
//        if (date3.isAfter(date4)) {
//            System.out.println("date3 is after date4");
//        }else if (date3.isBefore(date4)) {
//            System.out.println("date3 is before date4");
//        }else if (date3.isEqual(date4)) {
//            System.out.println("date3 is equal date4");
//        }
//        System.out.println("====================Java8 compareTo()");
//        if (date3.compareTo(date4) > 0) {
//            System.out.println("date3 is after date4");
//        } else if (date3.compareTo(date4) < 0) {
//            System.out.println("date3 is before date4");
//        } else if (date3.compareTo(date4) == 0) {
//            System.out.println("date3 is equal to date4");
//        }
        //获取当前时间
//        Date date = new Date();
//        System.out.println(sdf.format(date));
//
//        Calendar cal = Calendar.getInstance();
//        System.out.println(sdf.format(cal.getTime()));
//
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(formatter.format(now));
//
//        LocalDate localDate = LocalDate.now();
//        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate));

        //获取时间戳
        //method 1
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        System.out.println(timestamp);
//
//        //method 2 - via Date
//        Date date = new Date();
//        System.out.println(new Timestamp(date.getTime()));
//
//        //return number of milliseconds since January 1, 1970, 00:00:00 GMT
//        System.out.println(timestamp.getTime());
//
//        //format timestamp
//        System.out.println(sdf.format(timestamp));
    }
}
