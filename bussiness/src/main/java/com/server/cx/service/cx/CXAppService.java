package com.server.cx.service.cx;

import java.util.List;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.exception.SystemException;

public interface CXAppService {
    public List<Contacts> queryCXAppUserByImsi(String imsi) throws SystemException;

}
