package com.server.cx.entity.cx;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="status")
public class StatusSubscribeType extends SubscribeType {

    public StatusSubscribeType() {
        super();
        setName("状态包");
    }

}
