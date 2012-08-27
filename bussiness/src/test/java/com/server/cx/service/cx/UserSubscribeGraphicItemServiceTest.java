package com.server.cx.service.cx;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.fest.assertions.Assertions;
import org.fest.assertions.Fail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dao.cx.UserSubscribeRecordDao;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeRecord;
import com.server.cx.exception.MoneyNotEnoughException;
import com.server.cx.exception.NotSubscribeTypeException;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class UserSubscribeGraphicItemServiceTest extends SpringTransactionalTestCase {
    @Autowired
    private UserSubscribeGraphicItemService userSubscribeGraphicItemService;
    
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    private GraphicInfoDao graphicInfoDao;
    
    @Autowired
    private UserSubscribeRecordDao userSubscribeRecordDao;
    
    @Test
    public void test_subscribeGraphicItem() {
        List<UserSubscribeRecord> records1 = (List<UserSubscribeRecord>) userSubscribeRecordDao.findAll();
        UserInfo userInfo = userInfoDao.findOne("2");
        userSubscribeGraphicItemService.subscribeGraphicItem(userInfo.getImsi(), "4028b88138d5e5e50138d5e5f2800093");
        UserInfo userInfo2 = userInfoDao.findOne("2");
        assertEquals(30d, userInfo2.getTotleMoney().doubleValue(), 1e-3);
        List<UserSubscribeRecord> records2 = (List<UserSubscribeRecord>) userSubscribeRecordDao.findAll();
        Assertions.assertThat(records1.size()).isEqualTo(records2.size() - 1);
        Assertions.assertThat(records2.get(records2.size() - 1).getDescription()).isEqualTo("订购");
    }
    
    @Test
    public void test_subscribeGraphicItem_not_enough_money() {
        try {
            UserInfo userInfo = userInfoDao.findOne("2");
            userSubscribeGraphicItemService.subscribeGraphicItem(userInfo.getImsi(), "4028b88138d5e5e50138d5e5f2800094");
            Fail.fail("没有抛出异常");
        } catch(MoneyNotEnoughException e) {
        }
    }

    @Test
    public void test_subscribeGraphicItem_userinfo_grapicInfo() {
        List<UserSubscribeRecord> records1 = (List<UserSubscribeRecord>) userSubscribeRecordDao.findAll();
        UserInfo userInfo = userInfoDao.findOne("2");
        GraphicInfo graphicInfo = graphicInfoDao.findOne("4028b88138d5e5e50138d5e5f2800093");
        userSubscribeGraphicItemService.subscribeGraphicItem(userInfo, graphicInfo);
        UserInfo userInfo2 = userInfoDao.findOne("2");
        assertEquals(30d, userInfo2.getTotleMoney().doubleValue(), 1e-3);
        List<UserSubscribeRecord> records2 = (List<UserSubscribeRecord>) userSubscribeRecordDao.findAll();
        Assertions.assertThat(records1.size()).isEqualTo(records2.size() - 1);
        Assertions.assertThat(records2.get(records2.size() - 1).getDescription()).isEqualTo("订购");
    }

    @Test
    public void testCheckUserSubscribeGraphicItem_successful() {
        UserInfo userInfo = userInfoDao.findOne("1");
        userSubscribeGraphicItemService.checkUserSubscribeGraphicItem(userInfo, "4028b88138d5e5e50138d5e5f2800001");
    }
    
    @Test
    public void testCheckUserSubscribeGraphicItem_exception() {
        try {
            UserInfo userInfo = userInfoDao.findOne("2");
            userSubscribeGraphicItemService.checkUserSubscribeGraphicItem(userInfo, "4028b88138d5e5e50138d5e5f2800001");
            Fail.fail("没有抛出异常");
        } catch(NotSubscribeTypeException e) {
        }
    }

}
