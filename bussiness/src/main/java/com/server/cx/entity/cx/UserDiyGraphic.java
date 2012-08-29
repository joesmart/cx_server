package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;
import com.server.cx.util.business.AuditStatus;

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
public class UserDiyGraphic extends AuditableStringEntity{
    private UserInfo userInfo;
    private String name;
    private String signature;
    private AuditStatus auditStatus;
    private List<GraphicResource> graphicResources;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Column(length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 40)
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "diyGraphic", fetch = FetchType.LAZY)
    public List<GraphicResource> getGraphicResources() {
        return graphicResources;
    }

    public void setGraphicResources(List<GraphicResource> graphicResources) {
        this.graphicResources = graphicResources;
    }

    public AuditStatus getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(AuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }
}
