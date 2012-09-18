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
import com.server.cx.exception.MoneyNotEnoughException;
import com.server.cx.exception.SystemException;
import com.server.cx.util.StringUtil;

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
        cxCoinAccount = cxCoinAccountDao.findByImsi(imsi);
        if (cxCoinAccount == null) {
            throw new SystemException("用户未注册过酷币帐号");
        }
    }
    
    public void checkCXCoinAccountCorrect(String name, String password) {
        cxCoinAccount = cxCoinAccountDao.findByNameAndPassword(name, password);
        if(cxCoinAccount == null) {
            throw new SystemException("用户登录信息不对");
        }
    }
    
    public void checkUserUnRegisterCXCoinAccount(String imsi) {
        checkAndSetUserInfoExists(imsi);
        cxCoinAccount = cxCoinAccountDao.findByImsi(imsi);
        if (cxCoinAccount != null) {
            throw new SystemException("用户已经注册过酷币帐号");
        }
    }

    public void checkUserUnConsumeCXCoin(UserInfo userInfo) {
        CXCoinConsumeRecord cxCoinConsumeRecord = cxCoinConsumeRecordDao.findByUserInfo(userInfo);
        if (cxCoinConsumeRecord != null) {
            throw new SystemException("用户已经抢购过酷币");
        }
    }

    public void checkUserCXCoinEnough(Double coin, Double price) {
        if (price != null && coin < price) {
            throw new MoneyNotEnoughException("用户余额不足");
        }
    }

    public boolean hasRegisteredCXCoinAccount(String imsi) {
        cxCoinAccount = cxCoinAccountDao.findByImsi(imsi);
        if (cxCoinAccount != null) {
            return true;
        }
        return false;
    }
    
    public void checkEmailValid(String email) {
        StringUtil.checkEmailFormatValid(email);
    }
    
    public void checkEmailUnRegistered(String email) {
        cxCoinAccount = cxCoinAccountDao.findByName(email);
        if (cxCoinAccount != null) {
            throw new SystemException("邮箱已经被注册过");
        }
    }

}
