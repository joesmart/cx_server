package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.cl.cx.platform.dto.DataPage;
import com.sun.jersey.api.client.ClientResponse;

public class HolidayTypeResourceTest extends BasicJerseyTest{
    @Test
    public void test_query_all_holiday_types() {
        resource = resource.path("/13146001000/holidayTypes");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        DataPage dataPage =  response.getEntity(DataPage.class);
        assertThat(dataPage.getLimit()).isEqualTo(19);
        assertThat(dataPage.getItems().get(0).getName()).isEqualTo("七夕");
    }
}
