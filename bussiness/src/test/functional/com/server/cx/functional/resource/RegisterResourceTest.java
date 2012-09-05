package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.cl.cx.platform.dto.ContactInfoDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.RegisterDTO;
import com.cl.cx.platform.dto.RegisterOperationDescription;
import com.server.cx.constants.Constants;
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
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.SUCCESS_FLAG);
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
        RegisterOperationDescription operationDescription = response.getEntity(RegisterOperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.SUCCESS_FLAG);
        assertThat(operationDescription.getForceSMS()).isEqualTo(true);
    }
    
    @Test
    public void test_register_registered_no_phoneNo() {
        resource = resource.path("/register");
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setDeviceId("xxx-509");
        registerDTO.setUserAgent("userAgent");
        registerDTO.setImsi("111122223333");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, registerDTO);
        assertThat(response.getStatus()).isEqualTo(200);
        RegisterOperationDescription operationDescription = response.getEntity(RegisterOperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.ERROR_FLAG);
        assertThat(operationDescription.getForceSMS()).isEqualTo(true);
    }
    
    @Test
    public void test_update_sms_flag() {
        resource = resource.path("/register/111122223333/sms");
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setForceSMS(true);
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, registerDTO);
        assertThat(response.getStatus()).isEqualTo(200);
        String reuslt = response.getEntity(String.class);
        System.out.println("result = " + reuslt);
    }
    
}
