package com.server.cx.service.cx.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.StatusTypeDao;
import com.server.cx.entity.cx.StatusType;
import com.server.cx.service.cx.StatusTypeService;

@Component
@Transactional(readOnly = true)
public class StatusTypeServiceImpl extends BasicService implements StatusTypeService {
    @Autowired
    private StatusTypeDao statusTypeDao;
    
    @Override
    public DataPage queryAllStatusTypes(String imsi) {
        List<StatusType> statusType = Lists.newArrayList(statusTypeDao.findAll());
        final String baseHref = baseHostAddress + restURL + imsi + "/statusTypes";
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
        List<DataItem> holidayTypeDataItems = Lists.newArrayList();
        if (statusTypes == null || statusTypes.isEmpty())
            return holidayTypeDataItems;

        for (StatusType statusType : statusTypes) {
            DataItem dataItem = new DataItem();
            dataItem.setName(statusType.getName());
            dataItem.setLevel(statusType.getLevel());
            dataItem.setGraphicURL(imageShowURL + statusType.getGraphicResourceId());
            //TODO 这边接口未完成，需要根据imsi查出具体用户是否使用该状态包, 暂时全部返回false
            dataItem.setUsed(false);
            holidayTypeDataItems.add(dataItem);
        }

        return holidayTypeDataItems;
    }

}
