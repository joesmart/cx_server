package com.server.cx.dao.cx;

import com.server.cx.entity.cx.StatusType;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserStatusMGraphic;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserStatusMGraphicDaoTest  extends SpringTransactionalTestCase {
    @Autowired
    private UserStatusMGraphicDao userStatusMGraphicDao;

    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    private StatusTypeDao statusTypeDao;

    @Test
    public void test_find_by_userInfo_and_status_type() {
        UserInfo userInfo = userInfoDao.findByImsi("13146001000");
        StatusType statusType = statusTypeDao.findOne(1L);
        List<UserStatusMGraphic> userStatusMGraphics = userStatusMGraphicDao.findByUserInfoAndStatusType(userInfo, statusType);
        assertThat(userStatusMGraphics.size()).isEqualTo(1);
    }
    
    @Test
    public void test_not_found_item() {
        UserInfo userInfo = userInfoDao.findByImsi("13146001000");
        StatusType statusType = statusTypeDao.findOne(2L);
        List<UserStatusMGraphic> userStatusMGraphics = userStatusMGraphicDao.findByUserInfoAndStatusType(userInfo, statusType);
        assertThat(userStatusMGraphics).isEmpty();
    }
    
    @Test
    public void test_delete_all() {
        List<UserStatusMGraphic> userStatusMGraphics = userStatusMGraphicDao.findAll();
        assertThat(userStatusMGraphics).isNotNull();
        assertThat(userStatusMGraphics.size()).isGreaterThan(1);
        userStatusMGraphicDao.deleteAll();
        List<UserStatusMGraphic> userStatusMGraphics2 = userStatusMGraphicDao.findAll();
        assertThat(userStatusMGraphics2.size()).isEqualTo(0);
    }
}
