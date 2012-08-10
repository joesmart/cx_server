package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.DataItem;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.HistoryMGraphicDao;
import com.server.cx.dao.cx.spec.HistoryMGraphicSpecifications;
import com.server.cx.model.OperationResult;
import com.server.cx.entity.cx.HistoryMGraphic;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.service.cx.HistoryMGraphicService;
import com.server.cx.service.util.BusinessFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-7
 * Time: 下午2:53
 * FileName:HistoryMGraphicServiceImpl
 */
@Service(value = "historyMGraphicService")
@Transactional
public class HistoryMGraphicServiceImpl extends CheckAndHistoryMGraphicService implements HistoryMGraphicService {
    @Autowired
    private HistoryMGraphicDao historyMGraphicDao;

    @Autowired
    private BusinessFunctions businessFunctions;

    @Override
    public List<DataItem> queryUserHistoryMGraphic(String imsi) {
        checkAndSetUserInfoExists(imsi);
        return queryHistoryMGraphicsByUserInfo(userInfo);
    }

    @Override
    public List<DataItem> queryHistoryMGraphicsByUserInfo(UserInfo userInfo) {
        List<HistoryMGraphic> historyMGraphics = historyMGraphicDao.findAll(HistoryMGraphicSpecifications.userHistMGraphics(userInfo), new Sort(new Sort.Order(Sort.Direction.DESC, "createdOn")));
        List<DataItem> dataItems = Lists.transform(historyMGraphics, businessFunctions.historyMGraphicTransformToDataItem(userInfo.getImsi()));
        return dataItems;
    }

    @Override
    public OperationResult deleteHistoryMGraphic(String imsi, String historyMGraphicId) {
        checkAndSetUserInfoExists(imsi);
        Preconditions.checkNotNull(historyMGraphicId,"historyMGraphicId 为空");
        historyMGraphicDao.delete(historyMGraphicId);
        return new OperationResult("deleteHistoryMGraphic","success");
    }

}
