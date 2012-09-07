package com.server.cx.entity.cx;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.ToString;
import com.server.cx.entity.basic.AuditableEntity;

@Entity
@ToString
public class CXCoinConsumeRecord extends AuditableEntity {
    private UserInfo userInfo;
    private Double cxCoin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Double getCxCoin() {
        return cxCoin;
    }

    public void setCxCoin(Double cxCoin) {
        this.cxCoin = cxCoin;
    }
}
