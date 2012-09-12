package com.server.cx.service.cx.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.cl.cx.platform.dto.BasicDataItem;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.GraphicResourceDao;
import com.server.cx.dao.cx.HolidayTypeDao;
import com.server.cx.dao.cx.StatusTypeDao;
import com.server.cx.dao.cx.SubscribeTypeDao;
import com.server.cx.entity.cx.GraphicResource;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.StatusType;
import com.server.cx.entity.cx.SubscribeType;
import com.server.cx.service.util.BusinessFunctions;

@Component
@Transactional(readOnly = true)
public class DataServiceImpl implements DataService {
    @Autowired
    private SubscribeTypeDao subscribeTypeDao;

    @Autowired
    private StatusTypeDao statusTypeDao;
    
    @Autowired
    private HolidayTypeDao holidayTypeDao;

    @Autowired
    private BusinessFunctions businessFunctions;
    
    @Autowired
    private GraphicResourceDao graphicResourceDao;

    @Transactional(readOnly = false)
    @Override
    public void batchSaveSubscribeType(List<SubscribeType> subscribeTypes) {
        subscribeTypeDao.batchSaveSubscribeType(subscribeTypes);
    }

    @Transactional(readOnly = false)
    @Override
    public void batchSaveStatusType(List<StatusType> statusTypes) {
        statusTypeDao.save(statusTypes);
        System.out.println("----> after save");
    }

    @Transactional(readOnly = false)
    @Override
    public void batchSaveGraphicResources(List<BasicDataItem> basicDataItems) {
        List<GraphicResource> graphicResources = Lists.transform(basicDataItems,
            businessFunctions.transferBasicDataItemToGraphicResource());
        graphicResourceDao.save(graphicResources);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void batchSaveHolidayType(List<HolidayType> holidayTypes) {
        holidayTypeDao.save(holidayTypes);
    }

}
