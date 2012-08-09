package com.server.cx.service.cx;

import com.server.cx.model.OperationResult;
import com.server.cx.exception.SystemException;

import java.util.List;

public interface SmsMessageService {

    public OperationResult inviteFriends(String imsi, List<String> mobiles, String smsContent) throws SystemException;

}
