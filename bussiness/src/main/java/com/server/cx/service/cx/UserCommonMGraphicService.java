package com.server.cx.service.cx;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.server.cx.dto.OperationResult;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午2:37
 * FileName:UserCommonMGraphicService
 */
public interface UserCommonMGraphicService {
    public abstract OperationResult createUserCommonMGraphic(String imsi,MGraphicDTO mGraphicDTO);
    public abstract OperationResult editUserCommonMGraphic(String imsi,MGraphicDTO mGraphicDTO);
    public abstract OperationResult disableUserCommonMGraphic(String imsi,MGraphicDTO mGraphicDTO);
}