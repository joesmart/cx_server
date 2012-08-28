package com.cl.cx.platform.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-2
 * Time: 下午4:20
 * FileName:DataItem
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class DataItem extends BasicItem {
    private String id;
    private String name;
    private String signature;
    private Boolean auditPassed;
    private String auditStatus;
    private Boolean purchased;
    private Boolean collected;
    private Boolean hasUsed;
    private Integer level;
    private Double price;
    private String thumbnailPath;
    private String sourceImagePath;
    private String downloadNumber;
    private String resourceType;
    private String description;
    private String graphicURL;
    private String favoriteId;
    private Integer modeType;
    private List<String> phoneNos;
    private String mGraphicId;
    private Boolean inUsing;
    private String mediaType;
    private Long holidayType;
    private Long statusType;
    private Date begin;
    private Date end;
    private Boolean subScribe;
}
