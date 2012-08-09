package com.server.cx.entity.cx;

import com.google.common.collect.Lists;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午1:19
 * FileName:UserSpecialMGraphic
 */
@Entity
@DiscriminatorValue("special")
@ToString
public class UserSpecialMGraphic extends UserCommonMGraphic {

    private List<String> phoneNos = Lists.newArrayList();

    public UserSpecialMGraphic(){
        this.setPriority(4);
        this.setModeType(2);
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
