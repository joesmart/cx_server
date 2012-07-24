package com.server.cx.xml;

import com.server.cx.entity.cx.Signature;
import com.server.cx.entity.cx.UserFavorites;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class Result {

  private String flag;
  // private List<UserCXInfo> userCXInfos;
  private List<UserFavorites> userFavorites;
  private Signature signature;
  private String version;
  private String content;
  private String userId;
  private String userCXInfoId;
  private String cxInfoId;
  private String forceUpdate;
  private String url;
  private UserSetting userSetting;
  private String userStatus;
  private String userStatusValidTime;
  private String beginTime;
  private String endTime;

  @XmlElement()
  public String getForceUpdate() {
    return forceUpdate;
  }

  public void setForceUpdate(String forceUpdate) {
    this.forceUpdate = forceUpdate;
  }

  @XmlElement()
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @XmlElement()
  public String getCxInfoId() {
    return cxInfoId;
  }

  public void setCxInfoId(String cxInfoId) {
    this.cxInfoId = cxInfoId;
  }

  @XmlElement()
  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  /*
   * @XmlElementWrapper(name="userCXInfos")
   * 
   * @XmlElement(name = "userCxinfo") public List<UserCXInfo> getUserCXInfos() { return userCXInfos;
   * }
   * 
   * 
   * public void setUserCXInfos(List<UserCXInfo> userCXInfos) { this.userCXInfos = userCXInfos; }
   */
  @XmlElement()
  public Signature getSignature() {
    return signature;
  }

  public void setSignature(Signature signature) {
    this.signature = signature;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @XmlElement()
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @XmlElement()
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @XmlElement()
  public String getUserCXInfoId() {
    return userCXInfoId;
  }

  public void setUserCXInfoId(String userCXInfoId) {
    this.userCXInfoId = userCXInfoId;
  }

  @XmlElementWrapper(name = "userFavoritesList")
  @XmlElement()
  public List<UserFavorites> getUserFavorites() {
    return userFavorites;
  }

  public void setUserFavorites(List<UserFavorites> userFavorites) {
    this.userFavorites = userFavorites;
  }

  @XmlElement()
  public UserSetting getUserSetting() {
    return userSetting;
  }

  public void setUserSetting(UserSetting userSetting) {
    this.userSetting = userSetting;
  }

  @XmlElement()
  public String getUserStatus() {
    return userStatus;
  }

  public void setUserStatus(String userStatus) {
    this.userStatus = userStatus;
  }

  @XmlElement()
  public String getUserStatusValidTime() {
    return userStatusValidTime;
  }

  public void setUserStatusValidTime(String userStatusValidTime) {
    this.userStatusValidTime = userStatusValidTime;
  }

  @XmlElement()
  public String getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

  @XmlElement()
  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }


}
