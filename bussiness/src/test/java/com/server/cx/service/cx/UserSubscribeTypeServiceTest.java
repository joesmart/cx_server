package com.server.cx.service.cx;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.fest.assertions.Fail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dao.cx.UserSubscribeRecordDao;
import com.server.cx.dao.cx.UserSubscribeTypeDao;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeRecord;
import com.server.cx.entity.cx.UserSubscribeType;
import com.server.cx.exception.MoneyNotEnoughException;
import com.server.cx.exception.NotSubscribeTypeException;
import com.server.cx.util.business.SubscribeStatus;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class UserSubscribeTypeServiceTest extends SpringTransactionalTestCase {
    @Autowired
    private UserSubscribeTypeService userSubscribeTypeService;

    @Autowired
    private UserSubscribeTypeDao userSubscribeTypeDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserSubscribeRecordDao userSubscribeRecordDao;

    @Test
    public void test_subscribe_holiday_type_unsubscribed_month_validate() {
        int recordSize = ((List<UserSubscribeRecord>) userSubscribeRecordDao.findAll()).size();
        UserInfo userInfo = userInfoDao.findOne("7");
        userSubscribeTypeService.subscribeType(userInfo, "holiday");
        List<UserSubscribeType> userSubscribeTypes = userSubscribeTypeDao.findUserSubscribeTypes(userInfo, "holiday");
        assertThat(userSubscribeTypes.size()).isEqualTo(1);
        assertThat(userSubscribeTypes.get(0).getSubscribeStatus()).isEqualTo(SubscribeStatus.SUBSCRIBED);

        List<UserSubscribeRecord> records = (List<UserSubscribeRecord>) userSubscribeRecordDao.findAll(new Sort(
            Direction.ASC, "createdOn"));
        assertThat(records.size()).isEqualTo(recordSize + 1);
        UserSubscribeRecord record = records.get(records.size() - 1);
        System.out.println("record = " + record);
        assertThat(record.getUserInfo()).isEqualTo(userInfo);
        assertThat(record.getDescription()).isEqualTo("订购");
        assertThat(record.getSubscribeType().getName()).isEqualTo("节日包");
    }

    @Test
    public void test_subscribe_holiday_type_unsubscribed_month_invalidate_money_enough() {
        int recordSize = ((List<UserSubscribeRecord>) userSubscribeRecordDao.findAll()).size();
        UserInfo userInfo = userInfoDao.findOne("8");
        double totleMoney = userInfo.getTotleMoney();
        userSubscribeTypeService.subscribeType(userInfo, "holiday");
        List<UserSubscribeType> userSubscribeTypes = userSubscribeTypeDao.findUserSubscribeTypes(userInfo, "holiday");
        assertThat(userSubscribeTypes.size()).isEqualTo(1);
        assertThat(userSubscribeTypes.get(0).getSubscribeStatus()).isEqualTo(SubscribeStatus.SUBSCRIBED);

        List<UserSubscribeRecord> records = (List<UserSubscribeRecord>) userSubscribeRecordDao.findAll(new Sort(
            Direction.ASC, "createdOn"));
        assertThat(records.size()).isEqualTo(recordSize + 1);
        UserSubscribeRecord record = records.get(records.size() - 1);
        System.out.println("record = " + record);
        assertThat(record.getUserInfo()).isEqualTo(userInfo);
        assertThat(record.getDescription()).isEqualTo("订购");
        assertThat(record.getSubscribeType().getName()).isEqualTo("节日包");
        assertEquals(userInfo.getTotleMoney().doubleValue(), totleMoney - 15, 1e-3);
    }

    @Test
    public void test_subscribe_holiday_type_unsubscribed_month_invalidate_money_not_enough() {
        UserInfo userInfo = userInfoDao.findOne("9");
        try {
            userSubscribeTypeService.subscribeType(userInfo, "holiday");
            Fail.fail("没有抛出异常");
        } catch (MoneyNotEnoughException e) {

        }
    }

    @Test
    public void test_cancel_subscribe_holiday_type() {
        UserInfo userInfo = userInfoDao.findOne("1");
        List<UserSubscribeRecord> records = (List<UserSubscribeRecord>) userSubscribeRecordDao.findAll();
        List<UserSubscribeType> userSubscribeTypes = userSubscribeTypeDao.findUserSubscribeTypes(userInfo, "holiday");
        assertThat(userSubscribeTypes).isNotNull();
        assertThat(userSubscribeTypes.size()).isEqualTo(1);
        userSubscribeTypeService.cancelSubscribeType(userInfo.getImsi(), "holiday");
        List<UserSubscribeType> userSubscribeTypes2 = userSubscribeTypeDao.findUserSubscribeTypes(userInfo, "holiday");
        assertThat(userSubscribeTypes2).isNotNull();
        assertThat(userSubscribeTypes2.size()).isEqualTo(0);
        UserSubscribeType userSubscribeType = userSubscribeTypeDao.findOne(userSubscribeTypes.get(0).getId());
        assertThat(userSubscribeType).isNull();
        //check record 
        List<UserSubscribeRecord> records2 = (List<UserSubscribeRecord>) userSubscribeRecordDao.findAll();
        assertThat(records.size()).isEqualTo(records2.size() - 1);
        UserSubscribeRecord lastRecord = records2.get(records2.size() - 1);
        lastRecord.getDescription().equals("取消订购");

    }

    @Test
    public void test_check_subscribe_type_true() {
        UserInfo userInfo = userInfoDao.findOne("1");
        boolean result = userSubscribeTypeService.checkSubscribeType(userInfo, "holiday");
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void test_check_subscribe_type_exception() {
        UserInfo userInfo = userInfoDao.findOne("7");
        try {
            userSubscribeTypeService.checkSubscribeType(userInfo, "status");
            Fail.fail("没有抛出异常");
        } catch (NotSubscribeTypeException e) {

        }
    }
}
