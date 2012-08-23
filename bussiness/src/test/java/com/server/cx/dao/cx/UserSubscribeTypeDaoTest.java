package com.server.cx.dao.cx;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.UserSubscribeType;
import com.server.cx.util.business.SubscribeStatus;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserSubscribeTypeDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private UserSubscribeTypeDao userSubscribeTypeDao;
    
    @Autowired
    private SubscribeTypeDao subscribeTypeDao;
    
    @Autowired
    private UserInfoDao userInfoDao;
    
    @PersistenceContext
    private EntityManager em;

    @Test
    public void test_findAll_successful() {
        List<UserSubscribeType> userSubscribeTypes = (List<UserSubscribeType>) userSubscribeTypeDao.findAll(new Sort(Direction.ASC, "createdOn"));
        assertThat(userSubscribeTypes).isNotNull();
        assertThat(userSubscribeTypes.size()).isEqualTo(9);
        assertThat(userSubscribeTypes.get(0).getSubscribeType().getName()).isEqualTo("节日包");
    }

    @Test
    public void test_save_successful() {
        UserSubscribeType userSubscribeType = new UserSubscribeType();
        userSubscribeType.setSubscribeStatus(SubscribeStatus.SUBSCRIBED);
        userSubscribeType.setSubscribeType(subscribeTypeDao.findOne(1L));
        userSubscribeType.setUserInfo(userInfoDao.findOne("1"));
        userSubscribeType.setValidateMonth(8);
        userSubscribeTypeDao.save(userSubscribeType);
        
        UserSubscribeType dbType = userSubscribeTypeDao.findOne(userSubscribeType.getId());
        assertThat(userSubscribeType).isEqualTo(dbType);
    }

    @Test
    public void test_delete_successful() {
        UserSubscribeType userSubscribeType = userSubscribeTypeDao.findOne(6L);
        assertThat(userSubscribeType).isNotNull();
        
        userSubscribeTypeDao.delete(userSubscribeType);
        UserSubscribeType userSubscribeType2 = userSubscribeTypeDao.findOne(6L);
        assertThat(userSubscribeType2).isNull();
    }
    
    @Test
    public void test_update_validate_month() {
        int result = userSubscribeTypeDao.updateValidateMonth(10, 1L);
        System.out.println("affected rows = " + result);
        
        UserSubscribeType userSubscribeType2 = userSubscribeTypeDao.findOne(1L);
        assertThat(userSubscribeType2.getValidateMonth()).isEqualTo(10);
    }
    
    @Test
    public void test_find_subscribe_holiday_types() {
        List<UserSubscribeType> userSubscribeTypes = userSubscribeTypeDao.findSubscribeTypes(userInfoDao.findOne("1"), "holiday");
        System.out.println("userSubscribeType = " + (UserSubscribeType)userSubscribeTypes.get(0));
        assertThat(userSubscribeTypes).isNotNull();
        assertThat(userSubscribeTypes.size()).isEqualTo(1);
        assertThat(userSubscribeTypes.get(0).getSubscribeType().getName()).isEqualTo("节日包");
    }
    
    @Test
    public void test_find_subscribe_status_types() {
        List<UserSubscribeType> userSubscribeTypes = userSubscribeTypeDao.findSubscribeTypes(userInfoDao.findOne("1"), "status");
        System.out.println("userSubscribeType = " + (UserSubscribeType)userSubscribeTypes.get(0));
        assertThat(userSubscribeTypes).isNotNull();
        assertThat(userSubscribeTypes.size()).isEqualTo(1);
        assertThat(userSubscribeTypes.get(0).getSubscribeType().getName()).isEqualTo("状态包");
    }
    
    @Test
    public void test_find_subscribe_custom_types() {
        List<UserSubscribeType> userSubscribeTypes = userSubscribeTypeDao.findSubscribeTypes(userInfoDao.findOne("1"), "custom");
        System.out.println("userSubscribeType = " + (UserSubscribeType)userSubscribeTypes.get(0));
        assertThat(userSubscribeTypes).isNotNull();
        assertThat(userSubscribeTypes.size()).isEqualTo(1);
        assertThat(userSubscribeTypes.get(0).getSubscribeType().getName()).isEqualTo("自主包");
    }
    
    @Test
    public void test_findMonthValidateAndNotSubscribedType() {
        UserSubscribeType userSubscribeType = userSubscribeTypeDao.findMonthValidateAndNotSubscribedType(userInfoDao.findOne("4"), "holiday");
        assertThat(userSubscribeType).isNull();
        
        UserSubscribeType userSubscribeType2 = userSubscribeTypeDao.findMonthValidateAndNotSubscribedType(userInfoDao.findOne("4"), "status");
        assertThat(userSubscribeType2).isNotNull();
        assertThat(userSubscribeType2.getSubscribeType().getName()).isEqualTo("状态包");
    }

}
