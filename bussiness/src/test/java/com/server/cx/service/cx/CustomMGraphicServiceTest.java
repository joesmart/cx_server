package com.server.cx.service.cx;

import static org.fest.assertions.Assertions.assertThat;
import org.fest.assertions.Fail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.cl.cx.platform.dto.DataPage;
import com.server.cx.exception.NotSubscribeTypeException;
import com.server.cx.service.cx.impl.CustomMGraphicServiceImpl;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class CustomMGraphicServiceTest extends SpringTransactionalTestCase {
    @Autowired
    @Qualifier(value="customMGraphicService")
    private MGraphicService customMGraphicService;
    
    @Test
    public void test_query_user_MGraphic_subscribed() {
        DataPage dataPage = ((CustomMGraphicServiceImpl)customMGraphicService).queryUserMGraphic("13146001000");
        assertThat(dataPage.getLimit()).isEqualTo(5);
        assertThat(dataPage.getItems().get(0).getName()).isEqualTo("custom1");
    }
    
    @Test
    public void test_query_user_MGraphic_unsubscribed() {
        try {
            DataPage dataPage = ((CustomMGraphicServiceImpl)customMGraphicService).queryUserMGraphic("13146001006");
            Fail.fail("没有抛出异常");
        } catch(NotSubscribeTypeException e) {
            
        }
    }
}
