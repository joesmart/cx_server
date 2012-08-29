package com.server.cx.dao.cx;

import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.GraphicResource;
import com.server.cx.entity.cx.UserCommonMGraphic;
import com.server.cx.entity.cx.UserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午3:33
 * FileName:UserCommonMGraphicDaoTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserCommonMGraphicDaoTest extends SpringTransactionalTestCase {

    @Autowired
    private UserCommonMGraphicDao userCommonMGraphicDao;


    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private GraphicInfoDao graphicInfoDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_save_new_userCommonMGraphic_successful() {
        UserInfo userInfo = userInfoDao.findOne("1");
        GraphicInfo graphicInfo = graphicInfoDao.findOne("4028b88138d5e5e50138d5e5f2800003");
        UserCommonMGraphic userCommonMGraphic = new UserCommonMGraphic();
        userCommonMGraphic.setCommon(true);
        List<GraphicResource> graphicResourceList = graphicInfo.getGraphicResources();
        if(graphicResourceList != null && graphicResourceList.size()>0){
            userCommonMGraphic.setGraphicResource(graphicResourceList.get(0));
        }
        userCommonMGraphic.setName("abc");
        userCommonMGraphic.setSignature("abcdefg");
        userCommonMGraphic.setUserInfo(userInfo);
        UserCommonMGraphic userCommonMGraphic1 = userCommonMGraphicDao.save(userCommonMGraphic);
        assertThat(userCommonMGraphic1.getId()).isNotEmpty();
    }

}
