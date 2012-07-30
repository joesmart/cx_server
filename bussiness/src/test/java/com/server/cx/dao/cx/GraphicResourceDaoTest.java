package com.server.cx.dao.cx;

import com.server.cx.entity.cx.GraphicResource;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 上午11:30
 * FileName:GraphicResourceDaoTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class GraphicResourceDaoTest extends SpringTransactionalTestCase {
    @Autowired
    GraphicResourceDao graphicResourceDao;

    @Autowired
    GraphicInfoDao graphicInfoDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_get_GraphicResource_when_query_it(){
        List<GraphicResource> graphicResourceList =  graphicResourceDao.findAll();
        Assertions.assertThat(graphicResourceList.size()).isGreaterThan(0);
        Assertions.assertThat(graphicResourceList.get(0).getGraphicInfo()).isNotNull();
    }
}
