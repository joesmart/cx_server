package com.server.cx.entity.cx;

import com.server.cx.entity.basic.UUIDTypeBaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: yanjianzou
 * Date: 9/21/12
 * Time: 2:22 PM
 * To change this template use File | Settings | File Templates.
 */

//@Entity
//@Table(name = "human")
public class Human extends UUIDTypeBaseEntity{


    private String name;

    private String lastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}