package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.cl.cx.platform.dto.ContactInfoDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.RegisterDTO;
import com.sun.jersey.api.client.ClientResponse;

public class RegisterResourceTest extends BasicJerseyTest {
    @Test
    public void test_register_resource() {
        resource = resource.path("/111122223333/register");
        ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
        contactInfoDTO.setPhoneNo("33332222111");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, contactInfoDTO);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription = response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo("success");
    }
    
    @Test
    public void test_register_resource_register_dto() {
        resource = resource.path("/register");
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setDeviceId("xxx-509");
        registerDTO.setUserAgent("userAgent");
        registerDTO.setImsi("111122223333");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, registerDTO);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription = response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo("success");
    }
}
