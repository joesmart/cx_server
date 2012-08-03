package com.server.cx.entity.cx;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.ToString;
import com.server.cx.entity.basic.AuditableEntity;

@Entity
@Table(name = "suggestion")
@ToString
public class Suggestion extends AuditableEntity {
    private String content;
    private UserInfo userInfo;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userinfo_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Column(length = 500)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
