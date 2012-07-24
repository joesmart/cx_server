package com.server.cx.entity.cx;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usercxinfo_modelcount")
public class UserCXInfoModeCount extends AuditableEntity {

  private Integer commonMode;
  private Integer specialPhoneMode;
  private Integer timeSpanMode;
  private Integer statusMode;
  private UserInfo user;

  public UserCXInfoModeCount() {
    this.commonMode = 0;
    this.specialPhoneMode = 0;
    this.timeSpanMode = 0;
    this.statusMode = 0;
  }

  @Column(nullable = true, columnDefinition = "Integer default '0'")
  public Integer getCommonMode() {
    return commonMode;
  }

  public void setCommonMode(Integer commonMode) {
    this.commonMode = commonMode;
  }

  @Column(nullable = true, columnDefinition = "Integer default '0'")
  public Integer getSpecialPhoneMode() {
    return specialPhoneMode;
  }

  public void setSpecialPhoneMode(Integer specialPhoneMode) {
    this.specialPhoneMode = specialPhoneMode;
  }

  @Column(nullable = true, columnDefinition = "Integer default '0'")
  public Integer getTimeSpanMode() {
    return timeSpanMode;
  }

  @Column(nullable = true, columnDefinition = "Integer default '0'")
  public void setTimeSpanMode(Integer timeSpanMode) {
    this.timeSpanMode = timeSpanMode;
  }

  @Column(nullable = true, columnDefinition = "Integer default '0'")
  public Integer getStatusMode() {
    return statusMode;
  }

  public void setStatusMode(Integer statusMode) {
    this.statusMode = statusMode;
  }

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
  @JoinColumn(name = "user_id")
  public UserInfo getUser() {
    return user;
  }

  public void setUser(UserInfo user) {
    this.user = user;
  }
}
