/*
 * Copyright (c) 2011 CieNet, Ltd. Created on 2011-9-21
 */
package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * entiry of UserInfo. Briefly describe what this class does.
 */

@Entity
@Table(name = "userinfo", uniqueConstraints = @UniqueConstraint(columnNames = {"phoneNo"}))
public class UserInfo extends AuditableStringEntity {

    @Column(unique = true, nullable = false)
    private String phoneNo;
    @Column(unique = true, nullable = false)
    private String imsi;
    private List<UserFavorites> userFavorites;

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

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "user", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    public List<UserFavorites> getUserFavorites() {
        return userFavorites;
    }

    public void setUserFavorites(List<UserFavorites> userFavorites) {
        this.userFavorites = userFavorites;
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
}
