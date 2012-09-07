package com.server.cx.dao.cx;

import com.server.cx.entity.cx.SmsMessage;
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
 * Date: 9/7/12
 * Time: 4:39 PM
 * FileName:SmsMessageDaoTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class SmsMessageDaoTest extends SpringTransactionalTestCase {
    @Autowired
    private SmsMessageDao smsMessageDao;
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFindByIsSendAndFromMobileNo() throws Exception {
        List<SmsMessage> smsMessageList =  smsMessageDao.findByIsSendAndFromMobileNo(false,"123123");
        assertThat(smsMessageList.size()).isEqualTo(8);
    }
}
