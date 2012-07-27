package com.server.cx.entity.cx;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.server.cx.dto.UserCXInfo;
import com.server.cx.dto.CXInfo;
import com.server.cx.entity.basic.AuditableStringEntity;


@Entity
@Table(name = "mgraphic_storemode")
public class MGraphicStoreMode extends AuditableStringEntity {
    private Integer startHour;
    private Integer endHour;
    private String phoneNo;
    private Date modifyTime;
    private Double price;
    private Integer modeType; // 1--common 2--time 3--number 4--number and time 5--status
    private Integer type; // 用户设定彩像类型 1:系统默认彩像,2:系统状态彩像,3:用户设定彩像
    private String resourceId;
    private UserInfo userInfo;
    private String signature;
    private String graphicId;
    private boolean isAuditPass = false;
    private String name;
    private String fileType;

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    @Column(length = 4000)
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getModeType() {
        return modeType;
    }

    public void setModeType(Integer modeType) {
        this.modeType = modeType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(length = 32)
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getGraphicId() {
        return graphicId;
    }

    public void setGraphicId(String graphicId) {
        this.graphicId = graphicId;
    }

    public boolean isAuditPass() {
        return isAuditPass;
    }

    public void setAuditPass(boolean isAuditPass) {
        this.isAuditPass = isAuditPass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 转换成UserCXInfo.
     *
     * @return
     */
    public UserCXInfo convertMGraphicStoreModeToUserCXInfo() {
        UserCXInfo userCXInfo = new UserCXInfo();
        userCXInfo.setId(this.getId());
        userCXInfo.setStartHour(this.getStartHour());
        userCXInfo.setEndHour(this.getEndHour());
        userCXInfo.setModeType(this.getModeType());
        userCXInfo.setPhoneNo(this.getPhoneNo());
        userCXInfo.setType(this.getType());
        userCXInfo.setModeType(this.getModeType());
        userCXInfo.setAuditPass(this.isAuditPass);
        CXInfo cxInfo = new CXInfo();
        cxInfo.setId(this.getResourceId());
        cxInfo.setResourceId(this.getGraphicId());
        cxInfo.setName(this.getName());
        cxInfo.setSignature(this.getSignature());
        cxInfo.setPrice(this.getPrice());
        cxInfo.setFileType(this.fileType);
        userCXInfo.setCxInfo(cxInfo);
        return userCXInfo;
    }
}
