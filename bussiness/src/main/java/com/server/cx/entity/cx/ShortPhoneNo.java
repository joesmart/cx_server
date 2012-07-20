package com.server.cx.entity.cx;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "short_phoneno")
public class ShortPhoneNo extends AuditableEntity {

    private String shortPhoneNo;
    private UserInfo user;

    
    public String getShortPhoneNo() {
        return shortPhoneNo;
    }
    public void setShortPhoneNo(String shortPhoneNo) {
        this.shortPhoneNo = shortPhoneNo;
    }
    @ManyToOne( cascade = {CascadeType.PERSIST,CascadeType.REFRESH},optional = true)
    @JoinColumn(name = "user_id")
    public UserInfo getUser() {
        return user;
    }
    public void setUser(UserInfo user) {
        this.user = user;
    }
    
}
