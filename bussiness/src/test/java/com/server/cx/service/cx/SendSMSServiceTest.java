package com.server.cx.service.cx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: yanjianzou
 * Date: 9/7/12
 * Time: 11:41 AM
 * FileName:SendSMSServiceTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class SendSMSServiceTest extends SpringTransactionalTestCase {
    @Autowired
    private SendSMSService sendSMSService;
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSendSMS() throws Exception {
        Long[]  result = sendSMSService.sendSMS("123123");
        assertThat(result).isNotNull();
    }
}
