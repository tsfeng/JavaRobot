package com.tsfeng.cn.core.java8;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/22 16:42
 */
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
