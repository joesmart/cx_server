package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserInfo;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-9
 * Time: 下午3:52
 * FileName:UserFavoritesDaoTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserFavoritesDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private UserFavoritesDao userFavoritesDao;
    @Autowired
    private UserInfoDao userInfoDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_get_the_user_collected_GraphicInfo_ids_success(){
        UserInfo userInfo = userInfoDao.findOne("2");
        List<String> graphicInfoIds =  userFavoritesDao.getGraphicIdListByUserInfo(userInfo);
        Assertions.assertThat(graphicInfoIds.size()).isGreaterThanOrEqualTo(2);
    }
}
