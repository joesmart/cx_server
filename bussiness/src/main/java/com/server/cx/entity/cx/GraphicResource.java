package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: yanjianzou
 * Date: 12-7-25
 * Time: 下午2:41
 * FileName:GraphicResource
 */
@Entity
@Table(name = "graphic_resource")
public class GraphicResource extends AuditableStringEntity {
    private GraphicInfo graphicInfo;
    private String resourceId;
    private String graphicId;

    public String getGraphicId() {
        return graphicId;
    }

    public void setGraphicId(String graphicId) {
        this.graphicId = graphicId;
    }

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
}
