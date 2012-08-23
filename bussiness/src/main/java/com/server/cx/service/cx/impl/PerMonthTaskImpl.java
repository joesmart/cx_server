package com.server.cx.service.cx.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dao.cx.UserSubscribeRecordDao;
import com.server.cx.dao.cx.UserSubscribeTypeDao;
import com.server.cx.entity.cx.CustomSubscribeType;
import com.server.cx.entity.cx.HolidaySubscribeType;
import com.server.cx.entity.cx.StatusSubscribeType;
import com.server.cx.entity.cx.SubscribeType;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeRecord;
import com.server.cx.entity.cx.UserSubscribeType;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.PerMonthTask;
import com.server.cx.util.DateUtil;
import com.server.cx.util.ObjectFactory;

@Transactional(readOnly = true)
public class PerMonthTaskImpl implements PerMonthTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerMonthTaskImpl.class);

    @Autowired
    private UserSubscribeTypeDao userSubscribeTypeDao;
    @Autowired
    private UserInfoDao userInfoDao;

    private UserSubscribeRecordDao userSubscribeRecordDao;

    @Override
    public void doTask() throws SystemException {
        //扣除订购的钱， 更新有效的月份， 生成购买记录
        LOGGER.info("Into doTask");
        List<UserSubscribeType> userSubscribeTypes = (List<UserSubscribeType>) userSubscribeTypeDao.findAll(new Sort(
            Direction.ASC, "createdOn"));
        for (int i = 0; i < userSubscribeTypes.size(); i++) {
            doSingleSubscribeTask(userSubscribeTypes.get(i));
        }
    }

    @Transactional(readOnly = false)
    public void doSingleSubscribeTask(UserSubscribeType userSubscribeType) throws SystemException {
        UserInfo userInfo = userSubscribeType.getUserInfo();
        Double price = getMoneyBySubscribeType(userSubscribeType.getSubscribeType());
        try {
            if (price != null && userInfoDao.checkCurrentMoneyValidate(userInfo.getId(), price)) {
                userInfoDao.minusMoneyByPrice(userInfo.getId(), price);
                userSubscribeTypeDao.updateValidateMonth(DateUtil.getNextMonth(), userSubscribeType.getId());
                UserSubscribeRecord record = ObjectFactory.buildUserSubscribeRecord(userSubscribeType);
                userSubscribeRecordDao.save(record);
            }
        } catch (Exception e) {
            LOGGER.error("doSingleSubscribeTask error", e);
        }
    }

    private Double getMoneyBySubscribeType(SubscribeType subscribeType) {
        if (subscribeType instanceof HolidaySubscribeType)
            return ((HolidaySubscribeType) subscribeType).getPrice();
        if (subscribeType instanceof StatusSubscribeType)
            return ((StatusSubscribeType) subscribeType).getPrice();
        if (subscribeType instanceof CustomSubscribeType)
            return ((CustomSubscribeType) subscribeType).getPrice();
        return null;
    }
}
