package com.server.cx.entity.cx;

import com.server.cx.entity.basic.AuditableStringEntity;
import com.server.cx.util.business.AuditStatus;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-22
 * Time: 下午4:51
 * FileName:UserDiyGraphic
 */
@Entity
@Table(name = "mgraphic")
public class UserDiyGraphic extends AuditableStringEntity {
    private String name;
    private String signature;
    private String owner;
    private List<GraphicResource> graphicResources;
    private AuditStatus auditStatus;
}
