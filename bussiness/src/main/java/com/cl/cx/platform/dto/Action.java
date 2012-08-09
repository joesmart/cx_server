package com.cl.cx.platform.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午3:24
 * FileName:Action
 */
@Data
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property= "@type")
public class Action {
    private String zoneInURL;
    private String zoneOutURL;
    private String useURL;
    private String collectURL;
    private String removeURL;
    private String disableURL;
    private String editURL;


    //TODO 接口下发列表 需要新的DTO 对象
    private String recommendURL;
    private String hotURL;
    private String categoryURL;
    private String mGraphicsURL;
    private String statusURL;
    private String holidaysURL;
    private String customMGraphicsURL;
    private String versionURL;
    private String suggestionURL;
    private String callURL;
    private String collectionsURL;
    private String registerURL;
    private String purchaseURL;
}
