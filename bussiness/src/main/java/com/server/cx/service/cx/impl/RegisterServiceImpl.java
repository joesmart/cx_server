/*
 * Copyright (c) 2011 CieNet, Ltd. Created on 2011-9-21
 */
package com.server.cx.service.cx.impl;

import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.RegisterService;
import com.server.cx.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

/**
 * implement of RegisterService interface. Briefly describe what this class does.
 */
@Service("registerService")
@Transactional
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserInfoDao userInfoDao;

    public RegisterServiceImpl() {

    }

    @Override
    public String registe(Map<String, String> paramsMap) throws SystemException {
        String dealResult = "";
        String imsi = paramsMap.get(Constants.IMSI_STR);
        String phoneNo = paramsMap.get(Constants.PHONE_NO_STR);

        if (imsi != null && !"".equals(imsi)) {
            UserInfo userinfo = userInfoDao.getUserInfoByImsi(imsi);
            if (userinfo == null) {
                userinfo = new UserInfo();
                userinfo.setImsi(imsi);
                phoneNo = dealWithPhoneNo(imsi, phoneNo);
                userinfo.setPhoneNo(phoneNo);
                userInfoDao.save(userinfo);
                dealResult = StringUtil.generateXMLResultString(Constants.SUCCESS_FLAG, "用户注册成功");
            } else {
                dealResult = StringUtil.generateXMLResultString(Constants.USER_REGISTERED_FLAG, "用户已经注册");
            }
        } else {
            dealResult = StringUtil.generateXMLResultString(Constants.ERROR_FLAG, "IMSI数据为空");
        }

        return dealResult;
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
