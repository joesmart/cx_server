package com.server.cx.entity.cx;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="user_favorites")
public class UserFavorites extends AuditableEntity {
    
    private UserInfo user;
    private CXInfo cxInfo;
    
    @XmlTransient
    @ManyToOne( cascade = {CascadeType.PERSIST,CascadeType.REFRESH},optional = true)
    @JoinColumn(name = "user_id")
    public UserInfo getUser() {
        return user;
    }
    public void setUser(UserInfo user) {
        this.user = user;
    }
    
    @ManyToOne( cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cxinfo_id")
    public CXInfo getCxInfo() {
        return cxInfo;
    }
    public void setCxInfo(CXInfo cxInfo) {
        this.cxInfo = cxInfo;
    }
}
