package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_catacts")
public class Contacts extends AuditableEntity {

  private String name;
  private String phoneNo;
  private UserInfo userInfo;
  private UserInfo selfUserInfo;
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getPhoneNo() {
    return phoneNo;
  }
  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "user_id")
  public UserInfo getUserInfo() {
    return userInfo;
  }
  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "self_user_id")
  public UserInfo getSelfUserInfo() {
    return selfUserInfo;
  }
  public void setSelfUserInfo(UserInfo selfUserInfo) {
    this.selfUserInfo = selfUserInfo;
  }
  
}
