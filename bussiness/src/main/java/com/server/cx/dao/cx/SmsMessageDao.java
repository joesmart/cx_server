package com.server.cx.dao.cx;

import com.server.cx.exception.SystemException;

import java.util.List;

public interface SmsMessageDao {
  public void batchInsertSmsMessage(final List<String> content, final List<String> mobiles, String userInfoPhoneNo)
      throws SystemException;

  public void updateSmsMessageSentStatus(final Long[] ids) throws SystemException;
}
