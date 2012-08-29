package com.server.cx.dao.cx;

import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.UserHolidayMGraphic;
import com.server.cx.entity.cx.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserHolidayMGraphicDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private UserHolidayMGraphicDao userHolidayMGraphicDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private HolidayTypeDao holidayTypeDao;
    
    @Test
    public void test_find_by_userInfo_and_status_type() {
        UserInfo userInfo = userInfoDao.findByImsi("13146001000");
        HolidayType holidayType = holidayTypeDao.findOne(1L);
        List<UserHolidayMGraphic> userHolidayMGraphic = userHolidayMGraphicDao.findByUserInfoAndHolidayType(userInfo, holidayType);
        assertThat(userHolidayMGraphic.size()).isEqualTo(1);
    }

    @Test
    public void test_not_found_item() {
        UserInfo userInfo = userInfoDao.findByImsi("13146001000");
        HolidayType holidayType = holidayTypeDao.findOne(2L);
        List<UserHolidayMGraphic> userHolidayMGraphic = userHolidayMGraphicDao.findByUserInfoAndHolidayType(userInfo,
            holidayType);
        assertThat(userHolidayMGraphic).isEmpty();
    }

}
