package com.server.cx.service.cx.impl;

import java.util.List;

import com.server.cx.service.cx.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.cl.cx.platform.dto.BasicDataItem;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.CategoryDao;
import com.server.cx.dao.cx.GraphicResourceDao;
import com.server.cx.dao.cx.HolidayTypeDao;
import com.server.cx.dao.cx.MGraphicDao;
import com.server.cx.dao.cx.StatusTypeDao;
import com.server.cx.dao.cx.SubscribeTypeDao;
import com.server.cx.entity.cx.Category;
import com.server.cx.entity.cx.DefaultMGraphic;
import com.server.cx.entity.cx.GraphicResource;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.StatusType;
import com.server.cx.entity.cx.SubscribeType;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.ObjectFactory;

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
    
    @Autowired
    private CategoryDao categoryDao;
    
    @Autowired
    private MGraphicDao mGraphicDao;

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
    public void batchSaveGraphicResources(List<BasicDataItem> basicDataItems, String type) {
        List<GraphicResource> graphicResources = null;
        if("status".equalsIgnoreCase(type)) {
            graphicResources = Lists.transform(basicDataItems,
                businessFunctions.transferBasicDataItemToStatusGraphicResource());
            graphicResourceDao.save(graphicResources);
        } else if("holiday".equalsIgnoreCase(type)) {
            graphicResources = Lists.transform(basicDataItems, businessFunctions.transferBasicDataItemToHolidayGraphicResource());
            graphicResourceDao.save(graphicResources);
        } else if("category".equalsIgnoreCase(type)) {
            graphicResources = Lists.transform(basicDataItems, businessFunctions.transferBasicDataItemToCategoryGraphicResource());
            graphicResourceDao.save(graphicResources);
        } else if("specialNo".equalsIgnoreCase(type) || "systemDefault".equalsIgnoreCase(type)) {
            for(int i = 0; i < basicDataItems.size(); i ++) {
                GraphicResource graphicResource = ObjectFactory.buildSpecailGraphicResource(basicDataItems.get(i));
                DefaultMGraphic defaultMGraphic = ObjectFactory.buildDefaultMGraphic(basicDataItems.get(i));
                graphicResourceDao.save(graphicResource);
                defaultMGraphic.setGraphicResource(graphicResource);
                mGraphicDao.save(defaultMGraphic);
            }
        }
    }
    
    @Transactional(readOnly = false)
    @Override
    public void batchSaveHolidayType(List<HolidayType> holidayTypes) {
        holidayTypeDao.save(holidayTypes);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void batchSaveCategoryItems(List<Category> categories) {
        categoryDao.save(categories);
    }

}
