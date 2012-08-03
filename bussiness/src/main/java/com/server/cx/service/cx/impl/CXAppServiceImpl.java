package com.server.cx.service.cx.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.server.cx.dao.cx.ContactsDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.CXAppService;
import com.server.cx.util.business.ValidationUtil;

@Component
@Transactional(readOnly = true)
public class CXAppServiceImpl implements CXAppService {
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    private ContactsDao contactsDao;

    @Override
    public List<Contacts> queryCXAppUserByImsi(String imsi) throws SystemException {
        ValidationUtil.checkParametersNotNull(imsi);
        UserInfo userInfo = userInfoDao.findByImsi(imsi);
        List<Contacts> contacts = (List<Contacts>) contactsDao.getContactsByUserIdAndSelfUserInfoNotNull(userInfo.getId());
        return contacts;
    }

}
