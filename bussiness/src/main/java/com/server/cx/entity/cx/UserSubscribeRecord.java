package com.server.cx.entity.cx;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.ToString;
import com.server.cx.entity.basic.AuditableEntity;

@Entity
@ToString
public class UserSubscribeRecord extends AuditableEntity {
    private UserInfo userInfo;
    private Double income;
    private Double expenses;
    private String description;
    private SubscribeType subscribeType;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }
    
    @Column(length=200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="subscribe_type_id")
    public SubscribeType getSubscribeType() {
        return subscribeType;
    }

    public void setSubscribeType(SubscribeType subscribeType) {
        this.subscribeType = subscribeType;
    }

}
