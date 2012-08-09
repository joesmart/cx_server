package com.cl.cx.platform.dto;

import lombok.Data;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-2
 * Time: 下午4:20
 * FileName:DataItem
 */
@Data
public class DataItem extends BasicItem {
    private String id;
    private String name;
    private String signature;
    private Boolean auditPassed;
    private Boolean purchased;
    private Boolean collected;
    private Boolean hasUsed;
    private Integer level;
    private Float price;
    private String thumbnailPath;
    private String sourceImagePath;
    private String downloadNumber;
    private Action action;
    private String resourceType;
    private String description;
    private String graphicURL;
    private String favoriteId;
    private Integer modeType;
    private List<String> phoneNos;
    private String mGraphicId;
    private Boolean inUsing;
}
