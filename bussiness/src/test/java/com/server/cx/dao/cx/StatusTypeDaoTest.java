package com.server.cx.dao.cx;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.StatusType;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class StatusTypeDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private StatusTypeDao statusTypeDao;
    
    @Test
    public void test_find_all_status_types() {
        List<StatusType> statusTypes = (List<StatusType>) statusTypeDao.findAll();
        assertThat(statusTypes.size()).isEqualTo(9);
        assertThat(statusTypes.get(0).getName()).isEqualTo("飞机上");
    }
    
    @Test
    public void test_find_one_successful() {
        StatusType statusType = statusTypeDao.findOne(1L);
        assertThat(statusType.getName()).isEqualTo("飞机上");
    }
}