package com.server.cx.service.cx;

import java.util.List;
import com.cl.cx.platform.dto.ContactInfoDTO;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.exception.SystemException;

public interface ContactsServcie {

    public void uploadContacts(List<ContactInfoDTO> contactPeopleInfos, String imsi) throws SystemException;

    public String retrieveContactUserCXInfo(String imsi) throws SystemException;

    public abstract List<Contacts> queryCXAppConactsByImsi(String imsi) throws SystemException;

}
