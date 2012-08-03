package com.server.cx.entity.cx;

import javax.persistence.Column;
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
public class UserCustomMGraphic extends MGraphic {
    private Date begin;
    private Date end;
    private String phoneNos;

    public UserCustomMGraphic(){
        this.setPriority(5);
        this.setModeType(3);
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

    @Column(length = 4000)
    public String getPhoneNos() {
        return phoneNos;
    }

    public void setPhoneNos(String phoneNos) {
        this.phoneNos = phoneNos;
    }
}
