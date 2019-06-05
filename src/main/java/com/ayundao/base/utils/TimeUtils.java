package com.ayundao.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: TimeUtils
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/15 15:34
 * @Description: 时间工具类
 * @Version: V1.0
 */
public class TimeUtils {

    public static String setTime(Date date) {
        return convertTime(date, "yyyy-MM-dd");
    }

    public static String setTime(java.sql.Date date) {
        return convertTime(date, "yyyy-MM-dd");
    }

    public static String setTime(java.sql.Timestamp date) {
        return convertTime(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    public static String convertTime(Date date, String patten) {
        return new SimpleDateFormat(patten).format(date);
    }

    public static String convertTime(String date, String patten) {
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));
        int hour = Integer.parseInt(date.substring(8, 10));
        int min = Integer.parseInt(date.substring(10, 12));
        int sec = Integer.parseInt(date.substring(12, 14));
        cal.set(year, month - 1, day, hour, min, sec);
        return new SimpleDateFormat(patten).format(cal.getTime());
    }
    
    public static String nowTime() {
       return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
    }
}
