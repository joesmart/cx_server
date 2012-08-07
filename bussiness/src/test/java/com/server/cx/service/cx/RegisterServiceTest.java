package com.server.cx.service.cx;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.cl.cx.platform.dto.OperationDescription;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles(profiles = {"test"})
public class RegisterServiceTest extends SpringTransactionalTestCase {
    @Autowired
    private RegisterService registerService;
    
    @Test
    public void should_register_successful() {
        OperationDescription operationDescription = registerService.register("111122223333", "33332222111");
        assertThat(operationDescription.getDealResult()).isEqualTo("用户注册成功");
        
        OperationDescription operationDescription2 = registerService.register("111122223333", "33332222111");
        assertThat(operationDescription2.getDealResult()).isEqualTo("用户已经注册");
    }
}
