package com.server.cx.service.cx;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.dao.cx.UserStatusMGraphicDao;
import com.server.cx.entity.cx.UserStatusMGraphic;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class UserDailyServiceTest extends SpringTransactionalTestCase {
    @Autowired
    private UserDailyService userDailyService;

    @Autowired
    private UserStatusMGraphicDao userStatusMGraphicDao;

    @Test
    public void test_clearUserStatusMGraphic() {
        List<UserStatusMGraphic> userStatusMGraphics = userStatusMGraphicDao.findAll();
        assertThat(userStatusMGraphics).isNotNull();
        assertThat(userStatusMGraphics.size()).isGreaterThan(1);
        userDailyService.doDailyTask();
        List<UserStatusMGraphic> userStatusMGraphics2 = userStatusMGraphicDao.findAll();
        assertThat(userStatusMGraphics2.size()).isEqualTo(0);
    }
}
