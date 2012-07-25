package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-7-25
 * Time: 下午2:03
 * FileName:GraphicInfo
 */
@Entity
@Table(name="graphic_infos")
@ToString @EqualsAndHashCode(callSuper = false)
public class GraphicInfo extends AuditableStringEntity {
    private String name;
    private String signature;
    private Integer recommendLevel;
    private Integer popularLevel;
    private Integer useCount;
    private Float price;
    private String owner;
    private String category;
    private List<GraphicResource> graphicResources ;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getPopularLevel() {
        return popularLevel;
    }

    public void setPopularLevel(Integer popularLevel) {
        this.popularLevel = popularLevel;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getRecommendLevel() {
        return recommendLevel;
    }

    public void setRecommendLevel(Integer recommendLevel) {
        this.recommendLevel = recommendLevel;
    }

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
}
