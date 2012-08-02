package com.server.cx.dto;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.ToString;

@XmlRootElement
@ToString
public class VersionInfoDTO {
    private String flag;
    private String content;
    private String forceUpdate;
    private String url;

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

    public String getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
