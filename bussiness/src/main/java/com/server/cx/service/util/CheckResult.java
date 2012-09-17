package com.server.cx.service.util;

/**
 * Created with IntelliJ IDEA.
 * User: test
 * Date: 9/17/12
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */
public enum CheckResult {
    PASS,
    UNPASS;

    public String getValue(){
        return  this.toString();
    }
}