// Copyright (c) 2009-2012 CIeNET Ltd. All rights reserved.

package com.server.cx.dto;

/**
 * Briefly describe what this class does.
 */
public class ContactPeopleInfo {

    private String contactName;
    private String phoneNumList;
    private UserCXInfo userCXInfo;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumList() {
        return phoneNumList;
    }

    public void setPhoneNumList(String phoneNumList) {
        this.phoneNumList = phoneNumList;
    }

    public UserCXInfo getUserCXInfo() {
      return userCXInfo;
    }

    public void setUserCXInfo(UserCXInfo userCXInfo) {
      this.userCXInfo = userCXInfo;
    }
}
