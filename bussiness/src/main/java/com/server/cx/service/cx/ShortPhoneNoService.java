package com.server.cx.service.cx;

import com.server.cx.exception.SystemException;

public interface ShortPhoneNoService {

  public String addNewShorePhoneNo(String imsi, String shortPhoneNos) throws SystemException;

  public String retriveAllShortPhoneNos(String imsi) throws SystemException;

}
