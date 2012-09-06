/*
 * Copyright (c) 2011 CieNet, Ltd. Created on 2011-9-21
 */
package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import javax.persistence.*;
import java.util.List;

/**
 * entiry of UserInfo. Briefly describe what this class does.
 */

@Entity
@Table(name = "userinfo")
@ToString(callSuper = true)
public class UserInfo extends AuditableStringEntity {

    private String phoneNo;
    private String imsi;
    private List<UserFavorites> userFavorites;

    private String deviceId;

    private String userAgent;

    private Boolean forceSMS;

    public UserInfo() {
    }

    public UserInfo(final String imsi) {
        super();
        this.imsi = imsi;
    }

    public UserInfo(final String phoneNo, final String imsi) {
        super();
        this.phoneNo = phoneNo;
        this.imsi = imsi;
    }

    @Column(length = 60)
    public String getDeviceId() {
        return deviceId;
    }

    @Index(name = "user_imsi")
    @Column(length = 40, unique = true)
    public String getImsi() {
        return imsi;
    }

    @Index(name = "user_phone_no")
    @Column(length = 20, unique = true)
    public String getPhoneNo() {
        return phoneNo;
    }

    @Column(length = 60)
    public String getUserAgent() {
        return userAgent;
    }

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "user", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    public List<UserFavorites> getUserFavorites() {
        return userFavorites;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setUserFavorites(List<UserFavorites> userFavorites) {
        this.userFavorites = userFavorites;
    }

    public Boolean getForceSMS() {
        return forceSMS;
    }

    public void setForceSMS(Boolean forceSMS) {
        this.forceSMS = forceSMS;
    }
}
