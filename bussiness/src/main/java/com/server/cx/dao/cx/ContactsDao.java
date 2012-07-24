package com.server.cx.dao.cx;

import com.server.cx.entity.cx.Contacts;
import com.server.cx.exception.SystemException;

import java.util.List;

public interface ContactsDao {
  public void batchInsertContacts(final List<Contacts> contacts) throws SystemException;
  
  public List<Contacts> getContactsByUserId(Long userId) throws SystemException;
  
  public List<String> retrieveExistsMobiles(Long userId, List<String> phoneNos) throws SystemException;
  
  public List<Contacts> getContactsByUserIdAndSelfUserInfoNotNull(Long userId) throws SystemException;
}
