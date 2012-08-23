package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataPage;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.NotSubscribeTypeException;
import com.server.cx.exception.SystemException;

public interface UserSubscribeTypeService {
    public void subscribeType(UserInfo userInfo, String type);

    public void cancelSubscribeType(String imsi, String type) throws SystemException;

    public DataPage subscribeAndQueryHoliayTypes(String imsi);

    public DataPage subscribeAndQueryStatusTypes(String imsi);

    public DataPage subscribeAndQueryCustomTypes(String imsi);
    
    public boolean checkSubscribeType(UserInfo userInfo, String type) throws NotSubscribeTypeException;
}
