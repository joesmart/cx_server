/*
 * Copyright (c) 2011 CieNet, Ltd. Created on 2011-9-21
 */
package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity of VersionInfo, interact with table VersionInfo.
 */
@Entity
@Table(name = "versioninfo")
public class VersionInfo extends AuditableEntity {
  private String version;
  private String url;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


}
