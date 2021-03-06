package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.util.Date;

/**
 * User: yanjianzou Date: 12-8-3 Time: 下午12:36 FileName:MGraphic
 */
@Entity
@Table(name = "mgraphic")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "cast_type", discriminatorType = DiscriminatorType.STRING, length = 10)
@DiscriminatorValue("basic")
@ToString
public class MGraphic extends AuditableStringEntity {

    private Date modifyTime;
    private Integer modeType; // 1--common 2-special user 3--Custom 4-- holiday 5-- status
    private UserInfo userInfo;
    private String signature;
    private String name;
    private GraphicInfo graphicInfo;
    private GraphicResource graphicResource;
    private Integer priority;
    private Boolean subscribe;



    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "graphic_resource_id")
    public GraphicResource getGraphicResource() {
        return graphicResource;
    }

    public void setGraphicResource(GraphicResource graphicResource) {
        this.graphicResource = graphicResource;
    }

    public Integer getModeType() {
        return modeType;
    }

    public void setModeType(Integer modeType) {
        this.modeType = modeType;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }

}
