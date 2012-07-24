package com.server.cx.service.cx;

import com.server.cx.dto.UserCXInfo;
import com.server.cx.exception.SystemException;

public interface UserStatusManagerService {

  public String addNewUserStatus(String imsi, String type, String signature, String validTime) throws SystemException;

  public String retriveAllStatusMGraphic(String imsi) throws SystemException;

  public String getCurrentUserStatus(String imsi) throws SystemException;

  public String deletCurrentUserStatus(String imsi, String userCXInfoId, String cxInfoId) throws SystemException;

  public UserCXInfo getCurrentValidStatusUserCXInfo() throws SystemException;

  public void isUserExists(String imsi) throws SystemException;

}
