package com.server.cx.entity.cx;


import com.server.cx.model.UserCXInfo;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("common")
@ToString
public class UserCommonMGraphic extends MGraphic {
    private Boolean active;

    public UserCommonMGraphic(){
        this.setPriority(3);
        this.setModeType(1);
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public UserCXInfo convertMGraphicStoreModeToUserCXInfo() {
        return  null;
    }
}
