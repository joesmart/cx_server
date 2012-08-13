package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.cl.cx.platform.dto.Actions;
import com.sun.jersey.api.client.ClientResponse;

public class ActionResourceTest extends BasicJerseyTest {
    @Autowired
    private String baseHostAddress;
    @Autowired
    private String restURL;

    @Test
    public void test_query_all_actions() {
        resource = resource.path("/actions");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .get(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        Actions actions = response.getEntity(Actions.class);
        assertThat(actions.getVersionURL()).isEqualTo("http://localhost:8080/CXServer/" + "rs/" + "upgrade");
        assertThat(actions.getRecommendURL()).isEqualTo("http://localhost:8080/CXServer/" + "rs/" + "none" + "/graphicInfos?recommend=true");
    }
    
    @Test
    public void test_query_all_actions_by_imsi() {
        resource = resource.path("/actions/13146001000");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .get(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        Actions actions = response.getEntity(Actions.class);
        assertThat(actions.getVersionURL().getHref()).isEqualTo("http://localhost:8080/CXServer/" + "rs/" + "upgrade");
        assertThat(actions.getRecommendURL().getHref()).isEqualTo("http://localhost:8080/CXServer/" + "rs/" + "13146001000" + "/graphicInfos?recommend=true");
    }
}
