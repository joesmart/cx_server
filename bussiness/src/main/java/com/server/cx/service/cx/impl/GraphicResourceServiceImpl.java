package com.server.cx.service.cx.impl;

import com.google.common.collect.Lists;
import com.server.cx.dao.cx.GraphicResourceDao;
import com.server.cx.dao.cx.UserDiyGraphicDao;
import com.server.cx.entity.cx.GraphicResource;
import com.server.cx.entity.cx.UserDiyGraphic;
import com.server.cx.service.cx.GraphicResourceService;
import com.server.cx.util.business.AuditStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: ZouYanjian
 * Date: 8/30/12
 * Time: 1:44 PM
 * FileName:GraphicResourceServiceImpl
 */
@Service(value = "graphicResourceService")
@Transactional
public class GraphicResourceServiceImpl implements GraphicResourceService {

    public static final Logger LOGGER = LoggerFactory.getLogger(GraphicResourceServiceImpl.class);
    @Autowired
    private GraphicResourceDao graphicResourceDao;
    @Autowired
    private UserDiyGraphicDao userDiyGraphicDao;


    @Override
    public void updateGraphicResourcesAuditStatus(List<String> ids, AuditStatus auditStatus) {
       List<GraphicResource> graphicResources = Lists.newArrayList( graphicResourceDao.findByResourceIdIn(ids) );
        if(graphicResources == null || graphicResources.size() == 0){
            return;
        }

        for(GraphicResource graphicResource :graphicResources){
            graphicResource.setAuditStatus(auditStatus);
        }
        List<GraphicResource> graphicResourceList =  graphicResourceDao.save(graphicResources);
      //  updateRelateUserDiyGraphics(graphicResourceList, auditStatus);

        LOGGER.info(graphicResourceList.toString());
    }

    private void updateRelateUserDiyGraphics(List<GraphicResource> graphicResourceList, AuditStatus auditStatus) {
        List<UserDiyGraphic> userDiyGraphics = Lists.newArrayListWithCapacity(graphicResourceList.size());
        for(GraphicResource tempGraphicResource:graphicResourceList){
            UserDiyGraphic userDiyGraphic = tempGraphicResource.getDiyGraphic();
            if(userDiyGraphic != null){
                userDiyGraphic.setAuditStatus(auditStatus);
                userDiyGraphics.add(userDiyGraphic);
            }
        }
        userDiyGraphicDao.save(userDiyGraphics);
    }
}
