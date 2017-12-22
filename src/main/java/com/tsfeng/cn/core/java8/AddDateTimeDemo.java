package com.tsfeng.cn.core.java8;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/22 17:33
 */
public class AddDateTimeDemo {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(FORMAT);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    public static void main(String[] args) {
        // Get current date
        Date currentDate = new Date();
        System.out.println("date : " + DATE_FORMAT.format(currentDate));

        // convert date to localdatetime
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println("localDateTime : " + DATE_TIME_FORMATTER.format(localDateTime));

        // plus one
        localDateTime = localDateTime.plusYears(1).plusMonths(1).plusDays(1);
        localDateTime = localDateTime.plusHours(1).plusMinutes(2).minusMinutes(1).plusSeconds(1);

        // convert LocalDateTime to date
        Date currentDatePlusOneDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        System.out.println("\nOutput : " + DATE_FORMAT.format(currentDatePlusOneDay));

    }
}
