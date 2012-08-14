// Copyright (c) 2009-2012 CIeNET Ltd. All rights reserved.

package com.cl.cx.platform.dto;

import lombok.ToString;

/**
 * Briefly describe what this class does.
 */
@ToString
public class ContactInfoDTO {

    private String contactName;
    private String phoneNo;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

}
