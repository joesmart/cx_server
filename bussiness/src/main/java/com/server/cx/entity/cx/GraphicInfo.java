package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;
import com.server.cx.util.business.AuditStatus;
import javax.persistence.*;
import java.util.List;

/**
 * User: yanjianzou Date: 12-7-25 Time: 下午2:03 FileName:GraphicInfo
 */
@Entity
@Table(name = "graphic_infos")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "cast_type", discriminatorType = DiscriminatorType.STRING, length = 10)
@DiscriminatorValue("basic")
public class GraphicInfo extends AuditableStringEntity {
    private String name;
    private String signature;
    private Integer level;
    private Integer useCount;
    private Double price; 
    private String owner;
    private List<GraphicResource> graphicResources;
    private Category category;
    private StatusType statusType;
    private HolidayType holidayType; 
    private Boolean recommend;
    private AuditStatus auditStatus;

    @Column(length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 40)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(length = 60)
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "graphicInfo", fetch = FetchType.LAZY)
    public List<GraphicResource> getGraphicResources() {
        return graphicResources;
    }

    public void setGraphicResources(List<GraphicResource> graphicResources) {
        this.graphicResources = graphicResources;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "category_id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "status_type_id")
    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "holiday_type_id")
    public HolidayType getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(HolidayType holidayType) {
        this.holidayType = holidayType;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    public AuditStatus getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(AuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }
}
