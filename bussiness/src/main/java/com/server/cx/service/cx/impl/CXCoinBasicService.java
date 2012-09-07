package com.server.cx.service.cx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.server.cx.dao.cx.CXCoinAccountDao;
import com.server.cx.dao.cx.CXCoinConsumeRecordDao;
import com.server.cx.entity.cx.CXCoinAccount;
import com.server.cx.entity.cx.CXCoinConsumeRecord;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;

@Component
@Scope("request")
@Transactional(readOnly = true)
public class CXCoinBasicService extends UserCheckService {
    @Autowired
    protected CXCoinAccountDao cxCoinAccountDao;
    
    protected CXCoinAccount cxCoinAccount;
    
    @Autowired
    protected CXCoinConsumeRecordDao cxCoinConsumeRecordDao;
    
    public void checkUserRegisterCXCoinAccount(String imsi) {
        checkAndSetUserInfoExists(imsi);
        cxCoinAccount = cxCoinAccountDao.findByUserInfo(userInfo);
        if(cxCoinAccount == null) {
            throw new SystemException("用户未注册过酷币帐号");
        }
    }
    
    public void checkUserUnRegisterCXCoinAccount(String imsi) {
        checkAndSetUserInfoExists(imsi);
        cxCoinAccount = cxCoinAccountDao.findByUserInfo(userInfo);
        if(cxCoinAccount != null) {
            throw new SystemException("用户已经注册过酷币帐号");
        }
    }
    
    public void checkUserRegisterCXCoinAccount(UserInfo userInfo) {
        if(userInfo != null) {
            cxCoinAccount = cxCoinAccountDao.findByUserInfo(userInfo);
            if(cxCoinAccount == null) {
                throw new SystemException("用户未注册过酷币帐号");
            }
        } else {
            throw new SystemException("用户不存在");
        }
    }
    
    public void checkUserUnConsumeCXCoin(UserInfo userInfo) {
        CXCoinConsumeRecord cxCoinConsumeRecord = cxCoinConsumeRecordDao.findByUserInfo(userInfo);
        if(cxCoinConsumeRecord != null) {
            throw new SystemException("用户已经抢购过酷币");
        }
    }
    
}
