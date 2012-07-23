/*
 * Copyright (c) 2011 CieNet, Ltd.
 * Created on 2011-9-21
 */
package com.server.cx.entity.cx;

import java.util.List;

import javax.persistence.*;

/**
 * entiry of UserInfo. Briefly describe what this class does.
 */

@Entity
@Table(name = "userinfo",uniqueConstraints=@UniqueConstraint(columnNames={"phoneNo"}))
public class UserInfo extends AuditableEntity{

    @Column(unique=true,nullable=false)
    private String phoneNo;
    private String imsi;
    private Integer cxService;
    private List<UserFavorites> userFavorites;
    private List<ShortPhoneNo> shortPhoneNoList;
    private UserCXInfoModeCount modeCount;
    
    private UserStatus userStatus;
    
    private StatusPackage statusPackage;

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
    @OneToMany(cascade={CascadeType.REFRESH,CascadeType.PERSIST,
                    CascadeType.MERGE,CascadeType.REMOVE},mappedBy="user",fetch=FetchType.LAZY)
    public List<UserFavorites> getUserFavorites() {
        return userFavorites;
    }
    
    public void setUserFavorites(List<UserFavorites> userFavorites) {
        this.userFavorites = userFavorites;
    }
    @OneToMany(cascade={CascadeType.REFRESH,CascadeType.PERSIST,
                    CascadeType.MERGE,CascadeType.REMOVE},mappedBy="user",fetch=FetchType.LAZY)
    //@IndexColumn(name="id")
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

    @OneToOne(optional=true,cascade = CascadeType.ALL, mappedBy="user",fetch=FetchType.LAZY)
    public UserCXInfoModeCount getModeCount() {
        return modeCount;
    }

    public void setModeCount(UserCXInfoModeCount modeCount) {
        this.modeCount = modeCount;
    }

    @OneToOne(optional=true,cascade = CascadeType.ALL, mappedBy="user",fetch=FetchType.LAZY)
    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},optional=false,fetch=FetchType.LAZY)
    @JoinColumn(name="statuspackage_id")
    public StatusPackage getStatusPackage() {
        return statusPackage;
    }
    
    public void setStatusPackage(StatusPackage statusPackage) {
        this.statusPackage = statusPackage;
    }
}
