package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserSpecialMGraphic;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: yanjianzou
 * Date: 12-8-7
 * Time: 上午9:51
 * FileName:UserSpecialMGraphicDaoTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserSpecialMGraphicDaoTest extends SpringTransactionalTestCase {

    @Autowired
    private UserSpecialMGraphicDao mGraphicDao;

    @Test
    public void should_cant_load_common_mgraphic_when_query_common_mgraphic_by_id(){
        UserSpecialMGraphic userSpecialMGraphic =  mGraphicDao.findOne("2");
        assertThat(userSpecialMGraphic).isNotNull();
    }
}
