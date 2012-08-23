package com.server.cx.entity.cx;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue(value = "holiday")
@Entity
public class HolidaySubscribeType extends SubscribeType {

    public HolidaySubscribeType() {
        super();
        setName("节日包");
    }

}
