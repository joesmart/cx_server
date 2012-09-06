package com.server.cx.service.cx;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.dao.cx.CXCoinAccountDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dao.cx.UserSubscribeRecordDao;
import com.server.cx.dao.cx.UserSubscribeTypeDao;
import com.server.cx.entity.cx.CXCoinAccount;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeRecord;
import com.server.cx.entity.cx.UserSubscribeType;
import com.server.cx.util.business.SubscribeStatus;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class PerMonthTaskTest extends SpringTransactionalTestCase {
    @Autowired
    private PerMonthService perMonthService;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserSubscribeRecordDao userSubscribeRecordDao;
    @Autowired
    private UserSubscribeTypeDao userSubscribeTypeDao;
    
    @Autowired
    private CXCoinAccountDao cxCoinAccountDao;

    @Test
    public void test_do_task_successful() {
        perMonthService.doTask();
        //check user1
        UserInfo userInfo = userInfoDao.findOne("1");
        CXCoinAccount cxCoinAccount = cxCoinAccountDao.findByUserInfo(userInfo);
        assertEquals(25d, cxCoinAccount.getCoin(), 1e-3);
        
        List<UserSubscribeType> list1 = userSubscribeTypeDao.findUserSubscribeTypes(userInfo, "holiday");
        Assertions.assertThat(list1.size()).isEqualTo(1);
        Assertions.assertThat(list1.get(0).getSubscribeStatus()).isEqualTo(SubscribeStatus.SUBSCRIBED);
        
        List<UserSubscribeType> list2 = userSubscribeTypeDao.findUserSubscribeTypes(userInfo, "status");
        Assertions.assertThat(list2.size()).isEqualTo(1);
        Assertions.assertThat(list2.get(0).getSubscribeStatus()).isEqualTo(SubscribeStatus.SUBSCRIBED);
        
        
        //check user2
        UserInfo userInfo2 = userInfoDao.findOne("2");
        cxCoinAccount = cxCoinAccountDao.findByUserInfo(userInfo2);
        assertEquals(25d, cxCoinAccount.getCoin().doubleValue(), 1e-3);
        
        List<UserSubscribeType> list3 = userSubscribeTypeDao.findUserSubscribeTypes(userInfo2, "holiday");
        Assertions.assertThat(list3.size()).isEqualTo(1);
        Assertions.assertThat(list3.get(0).getSubscribeStatus()).isEqualTo(SubscribeStatus.SUBSCRIBED);
        
        List<UserSubscribeType> list4 = userSubscribeTypeDao.findUserSubscribeTypes(userInfo2, "status");
        Assertions.assertThat(list4.size()).isEqualTo(1);
        Assertions.assertThat(list4.get(0).getSubscribeStatus()).isEqualTo(SubscribeStatus.SUBSCRIBED);
        
        //check user3
        UserInfo userInfo3 = userInfoDao.findOne("3");
        cxCoinAccount = cxCoinAccountDao.findByUserInfo(userInfo3);
        assertEquals(30d, cxCoinAccount.getCoin().doubleValue(), 1e-3);
        
        
        List<UserSubscribeType> list5 = userSubscribeTypeDao.findUserSubscribeTypes(userInfo3, "custom");
        Assertions.assertThat(list5.size()).isEqualTo(1);
        Assertions.assertThat(list5.get(0).getSubscribeStatus()).isEqualTo(SubscribeStatus.SUBSCRIBED);
        
        //check user6
        UserInfo userInfo6 = userInfoDao.findOne("6");
        cxCoinAccount = cxCoinAccountDao.findByUserInfo(userInfo6);
        assertEquals(10d, cxCoinAccount.getCoin().doubleValue(), 1e-3);
        
        UserSubscribeType userSubscribeType = userSubscribeTypeDao.findMonthValidateAndNotSubscribedType(userInfo6, "holiday");
        Assertions.assertThat(userSubscribeType.getSubscribeStatus()).isEqualTo(SubscribeStatus.MONEYNOTENOUGH);
        
        
        List<UserSubscribeRecord> records = (List<UserSubscribeRecord>) userSubscribeRecordDao.findAll();
        Assertions.assertThat(records.size()).isGreaterThanOrEqualTo(5);
        Assertions.assertThat(records.get(records.size() - 1).getUserInfo().getImsi()).isEqualTo(userInfo3.getImsi());
        Assertions.assertThat(records.get(records.size() - 2).getUserInfo().getImsi()).isEqualTo(userInfo2.getImsi());
        Assertions.assertThat(records.get(records.size() - 3).getUserInfo().getImsi()).isEqualTo(userInfo2.getImsi());
        Assertions.assertThat(records.get(records.size() - 4).getUserInfo().getImsi()).isEqualTo(userInfo.getImsi());
        Assertions.assertThat(records.get(records.size() - 5).getUserInfo().getImsi()).isEqualTo(userInfo.getImsi());
        
        
    }

}
