package com.server.cx.dto;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "startHour", "endHour", "phoneNo", "modeType", "type", "imsi", "auditPass", "cxInfo"})
public class UserCXInfo {
    private String id;
    private Integer startHour;
    private Integer endHour;
    private String phoneNo;
    private String imsi;
    private Integer modeType; // 1--common 2--time 3--number 4--number and time
    private Integer type; // 用户设定彩像类型 1:系统默认彩像,2:系统状态彩像,3:用户设定彩像
    private boolean auditPass;
    private CXInfo cxInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public CXInfo getCxInfo() {
        return cxInfo;
    }

    public void setCxInfo(CXInfo cxInfo) {
        this.cxInfo = cxInfo;
    }

    public boolean isAuditPass() {
        return auditPass;
    }

    public void setAuditPass(boolean auditPass) {
        this.auditPass = auditPass;
    }
}
