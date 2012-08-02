package com.cl.cx.platform.dto;

import lombok.ToString;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午3:22
 * FileName:CategoryItem
 */
@ToString(callSuper = true)
public class CategoryItem extends DataItem {
    private String name;
    private String description;
    private String downloadNumber;
    private String graphicURL;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadNumber() {
        return downloadNumber;
    }

    public void setDownloadNumber(String downloadNumber) {
        this.downloadNumber = downloadNumber;
    }

    public String getGraphicURL() {
        return graphicURL;
    }

    public void setGraphicURL(String graphicURL) {
        this.graphicURL = graphicURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
