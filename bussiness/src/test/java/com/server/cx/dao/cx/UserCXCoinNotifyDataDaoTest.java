package com.server.cx.dao.cx;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.CXCoinAccount;
import com.server.cx.entity.cx.UserCXCoinNotifyData;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserCXCoinNotifyDataDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private UserCXCoinNotifyDataDao userCXCoinNotifyDataDao;
    
    @Autowired
    private CXCoinAccountDao cxCoinAccountDao;
    
    @Test
    public void test_save_successful() {
        UserCXCoinNotifyData userCXCoinNotifyData = new UserCXCoinNotifyData();
        userCXCoinNotifyData.setBuyerEmail("sdfsdf");
        userCXCoinNotifyData.setBuyerId("123");
        userCXCoinNotifyData.setOutTradeNo("11sdsdf");
        CXCoinAccount cxCoinAccount = cxCoinAccountDao.findOne("1");
        userCXCoinNotifyData.setCxCoinAccount(cxCoinAccount);
        userCXCoinNotifyDataDao.save(userCXCoinNotifyData);
        
        UserCXCoinNotifyData dbCoinNotifyData = userCXCoinNotifyDataDao.findOne(userCXCoinNotifyData.getId());
        Assert.assertNotNull(dbCoinNotifyData);
        Assert.assertEquals(dbCoinNotifyData, userCXCoinNotifyData);
    }
    
    @Test
    public void test_find_by_out_trade_no() {
        UserCXCoinNotifyData userCXCoinNotifyData = userCXCoinNotifyDataDao.findByOutTradeNo("my123");
        Assert.assertEquals("1234567", userCXCoinNotifyData.getTradeNo());
    }
    
}
