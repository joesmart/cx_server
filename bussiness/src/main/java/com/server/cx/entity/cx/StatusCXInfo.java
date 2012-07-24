package com.server.cx.entity.cx;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "status_cxinfo")
public class StatusCXInfo extends AuditableEntity {

  @Enumerated(EnumType.ORDINAL)
  private Integer statusType;
  private CXInfo cxinfo;
  private StatusPackage statuspackage;

  public Integer getStatusType() {
    return statusType;
  }

  public void setStatusType(Integer statusType) {
    this.statusType = statusType;
  }

  @ManyToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "cx_id")
  public CXInfo getCxinfo() {
    return cxinfo;
  }

  public void setCxinfo(CXInfo cxinfo) {
    this.cxinfo = cxinfo;
  }

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, optional = true)
  @JoinColumn(name = "package_id")
  public StatusPackage getStatuspackage() {
    return statuspackage;
  }

  public void setStatuspackage(StatusPackage statuspackage) {
    this.statuspackage = statuspackage;
  }


}
