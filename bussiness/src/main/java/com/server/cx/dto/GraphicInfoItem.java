package com.server.cx.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午5:46
 * FileName:GraphicInfoItem
 */
@ToString(callSuper = true)
@Setter @Getter
public class GraphicInfoItem extends DataItem {
    private String id;
    private String name;
    private String signature;
    private Boolean auditPassed;
    private Boolean purchased;
    private Boolean collected;
    private Integer level;
    private Float price;
    private String thumbnailPath;
    private String sourceImagePath;
    private String downloadNumber;
    private Action action;
}
