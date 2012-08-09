package com.server.cx.dao.cx.custom;

import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserInfo;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-8
 * Time: 下午3:03
 * FileName:MGraphicCustomDao
 */
public interface MGraphicCustomDao {

    List<MGraphic> queryUserMGraphics(UserInfo userInfo, Integer maxPriority, String callPhoneNo);

    int queryMaxPriorityByUserInfo(UserInfo userInfo, String callPhoneNo);

    MGraphic queryDefaultMGraphic(String callPhoneNo);
}
