package com.server.cx.service.cx.impl;

import com.server.cx.entity.cx.GraphicResource;
import com.server.cx.entity.cx.UserDiyGraphic;
import com.server.cx.service.cx.GraphicResourceService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * User: ZouYanjian
 * Date: 8/30/12
 * Time: 1:44 PM
 * FileName:GraphicResourceServiceImpl
 */
@Service(value = "graphicResourceService")
public class GraphicResourceServiceImpl implements GraphicResourceService {
    @Override
    public Map<String, GraphicResource> findCheckedGraphicResourceByUserDiyGraphic(UserDiyGraphic userDiyGraphic) {
        return null;
    }
}
