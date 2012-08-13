package com.server.cx.service.cx;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.server.cx.model.OperationResult;


public interface HolidayMGraphicService {
    public abstract OperationResult create(String imsi,MGraphicDTO mGraphicDTO);
    public abstract OperationResult edit(String imsi,MGraphicDTO mGraphicDTO);
    public abstract OperationResult disable(String imsi, String userCommonMGraphicId);
}
