package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.server.cx.constants.Constants;
import com.server.cx.dto.Result;
import com.sun.jersey.api.client.ClientResponse;

public class VersionInfoResourceTest extends BasicJerseyTest{
    public VersionInfoResourceTest() throws TestContainerException {
        super();
    }

    @Test
    public void test_checkIsTheLatestVersion() {
        resource = resource.path("upgrade");
        Result result = new Result();
        result.setImsi("13146001000");
        result.setVersion("3.0.110");
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, result);
        assertThat(response.getStatus()).isEqualTo(200);
        Result responseResult = response.getEntity(Result.class);
        assertThat(responseResult.getFlag()).isEqualTo(Constants.SERVER_HAVE_NEWVERION);
    }
    
    @Test
    public void test_no_imsi_exception() {
        resource = resource.path("upgrade");
        Result result = new Result();
        result.setVersion("3.0.110");
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, result);
        assertThat(response.getStatus()).isEqualTo(500);
    }

}
