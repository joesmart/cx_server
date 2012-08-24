package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import javax.ws.rs.core.MediaType;
import org.fest.assertions.Assertions;
import org.junit.Test;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
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
    
    @Test
    public void test_query_status_type_exist_user_byId() {
        resource = resource.path("/13146001000/holidayTypes/1").queryParam("offset", "0").queryParam("limit", "3");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        DataPage dataPage =  response.getEntity(DataPage.class);
        Assertions.assertThat(dataPage.getItems().size()).isEqualTo(4);
        Assertions.assertThat(dataPage.getItems().get(0).getName()).isEqualTo("彩像93");
        Assertions.assertThat(dataPage.getItems().get(1).getName()).isEqualTo("彩像96");
        System.out.println("dataPage  = " + dataPage);
    }
    
    @Test
    public void test_query_status_type_not_exist_user_byId() {
        resource = resource.path("/13146001001/holidayTypes/1").queryParam("offset", "0").queryParam("limit", "3");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        DataPage dataPage =  response.getEntity(DataPage.class);
        Assertions.assertThat(dataPage.getItems().size()).isEqualTo(3);
        Assertions.assertThat(dataPage.getItems().get(0).getName()).isEqualTo("彩像96");
        System.out.println("dataPage  = " + dataPage);
    }
    
    @Test
    public void test_subscribe_function_type_holiday_successful() {
        resource = resource.path("/13146001005/holidayTypes");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        DataPage dataPage =  response.getEntity(DataPage.class);
        assertThat(dataPage.getLimit()).isEqualTo(19);
        assertThat(dataPage.getItems().get(0).getName()).isEqualTo("七夕");
    }
    
    @Test
    public void test_subscribe_function_type_holiday_subscribed_exception() {
        resource = resource.path("/13146001001/subsucribe?type=holiday");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription =  response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo("用户已经订购");
    }
    
    @Test
    public void test_subscribe_function_type_holiday_no_enough_money_exception() {
        resource = resource.path("/13146001010/subsucribe?type=holiday");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription =  response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo("余额不足");
    }
}
