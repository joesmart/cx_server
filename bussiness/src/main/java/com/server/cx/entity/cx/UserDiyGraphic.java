package com.server.cx.entity.cx;

import javax.persistence.*;

/**
 * User: yanjianzou
 * Date: 12-8-22
 * Time: 下午4:51
 * FileName:UserDiyGraphic
 */
@Entity
@Table(name = "user_diy_graphic")
@DiscriminatorValue("diy")
public class UserDiyGraphic extends GraphicInfo {
    private UserInfo userInfo;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

}
