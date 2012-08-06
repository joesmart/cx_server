package com.server.cx.dao.cx;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    ContactsDaoTest.class,
    GraphicInfoDaoTest.class,
    GraphicResourceDaoTest.class,
    SuggestionDaoTest.class,
    UserInfoDaoTest.class,
    VersionInfoDaoTest.class
    })
public class DAOSuiteTest {

}
