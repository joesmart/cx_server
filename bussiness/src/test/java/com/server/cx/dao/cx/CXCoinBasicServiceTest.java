package com.server.cx.dao.cx;

import org.fest.assertions.Fail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.service.cx.impl.CXCoinBasicService;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CXCoinBasicServiceTest extends SpringTransactionalTestCase {
    @Autowired
    @Qualifier("CXCoinBasicService")
    private CXCoinBasicService cxCoinBasicService;
    
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Test
    public void test_check_userRegisterCXCoinAccount_successful() {
        cxCoinBasicService.checkUserRegisterCXCoinAccount(userInfoDao.findOne("1").getImsi());
    }
    
    @Test
    public void test_check_userRegisterCXCoinAccount_by_userInfo_successful() {
        UserInfo userInfo = userInfoDao.findOne("1");
        System.out.println("userInfo = " + userInfo);
        cxCoinBasicService.checkUserRegisterCXCoinAccount(userInfo);
    }
    
    @Test
    public void test_check_userRegisterCXCoinAccount_fail() {
        try {
            cxCoinBasicService.checkUserRegisterCXCoinAccount(userInfoDao.findOne("5"));
            Fail.fail("没有抛出异常");
        } catch(Exception e) {
            
        }
    }
}
