package com.server.cx.service.cx.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dao.cx.UserSubscribeGraphicItemDao;
import com.server.cx.dao.cx.UserSubscribeRecordDao;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeGraphicItem;
import com.server.cx.entity.cx.UserSubscribeRecord;
import com.server.cx.exception.MoneyNotEnoughException;
import com.server.cx.exception.NotSubscribeTypeException;
import com.server.cx.service.cx.UserSubscribeGraphicItemService;
import com.server.cx.util.ObjectFactory;

@Component
public class UserSubscribeGraphicItemServiceImpl extends UserCheckService implements UserSubscribeGraphicItemService {
    @Autowired
    private GraphicInfoDao graphicInfoDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserSubscribeRecordDao userSubscribeRecordDao;

    @Autowired
    private UserSubscribeGraphicItemDao userSubscribeGraphicItemDao;

    @Override
    public void subscribeGraphicItem(String imsi, String graphicInfoId) throws MoneyNotEnoughException {
        checkAndSetUserInfoExists(imsi);
        GraphicInfo graphicInfo = graphicInfoDao.findOne(graphicInfoId);
        if (!hasSubscribedGraphicItem(userInfo, graphicInfo)) {
            subscribeGraphicItem(userInfo, graphicInfo);
        }
    }

    private boolean hasSubscribedGraphicItem(UserInfo userInfo, GraphicInfo graphicInfo) {
        List<UserSubscribeGraphicItem> graphicItems = userSubscribeGraphicItemDao.findByUserInfoAndGraphicInfo(
            userInfo, graphicInfo);
        if (graphicItems == null || graphicItems.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void subscribeGraphicItem(UserInfo userInfo, GraphicInfo graphicInfo) throws MoneyNotEnoughException {
        //扣钱
        userInfoDao.checkCurrentMoneyValidate(userInfo.getId(), graphicInfo.getPrice());
        userInfo.setTotleMoney(userInfo.getTotleMoney() - graphicInfo.getPrice());
        userInfoDao.save(userInfo);
        
        //添加用户订购item
        UserSubscribeGraphicItem userSubscribeGraphicItem = new UserSubscribeGraphicItem(userInfo, graphicInfo);
        userSubscribeGraphicItemDao.save(userSubscribeGraphicItem);
        
        //添加用户订购记录
        UserSubscribeRecord userSubscribeRecord = ObjectFactory.buildUserGraphicItemSubscribeRecord(userInfo);
        userSubscribeRecord.setExpenses(graphicInfo.getPrice());
        userSubscribeRecordDao.save(userSubscribeRecord);
    }

    @Override
    public void checkUserSubscribeGraphicItem(UserInfo userInfo, String graphicInfoId) throws NotSubscribeTypeException {
        GraphicInfo graphicInfo = graphicInfoDao.findOne(graphicInfoId);
        if(!hasSubscribedGraphicItem(userInfo, graphicInfo))
            throw new NotSubscribeTypeException("用户未订购");
    }

}
