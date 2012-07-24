package com.server.cx.service.cx;

import com.server.cx.exception.SystemException;

public interface SmsMessageService {

  public String inviteFriends(String imsi, String mobiles) throws SystemException;

}
