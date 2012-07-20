package com.server.cx.entity.cx;

import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_status")
public class UserStatus extends AuditableEntity{
    
    private String name;
    private Signature signature;
    private Integer type;
    private String validTime;
    private Timestamp begingTime;
    private Timestamp endTime;
    private UserInfo user;
    private UserCXInfo userCXInfo;
    
    public UserStatus(){
        
    }
    
    public UserStatus(String name,Integer type,Timestamp begingTime,Timestamp endTime){
        this.name = name;
        this.type = type;
        this.begingTime = begingTime;
        this.endTime = endTime;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Timestamp getBegingTime() {
        return begingTime;
    }

    public void setBegingTime(Timestamp begingTime) {
        this.begingTime = begingTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    
    @OneToOne(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},optional=true)
    @JoinColumn(name="usercxinfo_id")
    public UserCXInfo getUserCXInfo() {
        return userCXInfo;
    }
    public void setUserCXInfo(UserCXInfo userCXInfo) {
        this.userCXInfo = userCXInfo;
    }
    @OneToOne(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},optional=false)
    @JoinColumn(name="user_id")
    public UserInfo getUser() {
        return user;
    }
    public void setUser(UserInfo user) {
        this.user = user;
    }
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "signature_id")
    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }
    
}
