package com.server.cx.entity.cx;

import com.server.cx.entity.basic.LongTypeIdBaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * User: yanjianzou
 * Date: 12-8-10
 * Time: 下午1:01
 * FileName:Holiday
 */
@Entity
@Table(name = "holiday")
public class Holiday extends LongTypeIdBaseEntity {
    private Date day;
    private HolidayType type;

    @Column(name = "holiday_day")
    @Temporal(TemporalType.DATE)
    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    @ManyToOne
    @JoinColumn(name = "type")
    public HolidayType getType() {
        return type;
    }

    public void setType(HolidayType type) {
        this.type = type;
    }
}
