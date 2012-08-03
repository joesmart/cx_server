package com.server.cx.entity.cx;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * User: yanjianzou
 * Date: 12-7-26
 * Time: 下午6:03
 * FileName:UserStatusMGraphic
 */
@Entity
@DiscriminatorValue("status")
public class UserStatusMGraphic extends MGraphic {
    private Date validDate;

    public UserStatusMGraphic(){
        this.setPriority(7);
        this.setModeType(5);
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

}
