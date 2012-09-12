package com.server.cx.dao.cx.custom;

import java.util.List;
import com.server.cx.entity.cx.CustomSubscribeType;
import com.server.cx.entity.cx.HolidaySubscribeType;
import com.server.cx.entity.cx.StatusSubscribeType;
import com.server.cx.entity.cx.SubscribeType;

public interface SubscribeTypeDaoCustom {
    public HolidaySubscribeType findHolidaySubscribeType();

    public StatusSubscribeType findStatusSubscribeType();

    public CustomSubscribeType findCustomSubscribeType();
    
    public SubscribeType findSubscribeType(String type);
    
    public void batchSaveSubscribeType(List<SubscribeType> subscribeTypes);
}
