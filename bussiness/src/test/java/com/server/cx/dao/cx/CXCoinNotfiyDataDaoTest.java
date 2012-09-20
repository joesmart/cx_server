package com.server.cx.dao.cx;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.CXCoinNotfiyData;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CXCoinNotfiyDataDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private CXCoinNotfiyDataDao cxCoinNotfiyDataDao;
    
    @Test
    public void test_add_cxcoin_test() {
        CXCoinNotfiyData coinNotfiyData = new CXCoinNotfiyData();
        coinNotfiyData.setBuyerEmail("sdfsdf");
        coinNotfiyData.setBuyerId("123");
        cxCoinNotfiyDataDao.save(coinNotfiyData);
        
        CXCoinNotfiyData dbCxCoinNotfiyData = cxCoinNotfiyDataDao.findOne(coinNotfiyData.getId());
        Assert.assertEquals(dbCxCoinNotfiyData, coinNotfiyData);
    }
    
    @Test
    public void test_find_by_trade_no_successful() {
        CXCoinNotfiyData cxCoinNotfiyData = cxCoinNotfiyDataDao.findByTradeNo("123abc");
        Assert.assertNotNull(cxCoinNotfiyData);
        Assert.assertEquals(cxCoinNotfiyData.getBuyerEmail(), "gavin@hotmail.com");
    }
    
    @Test
    public void test_find_by_trade_no_exception() {
        CXCoinNotfiyData cxCoinNotfiyData = cxCoinNotfiyDataDao.findByTradeNo("123abcd");
        Assert.assertNull(cxCoinNotfiyData);
    }
}
