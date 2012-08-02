package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;

import javax.persistence.*;


@Entity
@Table(name = "user_favorites")
public class UserFavorites extends AuditableStringEntity {

    private UserInfo user;
    private GraphicInfo graphicInfo;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "user_id")
    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "graphic_info_id")
    public GraphicInfo getGraphicInfo() {
        return graphicInfo;
    }

    public void setGraphicInfo(GraphicInfo graphicInfo) {
        this.graphicInfo = graphicInfo;
    }
}
