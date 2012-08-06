package com.server.cx.service.cx;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.server.cx.dto.OperationResult;

/**
 * User: yanjianzou
 * Date: 12-8-6
 * Time: 下午1:54
 * FileName:UserSpecialMGraphicService
 */
public interface UserSpecialMGraphicService {
    public abstract OperationResult createUserSpecialMGraphic(String imsi,MGraphicDTO mGraphicDTO);
    public abstract OperationResult editUserSpecialMGraphic(String imsi,MGraphicDTO mGraphicDTO);
    public abstract OperationResult disableUserSpecialMGraphic(String imsi, String userCommonMGraphicId);
}
