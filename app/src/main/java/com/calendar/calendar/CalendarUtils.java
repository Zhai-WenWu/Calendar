package com.calendar.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 关于日期处理的工具
 */

public class CalendarUtils {

    private static Calendar calendar = Calendar.getInstance();

    //获取当前的年月
    public static String getCurentData() {
        return "" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH);
    }

    //获取当前的年
    public static int getCurentYear() {
        return calendar.get(Calendar.YEAR);
    }

    //获取当前的月
    public static int getCurentMonth() {
        return calendar.get(Calendar.MONTH);
    }

    //获取根据年，月所在的天数
    public static int getDayByMonth(int year, int month) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int maxDate = calendar.get(Calendar.DATE);
        return maxDate;
    }

    //获取当前日期属于星期几
    public static int getWeek(int nian, int yue, int ri) throws ParseException {
        int weekData = -1;
        String time = nian + "-" + yue + "-" + ri;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String week = sdf.format(format.parse(time));
        switch (week) {
            case "周一":
                weekData = 1;
                break;
            case "周二":
                weekData = 2;
                break;
            case "周三":
                weekData = 3;
                break;
            case "周四":
                weekData = 4;
                break;
            case "周五":
                weekData = 5;
                break;
            case "周六":
                weekData = 6;
                break;
            case "周日":
                weekData = -1;    //这里返回的-1根据当前项目，咱不更改
                break;
        }
        return weekData;
    }


}
