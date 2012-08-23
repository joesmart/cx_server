package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.constants.Constants;
import com.sun.jersey.api.client.ClientResponse;

public class UserCommonMGraphicResourceTest extends BasicJerseyTest{
    @Test
    public void test_upload_image() throws IOException {
        resource = resource.path("/13146001000/userCommonMGraphic/upload");
        InputStream inputStream = UserCommonMGraphicResourceTest.class.getResourceAsStream("/Winter.jpg");
        System.out.println("inputStream = " + inputStream);
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, inputStream);
        OperationDescription operationDescription = response.getEntity(OperationDescription.class);
        System.out.println("operationDescription = " + operationDescription);
        assertThat(operationDescription.getStatusCode()).isEqualTo(201);
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.SUCCESS_FLAG);
    }
    
    @Test
    public void test_get_image() throws IOException {
        InputStream inputStream = UserCommonMGraphicResourceTest.class.getResourceAsStream("/Winter.jpg");
        System.out.println("inputStream = " + inputStream);
        assertThat(inputStream).isNotNull();
    }
    
}
