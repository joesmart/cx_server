package com.server.cx.dao.cx.custom;

import com.server.cx.entity.cx.UserInfo;

import java.util.List;

public interface UserInfoCustomDao {

    public abstract UserInfo getUserInfoByPhoneNo(String phoneNo);

    public abstract UserInfo getUserInfoByImsi(String imsi);

    public List<String> getHasRegisteredPhoneNos(List<String> phoneNos);

    public List<UserInfo> getUserInfosByPhoneNos(List<String> phoneNos);
    
    public boolean checkCurrentMoneyValidate(String id, Double price);
    
}
