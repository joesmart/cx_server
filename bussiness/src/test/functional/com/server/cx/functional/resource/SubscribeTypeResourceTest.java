package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
import com.sun.jersey.api.client.ClientResponse;

public class SubscribeTypeResourceTest extends BasicJerseyTest {
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
        resource = resource.path("/13146001001/holidayTypes");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription =  response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo("用户已经订购");
    }
    
    @Test
    public void test_subscribe_function_type_holiday_no_enough_money_exception() {
        resource = resource.path("/13146001010/holidayTypes");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription =  response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo("余额不足");
    }
    
    @Test
    public void test_subscribe_status_type_status_successful() {
        resource = resource.path("/13146001005/statusTypes");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        DataPage dataPage =  response.getEntity(DataPage.class);
        assertThat(dataPage.getLimit()).isEqualTo(9);
        assertThat(dataPage.getItems().get(0).getName()).isEqualTo("飞机上");
    }
    
    @Test
    public void test_subscribe_status_type_status_subscribed_exception() {
        resource = resource.path("/13146001000/statusTypes");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription =  response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo("用户已经订购");
    }
    
    @Test
    public void test_subscribe_status_type_status_no_enough_money_exception() {
        resource = resource.path("/13146001010/statusTypes");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription =  response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo("余额不足");
    }
    
    
    @Test
    public void test_subscribe_custom_type_custom_successful() {
        resource = resource.path("/13146001000/customMGraphics");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        DataPage dataPage =  response.getEntity(DataPage.class);
        assertThat(dataPage.getLimit()).isEqualTo(5);
        assertThat(dataPage.getItems().get(0).getName()).isEqualTo("custom1");
    }
    
    @Test
    public void test_subscribe_custom_type_custom_subscribed_exception() {
        resource = resource.path("/13146001003/customMGraphics");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription =  response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo("用户已经订购");
    }
    
    @Test
    public void test_subscribe_custom_type_custom_no_enough_money_exception() {
        resource = resource.path("/13146001010/customMGraphics");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription =  response.getEntity(OperationDescription.class);
        assertThat(operationDescription.getDealResult()).isEqualTo("余额不足");
    }
}
