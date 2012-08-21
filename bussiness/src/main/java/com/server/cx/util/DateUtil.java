package com.server.cx.util;

import java.util.Calendar;


class DateUtil {
    public static Integer getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        int result = calendar.get(Calendar.HOUR_OF_DAY);
        result = result == 0 ? 24 : result;
        return result;
    }
}
