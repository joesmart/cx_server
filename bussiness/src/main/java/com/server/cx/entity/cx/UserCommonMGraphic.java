package com.server.cx.entity.cx;


import com.server.cx.dto.UserCXInfo;
import com.server.cx.entity.basic.AuditableStringEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "user_common_mgrpahic")
public class UserCommonMGraphic extends AuditableStringEntity {
    private Date validDate;  //keep one day
    private String phoneNo;
    private Date modifyTime;
    private Integer modeType; // 1--common 2--special user
    private UserInfo userInfo;
    private String signature;
    private String name;
    private Boolean isAuditPass;
    private GraphicInfo graphicInfo;


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



    /**
     * 转换成UserCXInfo.
     *
     * @return
     */
    public UserCXInfo convertMGraphicStoreModeToUserCXInfo() {
        return  null;
    }
}
