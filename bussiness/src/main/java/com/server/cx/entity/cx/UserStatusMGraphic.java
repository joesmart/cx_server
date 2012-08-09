package com.server.cx.entity.cx;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * User: yanjianzou
 * Date: 12-7-26
 * Time: 下午6:03
 * FileName:UserStatusMGraphic
 */
@Entity
@DiscriminatorValue("status")
public class UserStatusMGraphic extends MGraphic {
    private Date validDate;
    private StatusType statusType;

    public UserStatusMGraphic(){
        this.setPriority(7);
        this.setModeType(5);
    }

    @ManyToOne
    @JoinColumn(name = "status_type_id")
    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

}
