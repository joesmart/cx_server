package com.server.cx.entity.cx;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.ToString;
import com.server.cx.entity.basic.AuditableEntity;

@Entity
@ToString
public class UserGraphicItemSubscribe extends AuditableEntity {
    private UserInfo userInfo;
    private GraphicInfo graphicInfo;
    
    @OneToOne
    @JoinColumn(name="user_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="graphic_info_id")
    public GraphicInfo getGraphicInfo() {
        return graphicInfo;
    }

    public void setGraphicInfo(GraphicInfo graphicInfo) {
        this.graphicInfo = graphicInfo;
    }

}
