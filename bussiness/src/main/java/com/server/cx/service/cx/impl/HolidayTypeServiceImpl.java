package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.HolidayTypeDao;
import com.server.cx.dao.cx.UserHolidayMGraphicDao;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.UserHolidayMGraphic;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.HolidayTypeService;
import com.server.cx.service.util.BusinessFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "holidayTypeService")
@Transactional(readOnly = true)
public class HolidayTypeServiceImpl extends UserCheckService implements HolidayTypeService {

    @Autowired
    private BasicService basicService;
    @Autowired
    private HolidayTypeDao holidayTypeDao;
    
    @Autowired
    private BusinessFunctions businessFunctions;

    @Autowired
    private UserHolidayMGraphicDao userHolidayMGraphicDao;
    
    @Override
    public DataPage queryAllHolidayTypes(String imsi) throws SystemException {
        checkAndSetUserInfoExists(imsi);
        List<HolidayType> holidayTypes = Lists.newArrayList(holidayTypeDao.findAll());
        final String baseHref = basicService.baseHostAddress + basicService.restURL + imsi + "/holidayTypes";
        List<DataItem> holidayTypeList = generateHolidayTypeList(imsi, holidayTypes);

        DataPage dataPage = new DataPage();
        dataPage.setOffset(0);
        dataPage.setLimit(holidayTypeList.size());
        dataPage.setTotal(1);
        dataPage.setHref(baseHref);
        dataPage.setItems(holidayTypeList);
        return dataPage;
    }

    private List<DataItem> generateHolidayTypeList(String imsi, List<HolidayType> holidayTypes) {
        List<UserHolidayMGraphic>  userHolidayMGraphics = userHolidayMGraphicDao.findByUserInfo(userInfo);
        List<HolidayType> holidayTypeList = Lists.transform(userHolidayMGraphics, businessFunctions.userHolidayMGraphicTransformToHolidayType());
    	return Lists.transform(holidayTypes,businessFunctions.holidayTypeTransformToDataItem(imsi,holidayTypeList));
    }

}
