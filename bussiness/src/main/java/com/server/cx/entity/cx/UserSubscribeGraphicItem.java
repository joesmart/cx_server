package com.server.cx.entity.cx;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.ToString;
import com.server.cx.entity.basic.AuditableEntity;

@Entity
@ToString
public class UserSubscribeGraphicItem extends AuditableEntity {
    public UserSubscribeGraphicItem(UserInfo userInfo, GraphicInfo graphicInfo) {
        super();
        this.userInfo = userInfo;
        this.graphicInfo = graphicInfo;
    }

    private UserInfo userInfo;
    private GraphicInfo graphicInfo;
    private Date validateTime;

    public Date getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(Date validateTime) {
        this.validateTime = validateTime;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "graphic_info_id")
    public GraphicInfo getGraphicInfo() {
        return graphicInfo;
    }

    public void setGraphicInfo(GraphicInfo graphicInfo) {
        this.graphicInfo = graphicInfo;
    }

    public UserSubscribeGraphicItem() {
        super();
    }

}
