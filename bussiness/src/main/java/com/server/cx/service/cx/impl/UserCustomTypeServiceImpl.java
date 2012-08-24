package com.server.cx.service.cx.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.cl.cx.platform.dto.DataPage;
import com.server.cx.service.cx.QueryMGraphicService;
import com.server.cx.service.cx.UserCustomTypeService;
import com.server.cx.service.cx.UserSubscribeTypeService;

@Component
public class UserCustomTypeServiceImpl extends UserCheckService implements UserCustomTypeService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserCustomTypeServiceImpl.class);

    @Autowired
    private UserSubscribeTypeService userSubscribeTypeService;

    @Autowired
    @Qualifier("customMGraphicService")
    private QueryMGraphicService queryMGraphicService;

    @Override
    public DataPage subscribeAndQueryCustomTypes(String imsi) {
        LOGGER.info("Into subscribeAndQueryCustomTypes imsi = " + imsi);
        checkAndSetUserInfoExists(imsi);
        userSubscribeTypeService.checkUserUnSubscribeType(userInfo, "custom");
        userSubscribeTypeService.subscribeType(userInfo, "custom");
        return queryMGraphicService.queryUserMGraphic(imsi);
    }

}
