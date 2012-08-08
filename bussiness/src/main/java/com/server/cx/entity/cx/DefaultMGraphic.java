package com.server.cx.entity.cx;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * User: yanjianzou
 * Date: 12-8-8
 * Time: 下午1:16
 * FileName:DefaultMGraphic
 */

@Entity
@DiscriminatorValue("default")
@ToString
public class DefaultMGraphic extends MGraphic {
    private String specialPhoneNo;

    public  DefaultMGraphic(){
        setModeType(0);
        setPriority(1);
    }

    @Column(length = 20)
    public String getSpecialPhoneNo() {
        return specialPhoneNo;
    }

    public void setSpecialPhoneNo(String specialPhoneNo) {
        this.specialPhoneNo = specialPhoneNo;
    }
}
