package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.server.cx.constants.Constants;
import com.cl.cx.platform.dto.VersionInfoDTO;
import com.sun.jersey.api.client.ClientResponse;

public class VersionInfoResourceTest extends BasicJerseyTest{

    @Test
    public void test_checkIsTheLatestVersion() {
        resource = resource.path("upgrade").queryParam("version", "3.0.110");
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        VersionInfoDTO versionInfoDTO = response.getEntity(VersionInfoDTO.class);
        assertThat(versionInfoDTO.getFlag()).isEqualTo(Constants.SERVER_HAVE_NEW_VERSION);
    }
    
}
