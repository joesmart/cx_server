package com.server.cx.service.cx;

import com.cl.cx.platform.dto.MGraphicDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

/**
 * User: yanjianzou
 * Date: 12-8-3
 * Time: 下午4:57
 * FileName:UserCommonMGraphicServiceTest
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserCommonMGraphicServiceTest extends SpringTransactionalTestCase {

    private MGraphicDTO mGraphicDTO;
    private String imsi;

    @Before
    public void setUp() throws Exception {
        imsi = "13146001001";
        mGraphicDTO = new MGraphicDTO();
        mGraphicDTO.setName("test");
        mGraphicDTO.setSignature("abc");
        mGraphicDTO.setGraphicInfoId("4028b88138d5e5e50138d5e5f2800006");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateUserCommonMGraphic() throws Exception {


    }

    @Test
    public void testEditUserCommonMGraphic() throws Exception {

    }

    @Test
    public void testDisableUserCommonMGraphic() throws Exception {

    }
}
