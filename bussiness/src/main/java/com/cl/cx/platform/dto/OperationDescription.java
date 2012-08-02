package com.cl.cx.platform.dto;

import lombok.Data;

/**
 * User: yanjianzou
 * Date: 12-8-2
 * Time: 上午10:11
 * FileName:OperationDescription
 */
@Data
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
