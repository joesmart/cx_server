package com.server.cx.dao.cx;

import com.server.cx.exception.SystemException;

public interface ShortPhoneNoDao {

    public void deleteAllOldShortPhonNos(Long userId) throws SystemException;
}
