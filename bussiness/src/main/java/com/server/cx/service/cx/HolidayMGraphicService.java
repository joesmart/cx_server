package com.server.cx.service.cx;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.server.cx.model.OperationResult;

/**
 * User: yanjianzou
 * Date: 12-8-10
 * Time: обнГ1:42
 * FileName:HolidayMGrpahicService
 */
public interface HolidayMGraphicService {
    public abstract OperationResult create(String imsi,MGraphicDTO mGraphicDTO);
    public abstract OperationResult edit(String imsi,MGraphicDTO mGraphicDTO);
    public abstract OperationResult disable(String imsi, String userCommonMGraphicId);
}
