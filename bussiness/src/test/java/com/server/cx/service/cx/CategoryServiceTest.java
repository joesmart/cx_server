package com.server.cx.service.cx;

import com.server.cx.dto.DataPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: yanjianzou
 * Date: 12-7-30
 * Time: 下午4:57
 * FileName:CategoryServiceTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class CategoryServiceTest extends SpringTransactionalTestCase {
    public static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceTest.class);
    @Autowired
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_get_all_category_data_when_query_all_category() throws Exception {
        DataPage dataPage  =categoryService.queryAllCategoryData("abcdefg");
        assertThat(dataPage).isNotNull();
        assertThat(dataPage.getItems().size()).isEqualTo(6);
        LOGGER.info(dataPage.toString());
    }
}
