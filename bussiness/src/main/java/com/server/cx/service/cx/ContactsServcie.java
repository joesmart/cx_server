package com.server.cx.service.cx;

import com.cl.cx.platform.dto.ContactInfoDTO;
import com.server.cx.exception.SystemException;

import java.util.List;

public interface ContactsServcie {

    public void uploadContacts(List<ContactInfoDTO> contactPeopleInfos, String imsi) throws SystemException;

    public String retrieveContactUserCXInfo(String imsi) throws SystemException;

}
