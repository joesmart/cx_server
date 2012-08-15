package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class MGraphicDaoTest extends SpringTransactionalTestCase {

    @Autowired
    MGraphicDao mGraphicDao;
    @Autowired
    UserInfoDao userInfoDao;

    @Test
    public void test_find_status_graphicinfos_by_imsi() {
        
    }

    @Test
    public void should_find_the_correct_priority(){
        UserInfo userInfo = userInfoDao.findOne("1");
        Integer maxPriority = mGraphicDao.queryMaxPriorityByUserInfo(userInfo,userInfo.getPhoneNo());
        System.out.println(maxPriority);
    }
}
