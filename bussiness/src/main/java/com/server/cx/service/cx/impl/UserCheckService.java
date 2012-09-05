package com.server.cx.service.cx.impl;

import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.CXServerBusinessException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午3:01
 * FileName:UserCheckService
 */
@Component
@Data
@Transactional
@Scope("request")
public class UserCheckService {
    @Autowired
    private UserInfoDao userInfoDao;
    protected UserInfo userInfo;

    public void checkAndSetUserInfoExists(String imsi) throws CXServerBusinessException{
        userInfo = userInfoDao.findByImsi(imsi);
        if(userInfo == null){
            throw new CXServerBusinessException("用户不存在");
        }
    }

    public List<String> getAlreadyRegisteredMobiles(List<String> mobiles){
        return  userInfoDao.getHasRegisteredPhoneNos(mobiles);
    }
}
