package com.server.cx.service.cx;

import com.server.cx.dto.ContactPeopleInfo;
import com.server.cx.exception.SystemException;

import java.util.List;

public interface ContactsServcie {

  public String uploadContacts(List<ContactPeopleInfo> contactPeopleInfos, String imsi) throws SystemException;
  
  public String retrieveContactUserCXInfo(String imsi) throws SystemException;
  
  public String copyContactUserCXInfo(String imsi, String cxInfoId)throws SystemException;
}
