package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sms_message")
public class SmsMessage extends AuditableEntity {

  private String sms;
  private String fromMobileNo;
  private String toMobileNo;
  private Boolean isSend;

  public String getSms() {
    return sms;
  }

  public void setSms(String sms) {
    this.sms = sms;
  }

  public String getFromMobileNo() {
    return fromMobileNo;
  }

  public void setFromMobileNo(String fromMobileNo) {
    this.fromMobileNo = fromMobileNo;
  }

  public String getToMobileNo() {
    return toMobileNo;
  }

  public void setToMobileNo(String toMobileNo) {
    this.toMobileNo = toMobileNo;
  }

  public Boolean getIsSend() {
    return isSend;
  }

  public void setIsSend(Boolean isSend) {
    this.isSend = isSend;
  }
}
