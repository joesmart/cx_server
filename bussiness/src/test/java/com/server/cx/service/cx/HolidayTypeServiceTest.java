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
public class HolidayTypeServiceTest extends SpringTransactionalTestCase {
    @Autowired
    private HolidayTypeService holidayTypeService;
    
    @Test
    public void test_query_all_holiday_types() {
        DataPage dataPage = holidayTypeService.queryAllHolidayTypes("11122");
        assertThat(dataPage.getLimit()).isEqualTo(19);
        assertThat(dataPage.getItems().get(0).getName()).isEqualTo("七夕");
    }
}
