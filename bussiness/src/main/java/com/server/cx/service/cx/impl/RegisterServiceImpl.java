/*
 * Copyright (c) 2011 CieNet, Ltd. Created on 2011-9-21
 */
package com.server.cx.service.cx.impl;

import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.RegisterDTO;
import com.cl.cx.platform.dto.RegisterOperationDescription;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.service.cx.RegisterService;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.StringUtil;

/**
 * implement of RegisterService interface. Briefly describe what this class does.
 */
@Service("registerService")
@Transactional(readOnly = true)
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    @Transactional(readOnly = false)
    public RegisterOperationDescription register(RegisterDTO registerDTO, String phoneNo) {
        UserInfo userinfo = userInfoDao.getUserInfoByImsi(registerDTO.getImsi());
        RegisterOperationDescription operationDescription;
        if (userinfo == null) {
            userinfo = new UserInfo();
            userinfo.setImsi(registerDTO.getImsi());
            userinfo.setPhoneNo(dealWithPhoneNo(registerDTO.getImsi()));
            userinfo.setUserAgent(registerDTO.getUserAgent());
            userinfo.setDeviceId(registerDTO.getDeviceId());
            userinfo.setTotleMoney(50D);
            userinfo.setForceSMS(false);
            userInfoDao.save(userinfo);
            operationDescription = ObjectFactory.buildRegisterOperationDescription(HttpServletResponse.SC_CREATED,
                "register");
            operationDescription.setForceSMS(false);
            operationDescription.setSendSMS(true);
        } else {
            operationDescription = ObjectFactory.buildErrorRegisterOperationDescription(
                HttpServletResponse.SC_CONFLICT, "register", "registered");
            if (isPhoneNoValidate(userinfo.getPhoneNo())) {
                operationDescription.setForceSMS(false);
                operationDescription.setSendSMS(false);
            } else {
                operationDescription.setForceSMS(getForceFlag(userinfo.getForceSMS()));
                operationDescription.setSendSMS(true);
            }
        }
        return operationDescription;
    }

    private Boolean getForceFlag(Boolean forceSMS) {
        if (forceSMS == null || forceSMS == Boolean.FALSE) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional(readOnly = false)
    public OperationDescription update(RegisterDTO registerDTO) {
        UserInfo userinfo = userInfoDao.getUserInfoByImsi(registerDTO.getImsi());
        OperationDescription operationDescription;
        if (userinfo != null) {
            userinfo.setPhoneNo(registerDTO.getPhoneNo());
            userInfoDao.saveAndFlush(userinfo);
            operationDescription = ObjectFactory.buildOperationDescription(HttpServletResponse.SC_CREATED,
                "updateUserInfo");
        } else {
            operationDescription = ObjectFactory.buildErrorOperationDescription(HttpServletResponse.SC_CONFLICT,
                "updateUserInfo", "failed");
        }
        return operationDescription;
    }

    private String dealWithPhoneNo(String imsi) {
        String phoneNo = null;
        if (imsi == null || "".equals(imsi)) {
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

    private boolean isPhoneNoValidate(String phoneNo) {
        if (StringUtil.notNull(phoneNo)) {
            return true;
        }
        return false;
    }

    @Transactional(readOnly = false)
    @Override
    public OperationDescription updateSMSFlag(RegisterDTO registerDTO) {
        UserInfo userInfo = userInfoDao.findByImsi(registerDTO.getImsi());
        userInfo.setForceSMS(registerDTO.getForceSMS());
        userInfoDao.save(userInfo);
        OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
            HttpServletResponse.SC_NO_CONTENT, "updateSMSFlag");
        return operationDescription;
    }
}
