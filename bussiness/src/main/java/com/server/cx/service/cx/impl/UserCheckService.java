package com.server.cx.service.cx.impl;

import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.CXServerBusinessException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午3:01
 * FileName:UserCheckService
 */
@Component
@Data
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
}
