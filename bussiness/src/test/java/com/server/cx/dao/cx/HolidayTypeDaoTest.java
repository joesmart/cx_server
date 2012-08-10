package com.server.cx.dao.cx;

import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.HolidayType;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class HolidayTypeDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private HolidayTypeDao holidayTypeDao;
    
    @Test
    public void test_find_all_holiday_types() {
        List<HolidayType> holidayTypes = (List<HolidayType>) holidayTypeDao.findAll();
        assertThat(holidayTypes.size()).isEqualTo(19);
        assertThat(holidayTypes.get(0).getName()).isEqualTo("七夕");
    }
    
    @Test
    public void test_find_one() {
        HolidayType holidayType = holidayTypeDao.findOne(19L);
        assertThat(holidayType).isNotNull();
        assertThat(holidayType.getName()).isEqualTo("父亲节");
    }
}
