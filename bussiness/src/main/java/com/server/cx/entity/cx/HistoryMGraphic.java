package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午2:04
 * FileName:HistoryMGraphic
 */
@Entity
@Table(name = "history_mgraphic")
@ToString(callSuper = true)
public class HistoryMGraphic extends AuditableStringEntity {
    private Date modifyTime;
    private Integer modeType; // 1--common 2-special user 3--Custom 4-- holiday 5-- status
    private String signature;
    private List<String> phoneNos;
    private String name;
    private Integer priority;
    private GraphicInfo graphicInfo;
    private UserInfo userInfo;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "graphic_info_id")
    public GraphicInfo getGraphicInfo() {
        return graphicInfo;
    }

    public void setGraphicInfo(GraphicInfo graphicInfo) {
        this.graphicInfo = graphicInfo;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @ElementCollection
    @CollectionTable(name = "history_mgraphic_phone_no", joinColumns = {@JoinColumn(name = "hmgraphic_id")})
    @Column(name = "phone_number")
    public List<String> getPhoneNos() {
        return phoneNos;
    }

    public void setPhoneNos(List<String> phoneNos) {
        this.phoneNos = phoneNos;
    }
}
