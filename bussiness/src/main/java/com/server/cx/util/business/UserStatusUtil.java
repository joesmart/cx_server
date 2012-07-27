package com.server.cx.util.business;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class UserStatusUtil {

    public static Timestamp getStatusUserCXInfo(Timestamp begingTime, String validTime) {
        List<Integer> list = validateTimeSplitIntoList(validTime);

        Integer hour = list.get(0);
        Integer minuter = list.get(1);

        Calendar calendar = new GregorianCalendar(Locale.CHINA);
        calendar.setTimeInMillis(begingTime.getTime());
        calendar.add(Calendar.MINUTE, minuter);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
        return timestamp;
    }

    public static List<Integer> validateTimeSplitIntoList(String validatTime) {
        Preconditions.checkNotNull(validatTime);

        CharMatcher hourMatchar = CharMatcher.is('时').or(CharMatcher.is('H'));
        CharMatcher minuterChar = CharMatcher.is('分').or(CharMatcher.is('M'));

        int hourPosition = hourMatchar.indexIn(validatTime);
        int minuterPosition = minuterChar.indexIn(validatTime);

        String hourString = "0";
        String minuterString = "0";
        if (hourPosition != -1) {
            hourString = validatTime.substring(0, hourPosition);
        }
        if (minuterPosition != -1) {
            minuterString = validatTime.substring(hourPosition + 1, minuterPosition);
        }

        if ("从不".equals(validatTime)) {
            hourString = "1440";
            minuterString = "0";
        }

        List<Integer> list = Lists.newArrayList();
        list.add(Integer.parseInt(hourString));
        list.add(Integer.parseInt(minuterString));
        return list;
    }


}
