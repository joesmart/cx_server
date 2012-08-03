package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.UploadContactDTO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.server.cx.constants.Constants;
import com.server.cx.data.ContactsData;
import com.sun.jersey.api.client.ClientResponse;

public class ContactsResourceTest extends BasicJerseyTest {
    @Test
    public void test_check_upload_contacts() throws JsonGenerationException, JsonMappingException, IOException {
        resource = resource.path("/contacts");
        UploadContactDTO uploadContactDTO = new UploadContactDTO();
        uploadContactDTO.setImsi("13146001010");
        uploadContactDTO.setContactPeopleInfos(ContactsData.buildContactPeopleInfos());
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, uploadContactDTO);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription = response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.SUCCESS_FLAG);
    }
}
