package com.server.cx.entity.cx;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.server.cx.entity.basic.AuditableEntity;
import com.server.cx.xml.adapter.DateXMLAdapter;
import com.server.cx.xml.adapter.SignatureXMLAdapter;

@XmlType(propOrder = {"startTime", "endTime", "phoneNo", "modeType", "signature", "name", "addTime", "timeStamp",
    "imsi", "cxInfo"})
@Entity
@Table(name = "usercxinfo")
public class UserCXInfo extends AuditableEntity {
  private Integer startTime;
  private Integer endTime;
  private int modeType; // -1--system,0--empty 1--common 2--time 3--number 4--number and time
  private Date timeStamp;
  private Date addTime;
  private String imsi; // 自己的imsi
  private String phoneNo; // 指定的号码
  private String name; // 用户自定义的彩像名称
  private UserInfo userInfo;
  private Integer type; // 用户设定彩像类型 1:系统默认彩像,2:系统状态彩像,3:用户设定彩像
  private Integer statusType; // 如果type 为状态用户设定彩像时,对应 各个状态标识. 1:做饭,2:坐飞机,3:忙碌,4:睡觉,5:开会,6:开车,7:工作
  @Transient
  private CXInfo cxInfo;
  private Signature signature;
  private boolean isCurrent;

  private List<CXInfo> cxInfos;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @XmlJavaTypeAdapter(SignatureXMLAdapter.class)
  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "signature_id")
  public Signature getSignature() {
    return signature;
  }

  public void setSignature(Signature signature) {
    this.signature = signature;
  }

  @Column(nullable = true, columnDefinition = "Integer default '-1'")
  public int getModeType() {
    return modeType;
  }

  // @Column(columnDefinition="Integer default '-1'")
  public Integer getStartTime() {
    return startTime;
  }

  public void setStartTime(Integer startTime) {
    this.startTime = startTime;
  }

  // @Column(columnDefinition="Integer default '-1'")
  public Integer getEndTime() {
    return endTime;
  }

  public void setEndTime(Integer endTime) {
    this.endTime = endTime;
  }

  public void setModeType(int modeType) {
    this.modeType = modeType;
  }

  @XmlTransient
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "user_id")
  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }

  @XmlElement()
  // @ManyToOne(cascade = CascadeType.PERSIST)
  // @JoinColumn(name = "cxId")
  @Transient
  public CXInfo getCxInfo() {
    return cxInfo;
  }

  public void setCxInfo(CXInfo cxInfo) {
    this.cxInfo = cxInfo;
  }

  @XmlTransient
  @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
  @JoinTable(name = "usercxinfo_cxinfo", joinColumns = {@JoinColumn(name = "usercxinfo_id")}, inverseJoinColumns = {@JoinColumn(name = "cxinfo_id")})
  public List<CXInfo> getCxInfos() {
    return cxInfos;
  }

  public void setCxInfos(List<CXInfo> cxInfos) {
    this.cxInfos = cxInfos;
  }

  @XmlJavaTypeAdapter(DateXMLAdapter.class)
  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  @XmlJavaTypeAdapter(DateXMLAdapter.class)
  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }

  @XmlTransient
  @Column(nullable = true, columnDefinition = "Integer default '-1'")
  public boolean isCurrent() {
    return isCurrent;
  }

  public void setCurrent(boolean isCurrent) {
    this.isCurrent = isCurrent;
  }

  public String getImsi() {
    return imsi;
  }

  public void setImsi(String imsi) {
    this.imsi = imsi;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  @XmlTransient
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  @XmlTransient
  public Integer getStatusType() {
    return statusType;
  }

  public void setStatusType(Integer statusType) {
    this.statusType = statusType;
  }

  @Transient
  public CXInfo getRandomUserCXInfo() {
    List<CXInfo> cxInfos = getCxInfos();
    int size = cxInfos.size();
    int randomIndex = new Random().nextInt(size);
    return cxInfos.get(randomIndex);
  }


}
