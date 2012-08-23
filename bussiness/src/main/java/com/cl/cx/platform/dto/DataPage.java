package com.cl.cx.platform.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.ToString;
import java.util.List;

/**
 * User: yanjianzou Date: 12-7-30 Time: 下午4:22 FileName:DataPage
 */
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public class DataPage {
    private String href;
    private Integer offset;
    private Integer limit;
    private Integer total;
    private String first;
    private String last;
    private String next;
    private String previous;
    private List<DataItem> items;
    private Boolean isSubscribed;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<DataItem> getItems() {
        return items;
    }

    public void setItems(List<DataItem> items) {
        this.items = items;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }
}
