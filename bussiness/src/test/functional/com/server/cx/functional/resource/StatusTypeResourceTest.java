package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.cl.cx.platform.dto.DataPage;
import com.sun.jersey.api.client.ClientResponse;

public class StatusTypeResourceTest extends BasicJerseyTest {
    @Test
    public void test_query_all_status_types() {
        resource = resource.path("/13146001000/statusTypes");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        DataPage dataPage =  response.getEntity(DataPage.class);
        assertThat(dataPage.getLimit()).isEqualTo(9);
        assertThat(dataPage.getItems().get(0).getName()).isEqualTo("飞机上");
    }
}
