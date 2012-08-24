package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataPage;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.exception.NotSubscribeTypeException;

public interface HolidayTypeService {

    public DataPage queryAllHolidayTypes(String imsi) throws NotSubscribeTypeException;

    public GraphicInfo getFirstChild(Long holidayTypeId);

    public abstract DataPage subscribeAndQueryHoliayTypes(String imsi);
}
