package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataPage;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.util.concurrent.ExecutionException;

/**
 * User: yanjianzou
 * Date: 12-7-31
 * Time: 上午10:16
 * FileName:GraphicInfoServiceTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class GraphicInfoServiceTest extends SpringTransactionalTestCase {

    public static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceTest.class);

    @Autowired
    private GraphicInfoService graphicInfoService;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_get_graphicInfos_when_query_by_categoryId() throws ExecutionException {
        DataPage dataPage =  graphicInfoService.findGraphicInfoDataPageByCategoryId("abcedefg", 1L, 0, 2);
        Assertions.assertThat(dataPage).isNotNull();
        LOGGER.info(dataPage.toString());
    }
    
    @Test
    public void should_find_status_graphicInfos_exist_by_imsi() {
        DataPage dataPage = graphicInfoService.findStatusGraphicInfosByImsi("13146001000", 1L, 0, 3);
        Assertions.assertThat(dataPage).isNotNull();
        LOGGER.info(dataPage.toString());
        Assertions.assertThat(dataPage.getItems().size()).isEqualTo(4);
        Assertions.assertThat(dataPage.getItems().get(0).getName()).isEqualTo("彩像83");
        Assertions.assertThat(dataPage.getItems().get(1).getName()).isEqualTo("彩像86");
    }
    
    @Test
    public void should_find_status_graphicInfos_not_exist_by_imsi() {
        DataPage dataPage = graphicInfoService.findStatusGraphicInfosByImsi("13146001001", 1L, 0, 3);
        Assertions.assertThat(dataPage).isNotNull();
        LOGGER.info(dataPage.toString());
        Assertions.assertThat(dataPage.getItems().size()).isEqualTo(3);
        Assertions.assertThat(dataPage.getItems().get(0).getName()).isEqualTo("彩像86");
    }
    
    @Test
    public void should_find_holiday_graphicInfos_exist_by_imsi() throws ExecutionException {
        DataPage dataPage = graphicInfoService.findHolidayGraphicInfosByImsi("13146001000", 1L, 0, 3);
        Assertions.assertThat(dataPage).isNotNull();
        LOGGER.info(dataPage.toString());
        Assertions.assertThat(dataPage.getItems().size()).isEqualTo(4);
        Assertions.assertThat(dataPage.getItems().get(0).getName()).isEqualTo("彩像93");
        Assertions.assertThat(dataPage.getItems().get(1).getName()).isEqualTo("彩像96");
    }
    
    @Test
    public void should_find_holiday_graphicInfos_not_exist_by_imsi() throws ExecutionException {
        DataPage dataPage = graphicInfoService.findHolidayGraphicInfosByImsi("13146001001", 1L, 0, 3);
        Assertions.assertThat(dataPage).isNotNull();
        LOGGER.info(dataPage.toString());
        Assertions.assertThat(dataPage.getItems().size()).isEqualTo(3);
        Assertions.assertThat(dataPage.getItems().get(0).getName()).isEqualTo("彩像96");
    }
    
    
}
