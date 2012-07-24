/*
 * Copyright (c) 2011 CieNet, Ltd. Created on 2011-9-21
 */
package com.server.cx.service.cx.impl;

import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.GenericDaoHibernate;
import com.server.cx.dao.cx.StatusPackageDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.StatusPackage;
import com.server.cx.entity.cx.UserCXInfoModeCount;
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
  private UserInfoDao userDao;
  @Autowired
  private GenericDaoHibernate<UserInfo, Long> genericUserInfoDao;

  @Autowired
  private StatusPackageDao statusPackageDao;

  public RegisterServiceImpl() {

  }

  @Override
  public String registe(Map<String, String> paramsMap) throws SystemException {
    String dealResult = "";
    String imsi = paramsMap.get(Constants.IMSI_STR);
    String phoneNo = paramsMap.get(Constants.PHONE_NO_STR);
    // String timeStampString = paramsMap.get(Constants.TIME_STAMP_STR);

    if (imsi != null && !"".equals(imsi)) {
      UserInfo userinfo = userDao.getUserInfoByImsi(imsi);
      StatusPackage statuspackage = statusPackageDao.getDefaultStatusPackage();
      if (userinfo == null) {
        userinfo = new UserInfo();
        userinfo.setImsi(imsi);
        UserCXInfoModeCount modelCount = new UserCXInfoModeCount();
        modelCount.setUser(userinfo);
        userinfo.setModeCount(modelCount);
        phoneNo = dealWithPhoneNo(imsi, phoneNo);
        userinfo.setPhoneNo(phoneNo);
        userinfo.setStatusPackage(statuspackage);
        genericUserInfoDao.persist(userinfo);
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
