package com.server.cx.dao.cx;

import com.server.cx.entity.cx.GraphicResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 上午11:29
 * FileName:GraphicResourceDao
 */
public interface GraphicResourceDao extends JpaRepository<GraphicResource, String>,JpaSpecificationExecutor<GraphicResource> {
}
