package com.server.cx.service.cx.impl;

import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.SmsMessageDao;
import com.server.cx.model.OperationResult;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.SmsMessageService;
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
    private UserCheckService userCheckService;

    @Override
    public OperationResult inviteFriends(String imsi, List<String> mobiles, String smsContent) throws SystemException {
        Preconditions.checkNotNull(imsi);
        Preconditions.checkNotNull(mobiles);
        userCheckService.checkAndSetUserInfoExists(imsi);
        String result = "";
        UserInfo userInfo = userCheckService.getUserInfo();
        String phoneNo = userInfo.getPhoneNo();

        if (phoneNo == null || "".equals(phoneNo)) {
            throw new CXServerBusinessException("服务端用户的手机号码为空");
        }

        List<String> alreadyRegisterPhoneNos = userCheckService.getAlreadyRegisteredMobiles(mobiles);
        List<String> notRegisteredPhoneNoList =
                SmsMessageServiceUtil.getTheNotRegisterPhoneNoList(mobiles, alreadyRegisterPhoneNos);

        List<String> contentList = SmsMessageServiceUtil.generateSmsContent(notRegisteredPhoneNoList, phoneNo);
        smsMessageDao.batchInsertSmsMessage(contentList, notRegisteredPhoneNoList, phoneNo);

        return new OperationResult("inviteFriends","success");
    }


}
