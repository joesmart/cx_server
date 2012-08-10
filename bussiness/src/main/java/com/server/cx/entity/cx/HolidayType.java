package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.ToString;

/**
 * User: yanjianzou
 * Date: 12-7-26
 * Time: 下午5:52
 * FileName:HolidayType
 */
@Entity
@Table(name = "holiday_type")
@ToString
public class HolidayType extends AuditableEntity {
    private String name;
    private Integer downloadNum;
    private String graphicResourceId;
    private Integer level;
    
    
    public HolidayType() {
        super();
        //TODO 节日分类需要增加星级数，默认为5， 数字需移除  by JinHui
        level = 5;
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

    public Integer getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(Integer downloadNum) {
        this.downloadNum = downloadNum;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }  
    
}
