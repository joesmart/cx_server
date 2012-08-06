package com.server.cx.service.cx.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.HolidayTypeDao;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.HolidayTypeService;
import com.server.cx.service.util.BusinessFunctions;

@Component
@Transactional(readOnly = true)
public class HolidayTypeServiceImpl extends BasicService implements HolidayTypeService {
    @Autowired
    private HolidayTypeDao holidayTypeDao;
    
    @Autowired
    private BusinessFunctions businessFunctions;
    
    @Override
    public DataPage queryAllHolidayTypes(String imsi) throws SystemException {
        List<HolidayType> holidayTypes = Lists.newArrayList(holidayTypeDao.findAll());
        final String baseHref = baseHostAddress + restURL + imsi + "/holidayTypes";
        List<DataItem> holidayTypeList = generateHolidayTypeList(holidayTypes, imsi);

        DataPage dataPage = new DataPage();
        dataPage.setOffset(0);
        dataPage.setLimit(holidayTypeList.size());
        dataPage.setTotal(1);
        dataPage.setHref(baseHref);
        dataPage.setItems(holidayTypeList);
        return dataPage;
    }

    private List<DataItem> generateHolidayTypeList(List<HolidayType> holidayTypes, String imsi) {
    	return Lists.transform(holidayTypes,businessFunctions.holidayTypeTransformToDataItem());
    }

}
