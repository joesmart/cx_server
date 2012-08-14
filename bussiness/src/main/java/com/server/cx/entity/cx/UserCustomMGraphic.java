package com.server.cx.entity.cx;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * User: yanjianzou
 * Date: 12-7-26
 * Time: 下午6:04
 * FileName:UserCustomMGraphic
 */
@Entity
@DiscriminatorValue("custom")
public class UserCustomMGraphic extends UserCommonMGraphic {
    private Date begin;
    private Date end;

    public UserCustomMGraphic(){
        this.setPriority(9);
        this.setModeType(3);
        this.setCommon(true);
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
