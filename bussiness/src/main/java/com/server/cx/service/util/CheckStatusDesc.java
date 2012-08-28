package com.server.cx.service.util;

/**
 * User: ZouYanjian
 * Date: 12-6-28
 * Time: 上午11:02
 * FileName:CheckStatusDesc
 */
public enum CheckStatusDesc {
    ALL,
    CHECKED,
    UNCHECKED,
    CHECKING,
    LATEST;

    public String getValue(){
        return  this.toString();
    }
}
