package com.server.cx.entity.basic;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class StringTypeBaseEntity implements StringTypeIdentifiable {
  private String id;

  // @Id
  // @GeneratedValue(strategy = GenerationType.AUTO,generator="system-uuid")
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  @Column(length = 32)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
