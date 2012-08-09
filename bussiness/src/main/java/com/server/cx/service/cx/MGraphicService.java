package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.MGraphicDTO;
import com.server.cx.model.OperationResult;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午2:37
 * FileName:MGraphicService
 */
public interface MGraphicService {
    public abstract OperationResult createUserCommonMGraphic(String imsi,MGraphicDTO mGraphicDTO);
    public abstract OperationResult editUserCommonMGraphic(String imsi,MGraphicDTO mGraphicDTO);
    public abstract OperationResult disableUserCommonMGraphic(String imsi, String userCommonMGraphicId);
    public DataPage queryUserMGraphic(String imsi);
}
