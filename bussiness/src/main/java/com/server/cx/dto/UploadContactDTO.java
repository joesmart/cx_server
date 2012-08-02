package com.server.cx.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.ToString;

@XmlRootElement
@ToString 
public class UploadContactDTO {
    private List<ContactPeopleInfo> contactPeopleInfos;
    private String imsi;
    private String flag;
    private String content;
    
    public List<ContactPeopleInfo> getContactPeopleInfos() {
        return contactPeopleInfos;
    }

    public void setContactPeopleInfos(List<ContactPeopleInfo> contactPeopleInfos) {
        this.contactPeopleInfos = contactPeopleInfos;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
