package com.server.cx.entity.cx;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.server.cx.util.business.StatusType;

@Entity
@Table(name="status_package")
public class StatusPackage extends AuditableEntity {
    
    private String name;
    private Double price;
    private Integer type =2; // 1:system default 2:system apply 3:custom type
    
    private List<StatusCXInfo> statusCXInfos;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    
    @OneToMany(cascade={CascadeType.REFRESH,CascadeType.PERSIST,
        CascadeType.MERGE,CascadeType.REMOVE},mappedBy="statuspackage",fetch=FetchType.LAZY)
    public List<StatusCXInfo> getStatusCXInfos() {
        return statusCXInfos;
    }
    public void setStatusCXInfos(List<StatusCXInfo> statusCXInfos) {
        this.statusCXInfos = statusCXInfos;
    }
    
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public CXInfo getCXInfoByStatusType(StatusType status) {
        List<StatusCXInfo> cxInfos = getStatusCXInfos();
        int posistion = status.getType()-1;
        if(cxInfos != null && cxInfos.size()>=posistion+1){
            return cxInfos.get(posistion).getCxinfo();
        }
        return null;
    }
}
