package com.server.cx.temp;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

/**
 * User: yanjianzou
 * Date: 12-8-1
 * Time: 上午10:06
 * FileName:GuavaTest
 */
public class GuavaTest {
    public void test() {


    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_a_exception() {
        Preconditions.checkArgument(false, "error");
    }

    @Test
    public void test_RandomUtils() {
        for (int i = 0; i < 100; i++) {
            System.out.println(RandomUtils.nextInt(5));
        }
    }

    @Test
    public void test_RandomUtils2() {
        for (int i = 0; i < 100; i++) {
            System.out.println(RandomUtils.nextInt(1));
        }
    }

    @Test
    public void test_RandomUtils3() {
        for (int i = 0; i < 100; i++) {
            System.out.println(RandomUtils.nextInt(0));
        }
    }
}
