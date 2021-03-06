package com.server.cx.dao.cx;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.CustomSubscribeType;
import com.server.cx.entity.cx.StatusSubscribeType;
import com.server.cx.entity.cx.SubscribeType;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class SubscribeTypeDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private SubscribeTypeDao subscribeTypeDao;

    @Test
    public void test_find_holiday_subscribe_type() {
        SubscribeType subscribeType = subscribeTypeDao.findSubscribeType("holiday");
        assertThat(subscribeType).isNotNull();
        assertThat(subscribeType.getName()).isEqualTo("节日包");
    }
    
    @Test
    public void test_find_status_subscribe_type() {
        StatusSubscribeType statusSubscribeType = subscribeTypeDao.findStatusSubscribeType();
        assertThat(statusSubscribeType).isNotNull();
        assertThat(statusSubscribeType.getName()).isEqualTo("状态包");
    }
    
    @Test
    public void test_find_custom_subscribe_type() {
        CustomSubscribeType customSubscribeType = subscribeTypeDao.findCustomSubscribeType();
        assertThat(customSubscribeType).isNotNull();
        assertThat(customSubscribeType.getName()).isEqualTo("自主包");
    }
}
