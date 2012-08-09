package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataItem;
import com.google.common.base.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: yanjianzou
 * Date: 12-8-8
 * Time: 下午9:24
 * FileName:CallingServiceTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class CallingServiceTest extends SpringTransactionalTestCase {
    @Autowired
    private CallingService callingService;
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_get_userCommonMGraphic_when_call_specialNo(){
        DataItem dataItem = callingService.getCallingMGraphic(Optional.of("13146001003"), Optional.of("10086"));
        assertThat(dataItem).isNotNull();
        assertThat(dataItem.getMGraphicId()).isEqualTo("4");
        System.out.println(dataItem.toString());
    }

    @Test
    public void should_get_userCommonMGraphic_when_call_not_register_user(){
        DataItem dataItem = callingService.getCallingMGraphic(Optional.of("13146001003"), Optional.of("100861"));
        assertThat(dataItem).isNotNull();
        assertThat(dataItem.getMGraphicId()).isEqualTo("3");
        System.out.println(dataItem.toString());
    }

    @Test
    public void should_get_userSpecialMGraphic_when_phoneNo_is_set(){
        DataItem dataItem = callingService.getCallingMGraphic(Optional.of("13146001003"), Optional.of("1512581470"));
        assertThat(dataItem).isNotNull();
        assertThat(dataItem.getMGraphicId()).isEqualTo("2");
        System.out.println(dataItem.toString());
    }

    @Test
    public void should_get_MGraphic_when_call_common_mGraphic(){
        DataItem dataItem = callingService.getCallingMGraphic(Optional.of("13146001002"), Optional.of("1512581470"));
        assertThat(dataItem).isNotNull();
        assertThat(dataItem.getMGraphicId()).isEqualTo("1");
        System.out.println(dataItem.toString());
    }
}
