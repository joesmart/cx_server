package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "user_favorites")
public class UserFavorites extends AuditableEntity {

    private UserInfo user;
    private String graphicInfoId;

    @XmlTransient
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "user_id")
    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getGraphicInfoId() {
        return graphicInfoId;
    }

    public void setGraphicInfoId(String graphicInfoId) {
        this.graphicInfoId = graphicInfoId;
    }
}
