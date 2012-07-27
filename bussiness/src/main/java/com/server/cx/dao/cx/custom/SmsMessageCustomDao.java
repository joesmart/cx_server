package com.server.cx.dao.cx.custom;

import com.server.cx.exception.SystemException;

import java.util.List;

public interface SmsMessageCustomDao {
    public void batchInsertSmsMessage(final List<String> content, final List<String> mobiles, String userInfoPhoneNo)
            throws SystemException;

    public void updateSmsMessageSentStatus(final Long[] ids) throws SystemException;
}
