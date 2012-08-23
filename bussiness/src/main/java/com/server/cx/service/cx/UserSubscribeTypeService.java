package com.server.cx.service.cx;

import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.NotSubscribeTypeException;
import com.server.cx.exception.SystemException;
import com.server.cx.exception.UserHasSubscribedException;

public interface UserSubscribeTypeService {
    public void subscribeType(UserInfo userInfo, String type);

    public void cancelSubscribeType(String imsi, String type) throws SystemException;

    public boolean checkSubscribeType(UserInfo userInfo, String type) throws NotSubscribeTypeException;

    public abstract boolean checkUserUnSubscribeType(UserInfo userInfo, String type) throws UserHasSubscribedException;
}
