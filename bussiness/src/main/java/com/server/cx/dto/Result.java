package com.server.cx.dto;

import com.server.cx.dto.adapters.XmlGenericMapAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
import java.util.Map;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class Result {
  private String imsi;
  private String flag;
  private String content;
  private List<CXInfo> cxinfos;
  private List<UserCXInfo> userCXInfos;
  private Map<String, CXInfo> cxinfoMap;
  private List<ContactPeopleInfo> contactPeopleInfos;
  
  @XmlElementWrapper(name = "cxInfos")
  @XmlElement(name = "cxInfo")
  public List<CXInfo> getCxinfos() {
    return cxinfos;
  }

  public void setCxinfos(List<CXInfo> cxinfos) {
    this.cxinfos = cxinfos;
  }

  @XmlElementWrapper(name = "userCXInfos")
  @XmlElement(name = "userCxinfo")
  public List<UserCXInfo> getUserCXInfos() {
    return userCXInfos;
  }

  public void setUserCXInfos(List<UserCXInfo> userCXInfos) {
    this.userCXInfos = userCXInfos;
  }

  @XmlElement
  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  @XmlElement
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @XmlElement
  @XmlJavaTypeAdapter(XmlGenericMapAdapter.class)
  public Map<String, CXInfo> getCxinfoMap() {
    return cxinfoMap;
  }

  public void setCxinfoMap(Map<String, CXInfo> cxinfoMap) {
    this.cxinfoMap = cxinfoMap;
  }
  
  @XmlElement(name = "imsi")
  public String getImsi() {
    return imsi;
  }

  public void setImsi(String imsi) {
    this.imsi = imsi;
  }
  @XmlElementWrapper(name = "contactPeopleInfos")
  @XmlElement(name = "contactPeopleInfo")
  public List<ContactPeopleInfo> getContactPeopleInfos() {
    return contactPeopleInfos;
  }

  public void setContactPeopleInfos(List<ContactPeopleInfo> contactPeopleInfos) {
    this.contactPeopleInfos = contactPeopleInfos;
  }
}
