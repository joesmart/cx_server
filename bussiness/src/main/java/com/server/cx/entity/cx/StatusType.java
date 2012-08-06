package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: yanjianzou Date: 12-7-26 Time: 下午5:34 FileName:StatusType
 */
@Entity
@Table(name = "status_type")
public class StatusType extends AuditableEntity {

    private String name;
    private String graphicResourceId;
    private Integer downloadNum;
    private Integer itemsNum;
    private Integer level;

    public StatusType() {
        super();
        //TODO 节日分类需要增加星级数，默认为5， 数字需移除  by JinHui
        level = 5;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

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
}
