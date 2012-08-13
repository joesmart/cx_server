package com.server.cx.service.cx;

import com.server.cx.dao.cx.HolidayTypeDao;
import com.server.cx.entity.cx.HolidayType;
import org.fest.assertions.Assertions;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.util.Date;

/**
 * User: yanjianzou
 * Date: 12-8-13
 * Time: 上午10:08
 * FileName:HolidayServiceTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class HolidayServiceTest extends SpringTransactionalTestCase {

    @Autowired
    private HolidayService holidayService;
    @Autowired
    private HolidayTypeDao holidayTypeDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_get_2012_christmas_eve() throws Exception {
        HolidayType holidayType = holidayTypeDao.findOne(7L);
        Date christmasEve = holidayService.getAppropriateHolidayDate(holidayType);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        Date date = LocalDate.parse("2012-12-24",dateTimeFormatter).toDate();

        Assertions.assertThat(christmasEve).isEqualTo(date);

    }

    @Test
    public void should_get_2012_china_lover_day() throws Exception {
        HolidayType holidayType = holidayTypeDao.findOne(1L);
        Date christmasEve = holidayService.getAppropriateHolidayDate(holidayType);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        Date date = LocalDate.parse("2012-8-23",dateTimeFormatter).toDate();
        Assertions.assertThat(christmasEve).isEqualTo(date);
    }

    @Test
    public void should_get_null_when_holiday_type_not_exists() throws Exception {
        HolidayType holidayType = holidayTypeDao.findOne(41L);
        Date appropriateDate = holidayService.getAppropriateHolidayDate(holidayType);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        Date date = LocalDate.parse("2012-8-23",dateTimeFormatter).toDate();
        Assertions.assertThat(appropriateDate).isNull();
    }
}
