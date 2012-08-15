package com.server.cx.temp;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;

/**
 * User: yanjianzou
 * Date: 12-8-14
 * Time: 下午3:06
 * FileName:TimeTest
 */
public class TimeTest {

    @Test
    public void showTime(){
        DateTime now = DateTime.now();
        System.out.println(now.toDate().getTime());
        System.out.println(now.plusDays(1).toDate().getTime());
    }

    @Test
    public void testLocalDateParse(){
        System.out.println(LocalDate.parse("2012-1-1"));
        System.out.println(LocalDate.parse("2022-1-1"));
    }
}
