package com.server.cx.entity.cx;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午1:19
 * FileName:UserSpecialMGraphic
 */
@Entity
@DiscriminatorValue("special")
public class UserSpecialMGraphic extends UserCommonMGraphic {
    private String phoneNos;

    public UserSpecialMGraphic(){
        this.setPriority(4);
        this.setModeType(2);
    }

    @Column(length = 4000)
    public String getPhoneNos() {
        return phoneNos;
    }

    public void setPhoneNos(String phoneNos) {
        this.phoneNos = phoneNos;
    }
}
