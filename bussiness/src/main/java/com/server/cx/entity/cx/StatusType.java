package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: yanjianzou
 * Date: 12-7-26
 * Time: 下午5:34
 * FileName:StatusType
 */
@Entity
@Table(name = "status_type")
public class StatusType extends AuditableEntity {

    private String name;
    private String graphicResourceId;

    public String getGraphicResourceId() {
        return graphicResourceId;
    }

    public void setGraphicResourceId(String graphicResourceId) {
        this.graphicResourceId = graphicResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
