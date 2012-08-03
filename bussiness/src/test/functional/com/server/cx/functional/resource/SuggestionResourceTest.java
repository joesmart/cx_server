package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.constants.Constants;
import com.sun.jersey.api.client.ClientResponse;

public class SuggestionResourceTest extends BasicJerseyTest {
    @Test
    public void test_add_suggestion() {
        resource = resource.path("suggestion/13146001006");
        String content = "Nice to meet you";
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription = response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.SUCCESS_FLAG);
    }
    
    @Test
    public void test_get_all_suggestions() {
        resource = resource.path("suggestion");
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        String result = response.getEntity(String.class);
        System.out.println("result = " + result);
    }
}
