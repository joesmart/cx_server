package com.server.cx.util.business;

public enum UserCXInfoType {
  SYSTEM_DEFAULT("系统默认彩像", 1), USERSTATUS("用户状态", 2), USERCUSTOM("用户自定义", 3);

  private String name;
  private Integer value;

  UserCXInfoType(String name, Integer value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public Integer getValue() {
    return value;
  }
}
