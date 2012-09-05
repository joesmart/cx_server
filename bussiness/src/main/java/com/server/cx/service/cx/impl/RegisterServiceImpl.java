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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * implement of RegisterService interface. Briefly describe what this class does.
 */
@Service("registerService")
@Transactional(readOnly = true)
@Scope(value = "request")
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserInfoDao userInfoDao;
    private UserInfo userinfo;

    @Override
    @Transactional(readOnly = false)
    public OperationDescription register(RegisterDTO registerDTO, String phoneNo) {
        userinfo = userInfoDao.getUserInfoByImsi(registerDTO.getImsi());
        OperationDescription operationDescription;
        if (userinfo == null) {
            addNewUserInfo(registerDTO);
            operationDescription = ObjectFactory.buildOperationDescription(HttpServletResponse.SC_CREATED, "register");
        } else {
            operationDescription = ObjectFactory.buildErrorOperationDescription(HttpServletResponse.SC_CONFLICT,
                "register", "registered");
        }
        return operationDescription;
    }

    private void addNewUserInfo(RegisterDTO registerDTO) {
        userinfo = new UserInfo();
        userinfo.setImsi(registerDTO.getImsi());
        userinfo.setPhoneNo(dealWithPhoneNo(registerDTO.getImsi()));
        userinfo.setUserAgent(registerDTO.getUserAgent());
        userinfo.setDeviceId(registerDTO.getDeviceId());
        //TODO 这里默认是50个,但是每个item价格设定的较高，先用100
        userinfo.setTotleMoney(100D);
        userInfoDao.save(userinfo);
    }

    @Override
    public OperationDescription update(RegisterDTO registerDTO) {
        userinfo = userInfoDao.getUserInfoByImsi(registerDTO.getImsi());
        OperationDescription operationDescription;
        if (userinfo != null) {
            updateUserInfo(registerDTO);
            operationDescription = ObjectFactory.buildOperationDescription(HttpServletResponse.SC_CREATED,
                "updateUserInfo");
        } else {
            addNewUserInfo(registerDTO);
            operationDescription = ObjectFactory.buildOperationDescription(HttpServletResponse.SC_CREATED, "register");
        }
        return operationDescription;
    }


    private void updateUserInfo(RegisterDTO registerDTO) {
        userinfo.setPhoneNo(registerDTO.getPhoneNo());
        userInfoDao.saveAndFlush(userinfo);
    }

    private String dealWithPhoneNo(String imsi) {
        String phoneNo = null;
        if (imsi == null || "".equals(imsi)) {
            phoneNo = String.valueOf(UUID.randomUUID().getMostSignificantBits());
        }
        return phoneNo;
    }
}
