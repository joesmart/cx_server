package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.server.cx.dto.OperationResult;
import com.server.cx.service.cx.UserSpecialMGraphicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: yanjianzou
 * Date: 12-8-6
 * Time: 下午1:56
 * FileName:UserSpecialMGraphicServiceImpl
 */
@Service("userSpecialMGraphicService")
@Transactional
public class UserSpecialMGraphicServiceImpl extends MGraphicService implements UserSpecialMGraphicService {

    @Override
    public OperationResult createUserSpecialMGraphic(String imsi, MGraphicDTO mGraphicDTO) {

        return null;
    }

    @Override
    public OperationResult editUserSpecialMGraphic(String imsi, MGraphicDTO mGraphicDTO) {
        return null;
    }

    @Override
    public OperationResult disableUserSpecialMGraphic(String imsi, String userCommonMGraphicId) {
        return null;
    }
}
