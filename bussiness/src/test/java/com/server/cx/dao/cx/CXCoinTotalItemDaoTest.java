package com.server.cx.dao.cx;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.CXCoinTotalItem;
import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CXCoinTotalItemDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private CXCoinTotalItemDao cxCoinTotalItemDao;

    @Test
    public void test_save() {
        CXCoinTotalItem cxCoinTotalItem = new CXCoinTotalItem();
        cxCoinTotalItem.setCxCoinCount(100D);
        cxCoinTotalItemDao.save(cxCoinTotalItem);
        CXCoinTotalItem dbItem = cxCoinTotalItemDao.findOne(cxCoinTotalItem.getId());
        assertThat(cxCoinTotalItem).isEqualTo(dbItem);
    }
}
