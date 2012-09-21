package com.server.cx.service.cx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.server.cx.dao.cx.CXCoinAccountDao;
import com.server.cx.dao.cx.CXCoinConsumeRecordDao;
import com.server.cx.dao.cx.CXCoinNotfiyDataDao;
import com.server.cx.dao.cx.UserCXCoinNotifyDataDao;
import com.server.cx.entity.cx.CXCoinAccount;
import com.server.cx.entity.cx.CXCoinConsumeRecord;
import com.server.cx.entity.cx.CXCoinNotfiyData;
import com.server.cx.entity.cx.UserCXCoinNotifyData;
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
    protected CXCoinNotfiyDataDao cxCoinNotfiyDataDao;
    
    protected CXCoinNotfiyData cxCoinNotfiyData;
    
    @Autowired
    protected UserCXCoinNotifyDataDao userCXCoinNotifyDataDao;
    
    protected UserCXCoinNotifyData userCXCoinNotifyData;

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
    
    public void checkCXCoinAccountNameExist(String name) {
        cxCoinAccount = cxCoinAccountDao.findByName(name);
        if(cxCoinAccount == null) {
            throw new SystemException("无法找到该帐号");
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
    
    public void checkValidOutTradeNoExists(String outTradeNo) {
        cxCoinNotfiyData = cxCoinNotfiyDataDao.findByOutTradeNo(outTradeNo);
        if(cxCoinNotfiyData == null) {
            throw new SystemException("无法找到该订单号");
        }
        if(Boolean.TRUE.equals(cxCoinNotfiyData.getStatus())) {
            throw new SystemException("该订单已经被处理");
        }
        
    }
    
    public void checkUserValidOutTradeNoExists(String outTradeNo) {
        userCXCoinNotifyData = userCXCoinNotifyDataDao.findByOutTradeNo(outTradeNo);
        if(userCXCoinNotifyData == null) {
            throw new SystemException("无法找到该订单号");
        }
        if(Boolean.TRUE.equals(userCXCoinNotifyData.getStatus())) {
            throw new SystemException("该订单已经被处理");
        }
    }

}
