package com.server.cx.model;

import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.cl.cx.platform.dto.ContactInfoDTO;
import com.server.cx.model.adapters.XmlGenericMapAdapter;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class Result {
    private String imsi;
    private String flag;
    private String content;
    private String forceUpdate;
    private String url;
    private String version;
    
    @XmlElement
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private List<CXInfo> cxinfos;

    private List<UserCXInfo> userCXInfos;

    private Map<String, CXInfo> cxinfoMap;

    private List<ContactInfoDTO> contactPeopleInfos;

    @XmlElementWrapper(name = "contactPeopleInfos")
    @XmlElement(name = "contactPeopleInfo")
    public List<ContactInfoDTO> getContactPeopleInfos() {
        return contactPeopleInfos;
    }

    @XmlElement
    public String getContent() {
        return content;
    }

    @XmlElement
    @XmlJavaTypeAdapter(XmlGenericMapAdapter.class)
    public Map<String, CXInfo> getCxinfoMap() {
        return cxinfoMap;
    }

    @XmlElementWrapper(name = "cxInfos")
    @XmlElement(name = "cxInfo")
    public List<CXInfo> getCxinfos() {
        return cxinfos;
    }

    @XmlElement
    public String getFlag() {
        return flag;
    }

    @XmlElement
    public String getForceUpdate() {
        return forceUpdate;
    }

    @XmlElement(name = "imsi")
    public String getImsi() {
        return imsi;
    }

    @XmlElement
    public String getUrl() {
        return url;
    }

    @XmlElementWrapper(name = "userCXInfos")
    @XmlElement(name = "userCxinfo")
    public List<UserCXInfo> getUserCXInfos() {
        return userCXInfos;
    }

    public void setContactPeopleInfos(List<ContactInfoDTO> contactPeopleInfos) {
        this.contactPeopleInfos = contactPeopleInfos;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCxinfoMap(Map<String, CXInfo> cxinfoMap) {
        this.cxinfoMap = cxinfoMap;
    }

    public void setCxinfos(List<CXInfo> cxinfos) {
        this.cxinfos = cxinfos;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserCXInfos(List<UserCXInfo> userCXInfos) {
        this.userCXInfos = userCXInfos;
    }
}