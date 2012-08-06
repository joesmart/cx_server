package com.server.cx.functional.resource;

import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.ContactDTO;
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
        ContactDTO uploadContactDTO = new ContactDTO();
        uploadContactDTO.setContactInfos(ContactsData.buildContactPeopleInfos());
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(uploadContactDTO);
        System.out.println("uploadContactDTO = " + result);
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, uploadContactDTO);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription = response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.SUCCESS_FLAG);
    }
}
