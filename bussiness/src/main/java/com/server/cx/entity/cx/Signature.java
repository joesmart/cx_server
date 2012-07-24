package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "signature")
public class Signature extends AuditableEntity {
  private String type;
  private int level;
  private String content;

  public Signature() {

  }

  public Signature(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  @Column(nullable = true, columnDefinition = "Integer default '-1'")
  public int getLevel() {
    return level;
  }

  @Column(nullable = true, columnDefinition = "Integer default '-1'")
  public String getType() {
    return type;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void setType(String type) {
    this.type = type;
  }

}
