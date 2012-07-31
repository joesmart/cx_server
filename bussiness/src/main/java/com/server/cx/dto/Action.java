package com.server.cx.dto;

import lombok.ToString;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午3:24
 * FileName:Action
 */
@ToString
public class Action {
    private String zoneInURL;
    private String zoneOutURL;
    private String useURL;
    private String collectURL;
    private String purchaseURL;

    public String getZoneInURL() {
        return zoneInURL;
    }

    public void setZoneInURL(String zoneInURL) {
        this.zoneInURL = zoneInURL;
    }

    public String getZoneOutURL() {
        return zoneOutURL;
    }

    public void setZoneOutURL(String zoneOutURL) {
        this.zoneOutURL = zoneOutURL;
    }

    public String getCollectURL() {
        return collectURL;
    }

    public void setCollectURL(String collectURL) {
        this.collectURL = collectURL;
    }

    public String getPurchaseURL() {
        return purchaseURL;
    }

    public void setPurchaseURL(String purchaseURL) {
        this.purchaseURL = purchaseURL;
    }

    public String getUseURL() {
        return useURL;
    }

    public void setUseURL(String useURL) {
        this.useURL = useURL;
    }
}
