package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataPage;
import com.server.cx.exception.SystemException;

public interface HolidayTypeService {

    public DataPage queryAllHolidayTypes(String imsi) throws SystemException;
}
