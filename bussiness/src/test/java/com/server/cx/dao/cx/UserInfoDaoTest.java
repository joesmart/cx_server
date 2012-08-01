package com.server.cx.dao.cx;

import org.junit.After;
import org.junit.Before;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

/**
 * User: yanjianzou
 * Date: 12-8-1
 * Time: 下午2:02
 * FileName:UserInfoDaoTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class UserInfoDaoTest extends SpringTransactionalTestCase {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    public void should_get_userinfo_when_find_by_imsi(){

    }
}
