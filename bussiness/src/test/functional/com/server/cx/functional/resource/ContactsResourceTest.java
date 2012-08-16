package com.server.cx.functional.resource;

import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.ContactsDTO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.cx.constants.Constants;
import com.server.cx.data.ContactsData;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

public class ContactsResourceTest extends BasicJerseyTest {
    @Test
    public void test_check_upload_contacts() throws JsonGenerationException, JsonMappingException, IOException {
        resource = resource.path("/13146001010/contacts");
        ContactsDTO uploadContactDTO = new ContactsDTO();
        uploadContactDTO.setContactInfos(ContactsData.buildContactPeopleInfos());
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(uploadContactDTO);
        System.out.println("uploadContactDTO = " + result);
        
//        String test = "{\"@type\":\"com.cl.cx.platform.dto.ContactsDTO\",\"contactInfos\":[{\"contactName\":\"啊是\",\"phoneNo\":\"13512345678\"},{\"contactName\":\"大家\",\"phoneNo\":\"13612345678\"},{\"contactName\":\"简单\",\"phoneNo\":\"12634567894\"}]}";
//        String test = "{\"@type\" : \"com.cl.cx.platform.dto.ContactsDTO\",\"contactInfos\" : [{\"@type\" : \"com.cl.cx.platform.dto.ContactInfoDTO\",\"contactName\" : \"King Gavin\",\"phoneNo\" : \"1512581111\"}]}";
        String test ="{\"@type\":\"com.cl.cx.platform.dto.ContactsDTO\",\"contactInfos\":[{\"@type\" : \"com.cl.cx.platform.dto.ContactInfoDTO\", \"contactName\":\"啊是\",\"phoneNo\":\"13512345678\"}, {\"@type\" : \"com.cl.cx.platform.dto.ContactInfoDTO\", \"contactName\":\"大家\",\"phoneNo\":\"13612345678\"}]}";
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, test);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription = response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.SUCCESS_FLAG);
    }
    
    @Test
    public void test_getCXAppUsersByImsi() {
        resource = resource.path("/13146001000/contacts");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        ContactsDTO contactDTO =  response.getEntity(ContactsDTO.class);
        assertThat(contactDTO.getContactInfos().size()).isEqualTo(3);
    }
}
