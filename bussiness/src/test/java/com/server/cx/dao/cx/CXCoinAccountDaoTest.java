package com.server.cx.dao.cx;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.CXCoinAccount;
import com.server.cx.entity.cx.UserInfo;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CXCoinAccountDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private CXCoinAccountDao cxCoinAccountDao;
    
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Test
    public void test_find_by_user_info() {
        UserInfo userInfo = userInfoDao.findOne("1");
        CXCoinAccount cxCoinAccount = cxCoinAccountDao.findByUserInfo(userInfo);
        assertThat(cxCoinAccount.getName()).isEqualTo("Account1");
    }
   
    @Test
    public void test_save() {
        CXCoinAccount cxCoinAccount = new CXCoinAccount();
        cxCoinAccount.setName("Account4");
        cxCoinAccount.setPassword("1123456");
        cxCoinAccount.setCoin(100D);
        cxCoinAccount.setUserInfo(userInfoDao.findOne("4"));
        cxCoinAccountDao.save(cxCoinAccount);
        
        CXCoinAccount cxCoinAccount2 = cxCoinAccountDao.findOne(cxCoinAccount.getId());
        assertThat(cxCoinAccount).isEqualTo(cxCoinAccount2);
    }
    
    @Test
    public void test_find_byNameAndPasswordAndUserInfo() {
        UserInfo userInfo = userInfoDao.findOne("1");
        CXCoinAccount cxCoinAccount = cxCoinAccountDao.findByNameAndPasswordAndUserInfo("Account1", "123456", userInfo);
        assertThat(cxCoinAccount).isNotNull();
        assertThat(cxCoinAccount.getName()).isEqualTo("Account1");
    }
}
