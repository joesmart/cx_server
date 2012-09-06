package com.server.cx.dao.cx;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.dao.cx.spec.SubscribeRecordSpecifications;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeRecord;

@ContextConfiguration(locations={"/applicationContext.xml"})
public class UserSubscribeRecordDaoTest extends SpringTransactionalTestCase{
    @Autowired
    private UserSubscribeRecordDao userSubscribeRecordDao;
    
    @Autowired
    private SubscribeTypeDao subscribeTypeDao;
    
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Test
    public void test_save_subscribe_record() {
        UserSubscribeRecord record = new UserSubscribeRecord();
        record.setDescription("订购");
        record.setExpenses(10D);
        record.setSubscribeType(subscribeTypeDao.findOne(1L));
        record.setUserInfo(userInfoDao.findOne("1"));
        userSubscribeRecordDao.save(record);
        
        UserSubscribeRecord dbRecord = userSubscribeRecordDao.findOne(record.getId());
        assertThat(dbRecord).isNotNull();
        assertThat(dbRecord).isEqualTo(record);
    }
    
    @Test
    public void test_save_unsubscribe_record() {
        UserSubscribeRecord record = new UserSubscribeRecord();
        record.setDescription("取消订购");
        record.setSubscribeType(subscribeTypeDao.findOne(1L));
        record.setUserInfo(userInfoDao.findOne("2"));
        userSubscribeRecordDao.save(record);
        
        UserSubscribeRecord dbRecord = userSubscribeRecordDao.findOne(record.getId());
        assertThat(dbRecord).isNotNull();
        assertThat(dbRecord).isEqualTo(record);
    }
    
    @Test
    public void test_find_all_by_specification() {
        UserInfo userInfo = userInfoDao.findOne("1");
        List<UserSubscribeRecord> userSubscribeRecords = userSubscribeRecordDao.findAll(SubscribeRecordSpecifications.userSubscribeRecord(userInfo));
        assertThat(userSubscribeRecords).isNotNull();
        assertThat(userSubscribeRecords.size()).isEqualTo(2);
        assertThat(userSubscribeRecords.get(0).getDescription()).isEqualTo("订购");
    }
    
    
}
