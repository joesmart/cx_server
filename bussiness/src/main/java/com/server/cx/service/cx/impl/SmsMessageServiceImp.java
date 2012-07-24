package com.server.cx.service.cx.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.SmsMessageDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.SmsMessageService;
import com.server.cx.util.StringUtil;
import com.server.cx.util.business.SmsMessageServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("smsMessageService")
@Transactional
public class SmsMessageServiceImp implements SmsMessageService {

  @Autowired
  private SmsMessageDao smsMessageDao;

  @Autowired
  private UserInfoDao userInfoDao;

  @Override
  public String inviteFriends(String imsi, String mobiles) throws SystemException {
    String result = "";
    UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
    if (userInfo == null) {
      return StringUtil.generateXMLResultString(Constants.USER_DATA_ERROR_FLAG, "用户不存在");
    }
    String phoneNo = userInfo.getPhoneNo();
    if (phoneNo == null || "".equals(phoneNo)) {
      result = StringUtil.generateXMLResultString(Constants.DATA_NOTFOUND_FLAG, "服务端用户的手机号码为空");
      return result;
    }

    List<String> mobilesList = Lists.newArrayList(Splitter.on(",").split(mobiles));
    List<String> alreadyRegisterPhoneNos = userInfoDao.getHasRegisteredPhoneNos(mobilesList);
    List<String> notRegisteredPhoneNoList =
        SmsMessageServiceUtil.getTheNotRegisterPhoneNoList(mobilesList, alreadyRegisterPhoneNos);

    List<String> contentList = SmsMessageServiceUtil.generateSmsContent(notRegisteredPhoneNoList, phoneNo);
    smsMessageDao.batchInsertSmsMessage(contentList, notRegisteredPhoneNoList, phoneNo);

    result = StringUtil.generateXMLResultString(Constants.SUCCESS_FLAG, "发送邀请好友成功");
    return result;
  }


}
