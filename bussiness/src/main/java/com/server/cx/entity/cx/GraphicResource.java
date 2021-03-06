package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;
import com.server.cx.util.business.AuditStatus;

import javax.persistence.*;

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
    private UserDiyGraphic diyGraphic;
    private String resourceId;
    private String type;
    private AuditStatus auditStatus;

    @Transient
    private String thumbnailPath;
    @Transient
    private String sourcePath;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH},optional = true)
    @JoinColumn(name = "graphicinfo_id")
    public GraphicInfo getGraphicInfo() {
        return graphicInfo;
    }

    public void setGraphicInfo(GraphicInfo graphicInfo) {
        this.graphicInfo = graphicInfo;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH},optional = true)
    @JoinColumn(name = "diy_id")
    public UserDiyGraphic getDiyGraphic() {
        return diyGraphic;
    }

    public void setDiyGraphic(UserDiyGraphic diyGraphic) {
        this.diyGraphic = diyGraphic;
    }

    @Column(length = 40)
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

    @Column(length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AuditStatus getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(AuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }
}
