package com.server.cx.xml;

import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
public class UserSetting {
    
    private String imsi;
    private String shortPhoneNos;
    
    public String getImsi() {
        return imsi;
    }
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }
    public String getShortPhoneNos() {
        return shortPhoneNos;
    }
    public void setShortPhoneNos(String shortPhoneNos) {
        this.shortPhoneNos = shortPhoneNos;
    }
}
