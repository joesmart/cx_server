package com.server.cx.dto;

import lombok.ToString;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午4:23
 * FileName:DataItem
 */
@ToString
public class DataItem {
    private String href;
    private Action action;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
