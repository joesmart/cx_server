package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataPage;
import com.server.cx.entity.cx.GraphicInfo;

public interface StatusTypeService {

    public DataPage queryAllStatusTypes(String imsi);

    public GraphicInfo getFirstChild(Long statusType);

    public abstract DataPage subscribeAndQueryStatusTypes(String imsi);
}
