package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * User: yanjianzou
 * Date: 12-7-25
 * Time: 下午2:41
 * FileName:GraphicResource
 */
@Entity
@Table(name = "graphic_resource")
@ToString
@EqualsAndHashCode(callSuper = false)
public class GraphicResource extends AuditableStringEntity {
    private GraphicInfo graphicInfo;
    private String resourceId;
    private String graphicId;

    @Transient
    private String thumbnailPath;
    @Transient
    private String sourcePath;

    public String getGraphicId() {
        return graphicId;
    }

    public void setGraphicId(String graphicId) {
        this.graphicId = graphicId;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "user_id")
    public GraphicInfo getGraphicInfo() {
        return graphicInfo;
    }

    public void setGraphicInfo(GraphicInfo graphicInfo) {
        this.graphicInfo = graphicInfo;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}
