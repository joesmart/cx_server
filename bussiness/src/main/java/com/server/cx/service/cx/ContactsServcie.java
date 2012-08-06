package com.server.cx.service.cx;

import java.util.List;
import com.cl.cx.platform.dto.ContactInfoDTO;
import com.server.cx.exception.SystemException;

public interface ContactsServcie {

    public void uploadContacts(List<ContactInfoDTO> contactPeopleInfos, String imsi) throws SystemException;

    public String retrieveContactUserCXInfo(String imsi) throws SystemException;

    public String copyContactUserCXInfo(String imsi, String cxInfoId) throws SystemException;
}
