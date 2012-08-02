package com.server.cx.service.cx;

import java.util.List;
import com.server.cx.dto.ContactPeopleInfo;
import com.server.cx.dto.UploadContactDTO;
import com.server.cx.exception.SystemException;

public interface ContactsServcie {

    public UploadContactDTO   uploadContacts(List<ContactPeopleInfo> contactPeopleInfos, String imsi) throws SystemException;

    public String retrieveContactUserCXInfo(String imsi) throws SystemException;

    public String copyContactUserCXInfo(String imsi, String cxInfoId) throws SystemException;
}
