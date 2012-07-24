package com.server.cx.util.business;

public enum ModeType {
  COMMON_MODE("一般模式", 1), TIMELIMIT_MODE("时间限制模式", 2), SPECIALPHONE_MODE("时间模式", 3);
  private String name;
  private Integer value;

  ModeType(String name, Integer value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return this.name;
  }

  public Integer getValue() {
    return this.value;
  }
}
