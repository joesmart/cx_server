package com.server.cx.entity.cx;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.ToString;

@Entity
@DiscriminatorValue("user")
@ToString
public class UserCXCoinNotifyData extends CXCoinNotfiyData {
    private CXCoinAccount cxCoinAccount;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cxcoin_account_id")
    public CXCoinAccount getCxCoinAccount() {
        return cxCoinAccount;
    }

    public void setCxCoinAccount(CXCoinAccount cxCoinAccount) {
        this.cxCoinAccount = cxCoinAccount;
    }
}
