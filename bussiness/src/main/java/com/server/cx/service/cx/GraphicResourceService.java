package com.server.cx.service.cx;

import com.server.cx.util.business.AuditStatus;

import java.util.List;

/**
 * User: ZouYanjian
 * Date: 8/30/12
 * Time: 1:41 PM
 * FileName:GraphicResourceService
 */
public interface GraphicResourceService {
    public abstract void updateGraphicResourcesAuditStatus(List<String> ids,AuditStatus auditStatus);
}
