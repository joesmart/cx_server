package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;

import javax.persistence.*;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-22
 * Time: 下午4:51
 * FileName:UserDiyGraphic
 */
@Entity
@Table(name = "user_diy_graphic")
public class UserDiyGraphic extends AuditableStringEntity {
    private String name;
    private String signature;
    private List<GraphicResource> graphicResources;
    private UserInfo userInfo;

    @OneToMany(
               cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
               mappedBy = "userDiyGraphic", fetch = FetchType.LAZY
               )
    public List<GraphicResource> getGraphicResources() {
        return graphicResources;
    }

    public void setGraphicResources(List<GraphicResource> graphicResources) {
        this.graphicResources = graphicResources;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

}
