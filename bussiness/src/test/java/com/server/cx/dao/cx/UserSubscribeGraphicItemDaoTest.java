package com.server.cx.dao.cx;

import static org.fest.assertions.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.entity.cx.UserSubscribeGraphicItem;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserSubscribeGraphicItemDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private UserSubscribeGraphicItemDao userSubscribeGraphicItemDao;
    
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    private GraphicInfoDao graphicInfoDao;
    
    @Test
    public void test_find_by_userInfo_and_status_type() {
        UserInfo userInfo = userInfoDao.findOne("1");
        GraphicInfo graphicInfo = graphicInfoDao.findOne("4028b88138d5e5e50138d5e5f2800001");
        List<UserSubscribeGraphicItem> userSubscribeGraphicItems = userSubscribeGraphicItemDao.findByUserInfoAndGraphicInfo(userInfo, graphicInfo);
        assertThat(userSubscribeGraphicItems).isNotNull();
        assertThat(userSubscribeGraphicItems.size()).isEqualTo(1);
        assertThat(userSubscribeGraphicItems.get(0).getGraphicInfo().getName()).isEqualTo("彩像11");
    }

}
