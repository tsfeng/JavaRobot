package com.tsfeng.cn.funny;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/28 9:39
 */
public class Date1927 {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str3 = "1927-12-31 23:54:07";
        String str4 = "1927-12-31 23:54:08";
        Date sDt3 = sf.parse(str3);
        Date sDt4 = sf.parse(str4);
        long ld3 = sDt3.getTime() / 1000;
        long ld4 = sDt4.getTime() / 1000;
        System.out.println(ld4 - ld3);
    }
}
