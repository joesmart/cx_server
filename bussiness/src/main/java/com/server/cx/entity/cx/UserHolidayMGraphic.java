package com.server.cx.entity.cx;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * User: yanjianzou Date: 12-7-26 Time: 下午6:03 FileName:UserHolidayMGraphic
 */
@Entity
@DiscriminatorValue("holiday")
public class UserHolidayMGraphic extends UserCommonMGraphic {
    private Date holiday;
    private HolidayType holidayType;
    
    @ManyToOne
    @JoinColumn(name = "holiday_type_id")
    public HolidayType getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(HolidayType holidayType) {
        this.holidayType = holidayType;
    }


    public UserHolidayMGraphic() {
        this.setPriority(7);
        this.setModeType(4);
    }

    public Date getHoliday() {
        return holiday;
    }

    public void setHoliday(Date holiday) {
        this.holiday = holiday;
    }

}
