package com.server.cx.entity.cx;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * User: yanjianzou
 * Date: 12-7-26
 * Time: 下午6:03
 * FileName:UserHolidayMGraphic
 */
@Entity
@DiscriminatorValue("holiday")
public class UserHolidayMGraphic extends MGraphic {
    private Date holiday;

    public UserHolidayMGraphic(){
        this.setPriority(6);
        this.setModeType(4);
    }

    public Date getHoliday() {
        return holiday;
    }

    public void setHoliday(Date holiday) {
        this.holiday = holiday;
    }
}
