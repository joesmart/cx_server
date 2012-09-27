package com.server.cx.entity.cx;


import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Entity
@DiscriminatorValue("common")
@ToString
public class UserCommonMGraphic extends MGraphic {

    public UserCommonMGraphic(){
        this.setPriority(3);
        this.setModeType(2);
    }
    private Boolean common;  //true 无特殊号码设定 false 指定特殊号码
    private List<String> phoneNos;

    @ElementCollection
    @CollectionTable(name = "mgraphic_phone_no", joinColumns = {@JoinColumn(name = "mgraphic_id")})
    @Column(name = "phone_number")
    public List<String> getPhoneNos() {
        return phoneNos;
    }

    public void setPhoneNos(List<String> phoneNos) {
        this.phoneNos = phoneNos;
    }

    public Boolean getCommon() {
        return common;
    }

    public void setCommon(Boolean common) {
        this.common = common;
    }
}
