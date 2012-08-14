package com.server.cx.service.cx.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.StatusTypeDao;
import com.server.cx.dao.cx.UserStatusMGraphicDao;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.StatusType;
import com.server.cx.entity.cx.UserHolidayMGraphic;
import com.server.cx.entity.cx.UserStatusMGraphic;
import com.server.cx.service.cx.StatusTypeService;
import com.server.cx.service.util.BusinessFunctions;

@Component
@Transactional(readOnly = true)
public class StatusTypeServiceImpl extends UserCheckService implements StatusTypeService {
    @Autowired
    private StatusTypeDao statusTypeDao;
    
    @Autowired
    private UserStatusMGraphicDao userStatusMGraphicDao;
    
    @Autowired
    private BusinessFunctions businessFunctions;
    
    @Autowired
    private BasicService basicService;
    
    @Override
    public DataPage queryAllStatusTypes(String imsi) {
        checkAndSetUserInfoExists(imsi);
        List<StatusType> statusType = Lists.newArrayList(statusTypeDao.findAll());
        final String baseHref = basicService.baseHostAddress + basicService.restURL + imsi + "/statusTypes";
        List<DataItem> statusTypeList = generateStatusTypeList(statusType, imsi);

        DataPage dataPage = new DataPage();
        dataPage.setOffset(0);
        dataPage.setLimit(statusTypeList.size());
        dataPage.setTotal(1);
        dataPage.setHref(baseHref);
        dataPage.setItems(statusTypeList);
        return dataPage;
    }
    
    private List<DataItem> generateStatusTypeList(List<StatusType> statusTypes, String imsi) {
        List<UserStatusMGraphic>  userStatusMGraphics = userStatusMGraphicDao.findByUserInfo(userInfo);
        List<StatusType> statusTypeList = Lists.transform(userStatusMGraphics, businessFunctions.userStatusMGraphicTransformToStatusType());
        return Lists.transform(statusTypes, businessFunctions.statusTypeTransformToDataItem(imsi,statusTypeList));
    }

}
