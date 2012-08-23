package com.server.cx.util;

import java.util.Calendar;


public class DateUtil {
    public static Integer getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        int result = calendar.get(Calendar.HOUR_OF_DAY);
        result = result == 0 ? 24 : result;
        return result;
    }
    
    public static Integer getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        int result = calendar.get(Calendar.MONTH) + 1;
        return result;
    }
    
    public static int getNextMonth() {
        int nextMonth = (getCurrentMonth() + 1) % 12;
        return (nextMonth == 0) ? 12 : nextMonth;
    }
}
