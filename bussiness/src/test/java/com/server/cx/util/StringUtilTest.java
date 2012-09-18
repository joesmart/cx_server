package com.server.cx.util;

import junit.framework.Assert;
import org.junit.Test;

public class StringUtilTest {
    @Test
    public void test_email_format_invalid() {
        try {
            String email = "123sldfslf@";
            StringUtil.checkEmailFormatValid(email);
            Assert.fail("没有抛出异常");
        } catch (Exception e) {

        }
    }

    @Test
    public void test_email_format_valid() {
        String email = "jin4644@126.com";
        StringUtil.checkEmailFormatValid(email);
        Assert.assertTrue(true);
    }
}
