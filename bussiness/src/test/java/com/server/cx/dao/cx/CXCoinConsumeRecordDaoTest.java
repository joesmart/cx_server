package com.server.cx.dao.cx;

import java.util.List;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.CXCoinConsumeRecord;
import com.server.cx.entity.cx.UserInfo;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CXCoinConsumeRecordDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private CXCoinConsumeRecordDao cxCoinConsumeRecordDao;
    
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Test
    public void test_save() {
        UserInfo userInfo = userInfoDao.findOne("4");
        CXCoinConsumeRecord coinConsumeRecord = new CXCoinConsumeRecord();
        coinConsumeRecord.setUserInfo(userInfo);
        coinConsumeRecord.setCxCoin(5D);
        cxCoinConsumeRecordDao.save(coinConsumeRecord);
        
        CXCoinConsumeRecord dbRecord = cxCoinConsumeRecordDao.findOne(coinConsumeRecord.getId());
        Assertions.assertThat(dbRecord).isEqualTo(coinConsumeRecord);
    }
    
    @Test
    public void test_delete_all() {
        cxCoinConsumeRecordDao.deleteAll();
        List<CXCoinConsumeRecord> coinConsumeRecords = (List<CXCoinConsumeRecord>) cxCoinConsumeRecordDao.findAll();
        Assertions.assertThat(coinConsumeRecords).isEmpty();
        
    }
    
    @Test
    public void test_find_by_userinfo() {
        UserInfo userInfo = userInfoDao.findOne("3");
        CXCoinConsumeRecord cxCoinConsumeRecord  = cxCoinConsumeRecordDao.findByUserInfo(userInfo);
        Assertions.assertThat(cxCoinConsumeRecord).isNotNull();
        Assertions.assertThat(cxCoinConsumeRecord.getUserInfo().getImsi()).isEqualTo("13146001003");
    }
}
