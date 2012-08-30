package com.server.cx.service.cx;

import com.server.cx.entity.cx.GraphicResource;
import com.server.cx.entity.cx.UserDiyGraphic;

import java.util.Map;

/**
 * User: ZouYanjian
 * Date: 8/30/12
 * Time: 1:41 PM
 * FileName:GraphicResourceService
 */
public interface GraphicResourceService {
    public  abstract Map<String,GraphicResource> findCheckedGraphicResourceByUserDiyGraphic(UserDiyGraphic userDiyGraphic);
}
