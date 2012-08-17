package com.server.cx.service.cx;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.RegisterDTO;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.UserInfo;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class RegisterServiceTest extends SpringTransactionalTestCase {
    @Autowired
    private RegisterService registerService;
    
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Test
    public void should_register_successful() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setDeviceId("xxx-509");
        registerDTO.setUserAgent("userAgent");
        registerDTO.setImsi("111122223333");
        OperationDescription operationDescription = registerService.register(registerDTO, "33332222111");
        assertThat(operationDescription.getDealResult()).isEqualTo("success");

        UserInfo info = userInfoDao.findByImsi("33332222111");
        System.out.println("info = " + info);
        OperationDescription operationDescription2 = registerService.register(registerDTO, "33332222111");
        assertThat(operationDescription2.getDealResult()).isEqualTo("registered");
    }
}
