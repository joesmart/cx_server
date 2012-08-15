package com.server.cx.dao.cx.custom;

import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserInfo;

/**
 * User: yanjianzou
 * Date: 12-8-8
 * Time: 下午3:03
 * FileName:MGraphicCustomDao
 */
public interface MGraphicCustomDao {
    int queryMaxPriorityByUserInfo(UserInfo callerUserInfo, String selfPhoneNo);
    MGraphic queryDefaultMGraphic(String callPhoneNo);
}
