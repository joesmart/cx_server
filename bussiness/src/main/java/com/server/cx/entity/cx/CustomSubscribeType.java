package com.server.cx.entity.cx;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="custom")
public class CustomSubscribeType extends SubscribeType {

    public CustomSubscribeType() {
        super();
        setName("自主包");
    }
}
