package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.cl.cx.platform.dto.DataPage;
import com.sun.jersey.api.client.ClientResponse;

public class CXAppResourceTest extends BasicJerseyTest {
    @Test
    public void test_getCXAppUsersByImsi() {
        resource = resource.path("/13146001000/cxapp");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        DataPage dataPage = response.getEntity(DataPage.class);
        assertThat(dataPage.getItems().size()).isEqualTo(3);
    }
}
