package com.server.cx.service.cx;

import com.server.cx.exception.SystemException;

public interface UserStatusManagerService {
    
    public String addNewUserStatus(String imsi, String type, String signature, String validTime) throws SystemException;

    public String retriveUserStatusUserCXInfoByStatus(String imsi, String type) throws SystemException;
    
    public String getCurrentUserStatus(String imsi) throws SystemException;
    
    public String deletCurrentUserStatus(String imsi) throws SystemException;
    
}
