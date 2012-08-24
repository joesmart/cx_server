package com.server.cx.service.cx.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.server.cx.dao.cx.SubscribeTypeDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dao.cx.UserSubscribeRecordDao;
import com.server.cx.dao.cx.UserSubscribeTypeDao;
import com.server.cx.entity.cx.SubscribeType;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeRecord;
import com.server.cx.entity.cx.UserSubscribeType;
import com.server.cx.exception.MoneyNotEnoughException;
import com.server.cx.exception.NotSubscribeTypeException;
import com.server.cx.exception.SystemException;
import com.server.cx.exception.UserHasSubscribedException;
import com.server.cx.service.cx.QueryMGraphicService;
import com.server.cx.service.cx.UserSubscribeTypeService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.DateUtil;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.SubscribeStatus;

@Component
@Transactional(readOnly = true)
public class UserSubscribeTypeServiceImpl extends UserCheckService implements UserSubscribeTypeService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserSubscribeTypeServiceImpl.class);

    @Autowired
    private SubscribeTypeDao subscribeTypeDao;

    @Autowired
    private UserSubscribeTypeDao userSubscribeTypeDao;

    @Autowired
    private BusinessFunctions businessFunctions;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserSubscribeRecordDao userSubscribeRecordDao;

    @Transactional(readOnly = false)
    @Override
    public void subscribeType(UserInfo userInfo, String type) throws MoneyNotEnoughException {
        LOGGER.info("Into subscribeHolidayType userInfo = " + userInfo);
        //检查本月是否有效
        UserSubscribeType userSubscribeItem = userSubscribeTypeDao.findMonthValidateAndNotSubscribedType(userInfo,
            type);
        if (userSubscribeItem != null) {
            userSubscribeItem.setSubscribeStatus(SubscribeStatus.SUBSCRIBED);
            userSubscribeTypeDao.save(userSubscribeItem);
            UserSubscribeRecord record = ObjectFactory.buildUserSubscribeRecord(userSubscribeItem);
            userSubscribeRecordDao.save(record);
        } else {
            SubscribeType subscribeType = subscribeTypeDao.findSubscribeType(type);
            //检查账户余额
            userInfoDao.checkCurrentMoneyValidate(userInfo.getId(), subscribeType.getPrice());
            UserSubscribeType userSubscribeType = businessFunctions.holidaySubscribeTypeTransformToUserSubscribeType(
                userInfo).apply(subscribeType);
            userSubscribeType.setValidateMonth(DateUtil.getCurrentMonth());
            userSubscribeType.setSubscribeStatus(SubscribeStatus.SUBSCRIBED);
            userSubscribeTypeDao.save(userSubscribeType);
            //扣除订购的钱
            userInfo.setTotleMoney(userInfo.getTotleMoney() - subscribeType.getPrice());
            userInfoDao.save(userInfo);
            //生成购买记录
            UserSubscribeRecord record = ObjectFactory.buildUserSubscribeRecord(userSubscribeType);
            record.setExpenses(record.getSubscribeType().getPrice());
            userSubscribeRecordDao.save(record);
        }

    }

    @Override
    public void cancelSubscribeType(String imsi, String type) throws SystemException {
        LOGGER.info("Into   imsi = " + imsi);
        checkAndSetUserInfoExists(imsi);

        List<UserSubscribeType> userSubscribeTypes = userSubscribeTypeDao.findUserSubscribeTypes(userInfo, type);
        if (userSubscribeTypes != null && !userSubscribeTypes.isEmpty()) {
            UserSubscribeType userSubscribeType = userSubscribeTypes.get(0);
            //取消用户订购
            userSubscribeTypeDao.delete(userSubscribeType);
            //生成订购记录
            UserSubscribeRecord record = ObjectFactory.buildUserCancelSubscribeRecord(userSubscribeType);
            userSubscribeRecordDao.save(record);

        }

    }

    @Override
    public boolean checkSubscribeType(UserInfo userInfo, String type) throws NotSubscribeTypeException {
        LOGGER.info("Into checkExistSubscribeHolidayType userInfo = " + userInfo);
        List<UserSubscribeType> userSubscribeTypes = userSubscribeTypeDao.findUserSubscribeTypes(userInfo, type);
        if (userSubscribeTypes != null && !userSubscribeTypes.isEmpty())
            return true;
        throw new NotSubscribeTypeException("用户未订购");
    }
    
    
    @Override
    public boolean checkUserUnSubscribeType(UserInfo userInfo, String type) throws UserHasSubscribedException {
        List<UserSubscribeType> userSubscribeTypes = userSubscribeTypeDao.findUserSubscribeTypes(userInfo, type);
        if (userSubscribeTypes != null && !userSubscribeTypes.isEmpty()) {
            throw new UserHasSubscribedException("用户已经订购");
        }
        return true;
    }
}
