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
    
    private String recommendUrl;
    private String hotUrl;
    private String categoryUrl;
    private String mGraphicsUrl;
    private String statusUrl;
    private String holidaiesUrl;
    private String customMGraphicsUrl;
    private String versionUrl;
    private String suggestionUrl;
    private String callUrl;
    private String collectionsUrl;
    private String registerUrl;
    private String purchaseURL;
}
