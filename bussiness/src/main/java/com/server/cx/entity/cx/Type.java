package com.server.cx.entity.cx;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="type")
public class Type extends AuditableEntity {
    private String name;

    public Type(){
        
    }
    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

