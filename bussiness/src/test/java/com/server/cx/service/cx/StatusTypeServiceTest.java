package com.server.cx.service.cx;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.cl.cx.platform.dto.DataPage;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class StatusTypeServiceTest extends SpringTransactionalTestCase {
    @Autowired
    private StatusTypeService statusTypeService;
    
    @Test
    public void test_query_all_status_types() {
        DataPage dataPage = statusTypeService.queryAllStatusTypes("11122");
        assertThat(dataPage.getLimit()).isEqualTo(9);
        assertThat(dataPage.getItems().get(0).getName()).isEqualTo("飞机上");
    }
}
