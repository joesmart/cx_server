package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: yanjianzou
 * Date: 12-7-26
 * Time: 下午5:52
 * FileName:HolidayType
 */
@Entity
@Table(name = "holiday_type")
@ToString
@EqualsAndHashCode(callSuper = false)
public class HolidayType extends AuditableEntity {
    private String name;
    private Integer downloadNum;
    private Integer itemsNum;
    private String graphicResourceId;

    public String getGraphicResourceId() {
        return graphicResourceId;
    }

    public void setGraphicResourceId(String graphicResourceId) {
        this.graphicResourceId = graphicResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(Integer downloadNum) {
        this.downloadNum = downloadNum;
    }

    public Integer getItemsNum() {
        return itemsNum;
    }

    public void setItemsNum(Integer itemsNum) {
        this.itemsNum = itemsNum;
    }
}
