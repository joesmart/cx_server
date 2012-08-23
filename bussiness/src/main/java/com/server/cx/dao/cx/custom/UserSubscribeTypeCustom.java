package com.server.cx.dao.cx.custom;

import java.util.List;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeType;

public interface UserSubscribeTypeCustom {
    public int updateValidateMonth(int month, Long id);
    
    
    public UserSubscribeType findMonthValidateAndNotSubscribedType(UserInfo userInfo, String queryCondition);
    public List<UserSubscribeType> findSubscribeTypes(UserInfo userInfo, String type);
    
}
