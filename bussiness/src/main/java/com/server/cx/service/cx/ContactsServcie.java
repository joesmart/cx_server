package com.server.cx.service.cx;

import java.util.List;
import com.cl.cx.platform.dto.ContactPeopleInfoDTO;
import com.cl.cx.platform.dto.UploadContactDTO;
import com.server.cx.exception.SystemException;

public interface ContactsServcie {

    public UploadContactDTO   uploadContacts(List<ContactPeopleInfoDTO> contactPeopleInfos, String imsi) throws SystemException;

    public String retrieveContactUserCXInfo(String imsi) throws SystemException;

    public String copyContactUserCXInfo(String imsi, String cxInfoId) throws SystemException;
}
