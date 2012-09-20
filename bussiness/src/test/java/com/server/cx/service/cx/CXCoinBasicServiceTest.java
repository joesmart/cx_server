package com.server.cx.service.cx;

import org.fest.assertions.Fail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.dao.cx.CXCoinConsumeRecordDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.impl.CXCoinBasicService;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CXCoinBasicServiceTest extends SpringTransactionalTestCase {
    @Autowired
    @Qualifier("CXCoinBasicService")
    private CXCoinBasicService cxCoinBasicService;
    
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    private CXCoinConsumeRecordDao cxCoinConsumeRecordDao;
    
    
    @Test
    public void test_check_userRegisterCXCoinAccount_successful() {
        cxCoinBasicService.checkUserRegisterCXCoinAccount(userInfoDao.findOne("1").getImsi());
    }
    
    @Test
    public void test_check_userRegisterCXCoinAccount_by_userInfo_successful() {
        UserInfo userInfo = userInfoDao.findOne("1");
        System.out.println("userInfo = " + userInfo);
        cxCoinBasicService.checkUserRegisterCXCoinAccount(userInfo.getImsi());
    }
    
    @Test
    public void test_check_userRegisterCXCoinAccount_fail() {
        try {
            cxCoinBasicService.checkUserRegisterCXCoinAccount(userInfoDao.findOne("5").getImsi());
            Fail.fail("没有抛出异常");
        } catch(Exception e) {
            
        }
    }
    
    @Test
    public void test_checkUserUnConsumeCXCoin_successful()  {
        UserInfo userInfo = userInfoDao.findOne("1");
        cxCoinBasicService.checkUserUnConsumeCXCoin(userInfo);
    }
    
    @Test
    public void test_checkUserUnConsumeCXCoin_fail()  {
        UserInfo userInfo = userInfoDao.findOne("3");
        try {
            cxCoinBasicService.checkUserUnConsumeCXCoin(userInfo);
            Fail.fail("没有抛出异常");
        } catch(SystemException e) {
            
        }
    }
    
}
