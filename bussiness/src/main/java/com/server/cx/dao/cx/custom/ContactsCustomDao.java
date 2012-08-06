package com.server.cx.dao.cx.custom;

import com.server.cx.entity.cx.Contacts;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;

import java.util.List;

public interface ContactsCustomDao {
    public void batchInsertContacts(final List<Contacts> contacts) throws SystemException;

    public List<Contacts> getContactsByUserId(String userId) throws SystemException;

    public List<String> retrieveExistsMobiles(String userId, List<String> phoneNos) throws SystemException;

    public List<Contacts> getContactsByUserIdAndSelfUserInfoNotNull(String userId) throws SystemException;
    
    public void updateContactsSelfUserInfo(UserInfo userinfo) throws SystemException;
}
