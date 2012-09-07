package com.server.cx.service.cx.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dao.cx.UserSubscribeRecordDao;
import com.server.cx.dao.cx.UserSubscribeTypeDao;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeRecord;
import com.server.cx.entity.cx.UserSubscribeType;
import com.server.cx.exception.MoneyNotEnoughException;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.PerMonthService;
import com.server.cx.util.DateUtil;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.SubscribeStatus;

@Transactional(readOnly = true)
public class PerMonthServiceImpl extends CXCoinBasicService implements PerMonthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerMonthServiceImpl.class);

    @Autowired
    private UserSubscribeTypeDao userSubscribeTypeDao;
    
    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserSubscribeRecordDao userSubscribeRecordDao;

    @Override
    public void doTask() throws SystemException {
        //扣除订购的钱， 更新有效的月份， 生成购买记录
        LOGGER.info("Into doTask");
        List<UserSubscribeType> userSubscribeTypes = userSubscribeTypeDao.findAllSubscribeTypes();
        if (userSubscribeTypes != null) {
            for (int i = 0; i < userSubscribeTypes.size(); i++) {
                doSingleSubscribeTask(userSubscribeTypes.get(i));
            }
        }
    }

    @Transactional(readOnly = false)
    public void doSingleSubscribeTask(UserSubscribeType userSubscribeType) throws SystemException {
        try {
            UserInfo userInfo = userSubscribeType.getUserInfo();
            checkUserRegisterCXCoinAccount(userInfo.getImsi());
            
            //扣除订购的钱
            checkUserCXCoinEnough(cxCoinAccount.getCoin(), userSubscribeType.getSubscribeType().getPrice());
            cxCoinAccount.setCoin(cxCoinAccount.getCoin() - userSubscribeType.getSubscribeType().getPrice());
            cxCoinAccountDao.save(cxCoinAccount);
            
            //更新有效的月份
            userSubscribeType.setValidateMonth(DateUtil.getCurrentMonth());
            userSubscribeType.setSubscribeStatus(SubscribeStatus.SUBSCRIBED);
            userSubscribeTypeDao.save(userSubscribeType);
            //生成购买记录
            UserSubscribeRecord record = ObjectFactory.buildUserSubscribeRecord(userSubscribeType);
            record.setExpenses(record.getSubscribeType().getPrice());
            userSubscribeRecordDao.save(record);
        } catch (MoneyNotEnoughException e) {
            //状态改为NOT_ENOUGH_MONEY
            userSubscribeType.setSubscribeStatus(SubscribeStatus.MONEYNOTENOUGH);
            userSubscribeType.setValidateMonth(DateUtil.getCurrentMonth());
            userSubscribeTypeDao.save(userSubscribeType);
        } catch (Exception e) {
            LOGGER.error("doSingleSubscribeTask error", e);
        }
    }
}
