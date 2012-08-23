package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataPage;

public interface UserCustomTypeService {
    public DataPage subscribeAndQueryCustomTypes(String imsi);
}
