/*
 * Copyright (c) 2011 CieNet, Ltd. Created on 2011-9-21
 */
package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableEntity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * entiry of UserInfo. Briefly describe what this class does.
 */

@Entity
@Table(name = "userinfo", uniqueConstraints = @UniqueConstraint(columnNames = {"phoneNo"}))
public class UserInfo extends AuditableEntity {

    @Column(unique = true, nullable = false)
    private String phoneNo;
    private String imsi;
    private Integer cxService;
    private List<UserFavorites> userFavorites;
    private List<ShortPhoneNo> shortPhoneNoList;

    public UserInfo() {
    }

    public UserInfo(String phoneNo, String imsi, int cxService, String timeStamp) {
        super();
        this.phoneNo = phoneNo;
        this.imsi = imsi;
        this.cxService = cxService;
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

    public UserInfo(final String phoneNo, final String imsi, final int cxService) {
        super();
        this.phoneNo = phoneNo;
        this.imsi = imsi;
        this.cxService = cxService;
    }

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "user", fetch = FetchType.LAZY)
    public List<UserFavorites> getUserFavorites() {
        return userFavorites;
    }

    public void setUserFavorites(List<UserFavorites> userFavorites) {
        this.userFavorites = userFavorites;
    }

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "user", fetch = FetchType.LAZY)
    // @IndexColumn(name="id")
    @OrderBy("id")
    public List<ShortPhoneNo> getShortPhoneNoList() {
        return shortPhoneNoList;
    }

    public void setShortPhoneNoList(List<ShortPhoneNo> shortPhoneNoList) {
        this.shortPhoneNoList = shortPhoneNoList;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public Integer getCxService() {
        return cxService;
    }

    public void setCxService(Integer cxService) {
        this.cxService = cxService;
    }
}
