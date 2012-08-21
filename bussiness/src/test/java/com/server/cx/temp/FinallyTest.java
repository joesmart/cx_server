package com.server.cx.temp;

import org.junit.Test;

/**
 * User: yanjianzou
 * Date: 12-8-21
 * Time: 上午11:26
 * FileName:FinallyTest
 */
public class FinallyTest {

    @Test(expected = Exception.class)
    public void testFinallyException(){
        try {
            if(true) throw new Exception();
        }finally {
            return;
        }
    }
    @Test(expected = Exception.class)
    public void testCatchException() throws Exception {
        try {
            throw new Exception("xxxxxx");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
//            return;
        }
    }

}
