package com.server.cx.entity.cx;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-7-26
 * Time: 下午6:04
 * FileName:UserCustomMGraphic
 */
@Entity
@DiscriminatorValue("custom")
public class UserCustomMGraphic extends MGraphic {
    private Date begin;
    private Date end;

    private List<String> phoneNos;

    public UserCustomMGraphic(){
        this.setPriority(5);
        this.setModeType(3);
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @ElementCollection
    @CollectionTable(name = "mgraphic_phone_no", joinColumns = {@JoinColumn(name = "mgraphic_id")})
    @Column(name = "phone_number")
    public List<String> getPhoneNos() {
        return phoneNos;
    }

    public void setPhoneNos(List<String> phoneNos) {
        this.phoneNos = phoneNos;
    }
}
