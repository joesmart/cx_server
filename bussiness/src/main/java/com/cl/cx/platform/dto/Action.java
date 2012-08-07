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
    private String purchaseURL;
    private String removeURL;
    private String disableURL;
    
    private String graphicInfoRecommendUrl;
    private String graphicInfoHotUrl;
    private String graphicInfoByCategoryUrl;
    private String myCXListUrl;
    private String statusPackageListUrl;
    private String holidayPackageListUrl;
    private String themePackageListUrl;
    private String versionUpdateUrl;
    private String suggestionUrl;
    private String phoneCallUrl;
    private String userCollectListUrl;
    private String userRegisterUrl;
}
