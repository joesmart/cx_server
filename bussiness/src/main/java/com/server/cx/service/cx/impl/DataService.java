package com.server.cx.service.cx.impl;

import java.util.List;
import com.cl.cx.platform.dto.BasicDataItem;
import com.server.cx.entity.cx.Category;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.StatusType;
import com.server.cx.entity.cx.SubscribeType;

public interface DataService {

    public void batchSaveSubscribeType(List<SubscribeType> subscribeTypes);

    public void batchSaveStatusType(List<StatusType> statusTypes);

    public void batchSaveGraphicResources(List<BasicDataItem> basicDataItems, String type);

    public void batchSaveHolidayType(List<HolidayType> holidayTypes);

    public void batchSaveCategoryItems(List<Category> categories);

}
