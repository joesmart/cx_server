package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserInfo;

import java.util.List;

public interface UserInfoDao {

    public abstract UserInfo getUserInfoByPhoneNo(String phoneNo);

    public abstract UserInfo getUserInfoByImsi(String imsi);
    
    public List<String> getHasRegisteredPhoneNos(List<String> phoneNos);

    public List<UserInfo> getUserInfoByShortPhoneNo(String shortPhoneNo);
}
