package com.server.cx.entity.cx;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import com.server.cx.dto.CXInfo;


@Entity
@Table(name = "user_favorites")
public class UserFavorites extends AuditableEntity {

  private UserInfo user;
  private String resourceId;
  private CXInfo cxInfo;

  @XmlTransient
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, optional = true)
  @JoinColumn(name = "user_id")
  public UserInfo getUser() {
    return user;
  }

  public void setUser(UserInfo user) {
    this.user = user;
  }

  @Column(length = 32)
  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  @Transient
  public CXInfo getCxInfo() {
    return cxInfo;
  }

  public void setCxInfo(CXInfo cxInfo) {
    this.cxInfo = cxInfo;
  }


}
