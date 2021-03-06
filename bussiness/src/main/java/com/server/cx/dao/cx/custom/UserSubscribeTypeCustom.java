package com.server.cx.dao.cx.custom;

import java.util.List;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeType;

public interface UserSubscribeTypeCustom {
    public int updateValidateMonth(int month, Long id);

    public UserSubscribeType findMonthValidateAndNotSubscribedType(UserInfo userInfo, String queryCondition);

    public List<UserSubscribeType> findUserSubscribeTypes(UserInfo userInfo, String type);

    public List<UserSubscribeType> findAllSubscribeTypes();
    
    public UserSubscribeType findCurrentValidateSubscribeTypes(UserInfo userInfo, String type);
    
    public  List<UserSubscribeType> findUserAllStatusItemsByType(UserInfo userInfo, String type);

}
