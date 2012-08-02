package com.cl.cx.platform.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * User: yanjianzou
 * Date: 12-8-2
 * Time: 上午10:11
 * FileName:OperationDescription
 */
@Data
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property= "@type")
public class OperationDescription {
    private Integer statusCode;
    private Integer errorCode;
    private String actionName;
    private String property;
    private String errorMessage;
    private String dealResult;
    private String developMessage;
    private String moreInfo;
}
