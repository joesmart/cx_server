package com.server.cx.entity.cx;

import com.server.cx.entity.basic.LongTypeIdBaseEntity;
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
public class HolidayType extends LongTypeIdBaseEntity {
    private String name;
    private Integer downloadNum;
    private String graphicResourceId;
    private Integer level;
    private Integer num;
    
    
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
