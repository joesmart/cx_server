package com.server.cx.service.cx;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.Contacts;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class CXAppServiceTest extends SpringTransactionalTestCase {
    public static final Logger LOGGER = LoggerFactory.getLogger(CXAppServiceTest.class);
    @Autowired
    private CXAppService cxAppService;

    @Test
    public void test_query_cxappuser_byimsi() throws Exception {
        List<Contacts> contacts  = cxAppService.queryCXAppUserByImsi("13146001000");
        assertThat(contacts).isNotNull();
        assertThat(contacts.size()).isEqualTo(3);
        assertThat(contacts.get(0).getName()).isEqualTo("Lebron James");
    }
}
