/*
 * Copyright (c) 2011 CieNet, Ltd. Created on 2011-9-21
 */
package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.RegisterDTO;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.service.cx.RegisterService;
import com.server.cx.util.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * implement of RegisterService interface. Briefly describe what this class does.
 */
@Service("registerService")
@Transactional(readOnly=true)
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Override
    @Transactional(readOnly=false)
    public OperationDescription register(RegisterDTO registerDTO, String phoneNo) {
        UserInfo userinfo = userInfoDao.getUserInfoByImsi(registerDTO.getImsi());
        OperationDescription operationDescription ;
        if (userinfo == null) {
            userinfo = new UserInfo();
            userinfo.setImsi(registerDTO.getImsi());
            phoneNo = dealWithPhoneNo(registerDTO.getImsi(), phoneNo);
            userinfo.setPhoneNo(phoneNo);
            userinfo.setUserAgent(registerDTO.getUserAgent());
            userinfo.setDeviceId(registerDTO.getDeviceId());
            userInfoDao.save(userinfo);
            operationDescription = ObjectFactory.buildOperationDescription(HttpServletResponse.SC_CREATED,
                "register", "success");
        } else {
            operationDescription = ObjectFactory.buildErrorOperationDescription(HttpServletResponse.SC_CONFLICT,
                "register", "registered");
        }
        return operationDescription;
    }

    private String dealWithPhoneNo(String imsi, String phoneNo) {
        if (phoneNo == null || "".equals(phoneNo)) {
            phoneNo = String.valueOf(UUID.randomUUID().getMostSignificantBits());
        } else if ("460025581509188".equals(imsi)) {
            phoneNo = "18358163576";
        } else if ("460025581509189".equals(imsi)) {
            phoneNo = "18358163577";
        } else if ("460025581509187".equals(imsi)) {
            phoneNo = "18358163575";
        }
        return phoneNo;
    }
}
