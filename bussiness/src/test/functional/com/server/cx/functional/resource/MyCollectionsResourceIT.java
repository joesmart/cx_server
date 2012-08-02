package com.server.cx.functional.resource;

import com.cl.cx.platform.dto.IdDTO;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: yanjianzou
 * Date: 12-8-2
 * Time: 下午12:42
 * FileName:MyCollectionsResourceIT
 */
public class MyCollectionsResourceIT extends  BasicJerseyTest{

    @Before
    public void setUp() throws Exception {
        resource = resource();

    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void should_add_userFavorites_success_when_post_IdDTO(){
        IdDTO idDTO = new IdDTO();
        idDTO.setId("4028b88138d5e5e50138d5e5f2800002");
        ClientResponse response = resource.path("13146001001/myCollections").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,idDTO);
        assertThat(response).isNotNull();
    }

    @Test
    public void should_add_userFavorites_failed_when_post_exists_graphinc_resource(){
        IdDTO idDTO = new IdDTO();
        idDTO.setId("4028b88138d5e5e50138d5e5f2800001");
        ClientResponse response = resource.path("13146001001/myCollections").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,idDTO);
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.CONFLICT.getStatusCode());
    }

    @Test
    public void should_delete_userFavorites_success_when_post_exists_graphinc_resource(){
        ClientResponse response = resource.path("13146001001/myCollections/4028b88138e5cc440138e5ccab220000").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.ACCEPTED.getStatusCode());
    }
}
