package com.server.cx.temp;

import com.google.common.base.Preconditions;
import org.junit.Test;

/**
 * User: yanjianzou
 * Date: 12-8-1
 * Time: 上午10:06
 * FileName:GuavaTest
 */
public class GuavaTest {
    public void test(){


    }

    @Test(expected = IllegalArgumentException.class )
    public void should_throw_a_exception(){
        Preconditions.checkArgument(false,"error");
    }
}
