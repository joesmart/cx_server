package com.server.cx.entity.cx;

import javax.persistence.Entity;
import com.server.cx.entity.basic.AuditableEntity;
import lombok.ToString;

@Entity
@ToString
public class CXCoinTotalItem extends AuditableEntity {
    private Double cxCoinCount;

    public Double getCxCoinCount() {
        return cxCoinCount;
    }

    public void setCxCoinCount(Double cxCoinCount) {
        this.cxCoinCount = cxCoinCount;
    }
}
