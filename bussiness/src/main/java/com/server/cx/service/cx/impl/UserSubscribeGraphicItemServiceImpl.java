package com.server.cx.service.cx.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.UserSubscribeGraphicItemService;
import com.server.cx.util.ObjectFactory;

@Component
@Transactional(readOnly = true)
public class UserSubscribeGraphicItemServiceImpl extends CXCoinBasicService implements UserSubscribeGraphicItemService {
    @Autowired
    private GraphicInfoDao graphicInfoDao;

    @Autowired
    private UserSubscribeRecordDao userSubscribeRecordDao;

    @Autowired
    private UserSubscribeGraphicItemDao userSubscribeGraphicItemDao;

    @Transactional(readOnly = false)
    @Override
    public void subscribeGraphicItem(String imsi, String graphicInfoId) throws MoneyNotEnoughException {
        if (graphicInfoId != null) {
            checkUserRegisterCXCoinAccount(imsi);
            GraphicInfo graphicInfo = graphicInfoDao.findOne(graphicInfoId);
            if (!hasSubscribedGraphicItem(userInfo, graphicInfo)) {
                subscribeGraphicItem(userInfo, graphicInfo);
            }
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

    @Transactional(readOnly = false)
    @Override
    public void subscribeGraphicItem(UserInfo userInfo, GraphicInfo graphicInfo) throws MoneyNotEnoughException {
        //扣钱
        checkUserRegisterCXCoinAccount(userInfo.getImsi());
        checkUserCXCoinEnough(cxCoinAccount.getCoin(), graphicInfo.getPrice());
        cxCoinAccount.setCoin(cxCoinAccount.getCoin() - graphicInfo.getPrice());
        cxCoinAccountDao.save(cxCoinAccount);

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
        if (graphicInfoId != null) {
            GraphicInfo graphicInfo = graphicInfoDao.findOne(graphicInfoId);
            if (!isGraphicInfoFree(graphicInfo) && !hasSubscribedGraphicItem(userInfo, graphicInfo))
                throw new NotSubscribeTypeException("用户未订购");
        }
    }

    private boolean isGraphicInfoFree(GraphicInfo graphicInfo) {
        if (graphicInfo.getPrice() == null || graphicInfo.getPrice().doubleValue() < 1e-3) {
            return true;
        }
        return false;
    }

    //TODO： 测试方法，方便客户端取消订购，以后需要删掉
    @Transactional(readOnly = false)
    @Override
    public void deleteSubscribeItem(String imsi, String graphicInfoId) {
        if (graphicInfoId != null) {
            checkAndSetUserInfoExists(imsi);
            GraphicInfo graphicInfo = graphicInfoDao.findOne(graphicInfoId);
            List<UserSubscribeGraphicItem> userSubscribeGraphicItems = userSubscribeGraphicItemDao
                .findByUserInfoAndGraphicInfo(userInfo, graphicInfo);
            if (userSubscribeGraphicItems != null && !userSubscribeGraphicItems.isEmpty())
                userSubscribeGraphicItemDao.delete(userSubscribeGraphicItems.get(0));
        }
    }

}
