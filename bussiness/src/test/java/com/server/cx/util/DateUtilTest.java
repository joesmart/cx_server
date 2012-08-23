package com.server.cx.util;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class DateUtilTest {
    @Test
    public void test_get_next_month() {
        DateUtil dateUtil = new DateUtil();
        int month = dateUtil.getNextMonth();
        Assertions.assertThat(month).isEqualTo(9);
    }
}
