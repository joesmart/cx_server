package com.server.cx.entity.cx;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.ToString;
import com.server.cx.entity.basic.AuditableEntity;
import com.server.cx.util.business.SubscribeStatus;

@Entity
public class UserSubscribeType extends AuditableEntity {
    @Override
    public String toString() {
        return "UserSubscribeType [userInfo=" + userInfo + ", subscribeStatus=" + subscribeStatus + ", subscribeType="
            + subscribeType + ", validateMonth=" + validateMonth + "]";
    }

    private UserInfo userInfo;
    private SubscribeStatus subscribeStatus;
    private SubscribeType subscribeType;
    private Integer validateMonth;

    @OneToOne
    @JoinColumn(name = "user_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    public SubscribeStatus getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(SubscribeStatus subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }

    @OneToOne
    @JoinColumn(name = "subscribe_type_id")
    public SubscribeType getSubscribeType() {
        return subscribeType;
    }

    public void setSubscribeType(SubscribeType subscribeType) {
        this.subscribeType = subscribeType;
    }

    public Integer getValidateMonth() {
        return validateMonth;
    }

    public void setValidateMonth(Integer validateMonth) {
        this.validateMonth = validateMonth;
    }

}
