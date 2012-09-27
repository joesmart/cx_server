package com.server.cx.util;

import org.fest.assertions.Assertions;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.Date;

/**
 * User: yanjianzou
 * Date: 9/27/12
 * Time: 4:27 PM
 */
public class TimeCompareTest {

    private boolean timeCompared(Date currentDate, Date begin, Date end) {

        if (currentDate != null && begin != null && end != null) {
            if (currentDate.getTime() == begin.getTime() || currentDate.getTime() == end.getTime()) {
                return true;
            } else {
                return currentDate.before(end) && currentDate.after(begin);
            }
        }else {
            return false;
        }
    }

    @Test
    public void test(){
        Assertions.assertThat(timeCompared(LocalDate.now().toDate(),LocalDate.now().minusYears(1).toDate(),LocalDate.now().plusDays(2).toDate())).isTrue();
    }

    public  void test2(){
        Assertions.assertThat(timeCompared(LocalDate.now().toDate(),null,LocalDate.now().plusDays(2).toDate())).isFalse();
    }



}
