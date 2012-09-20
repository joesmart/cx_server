package com.server.cx.functional.resource;

import static org.fest.assertions.Assertions.assertThat;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import com.cl.cx.platform.dto.CXCoinAccountDTO;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.constants.Constants;
import com.server.cx.entity.cx.CXCoinAccount;
import com.server.cx.functional.resource.BasicJerseyTest;
import com.sun.jersey.api.client.ClientResponse;

public class CXCoinResourceTest extends BasicJerseyTest {
    @Test
    public void test_get_cxcoin_account() {
        resource = resource.path("/13146001000/cxCoin/account");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        CXCoinAccountDTO account = response.getEntity(CXCoinAccountDTO.class);
        System.out.println("account = " + account);
        assertThat(account.getName()).isEqualTo("Account1");
    }
    
    @Test
    public void test_register() {
        resource = resource.path("/13146001010/cxCoin/register");
        CXCoinAccountDTO cxCoinAccountDTO = new CXCoinAccountDTO();
        cxCoinAccountDTO.setName("Account10");
        cxCoinAccountDTO.setPassword("123456");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, cxCoinAccountDTO);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription = response.getEntity(OperationDescription.class);
        System.out.println("operationDescription = " + operationDescription);
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.SUCCESS_FLAG);
    }
    
    @Test
    public void test_login() {
        resource = resource.path("/13146001000/cxCoin/login");
        CXCoinAccountDTO cxCoinAccountDTO = new CXCoinAccountDTO();
        cxCoinAccountDTO.setName("Account1");
        cxCoinAccountDTO.setPassword("123456");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, cxCoinAccountDTO);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription = response.getEntity(OperationDescription.class);
        System.out.println("operationDescription = " + operationDescription);
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.SUCCESS_FLAG);
    }
    
    @Test
    public void test_get_user_cxcoin_records() {
        resource = resource.path("/13146001000/cxCoin/records").queryParam("offset", "0").queryParam("limit", "2");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertThat(response.getStatus()).isEqualTo(200);
        DataPage dataPage = response.getEntity(DataPage.class);
        System.out.println("dataPage = " + dataPage);
        assertThat(dataPage.getItems().size()).isEqualTo(2);
        assertThat(dataPage.getItems().get(0).getTime()).isEqualTo("2012/09/06");
    }
    
    @Test
    public void test_consume_cxcoin() {
        resource = resource.path("/13146001000/cxCoin/consume");
        CXCoinAccountDTO cxCoinAccountDTO = new CXCoinAccountDTO();
        cxCoinAccountDTO.setName("Account1");
        cxCoinAccountDTO.setPassword("123456");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, cxCoinAccountDTO);
        assertThat(response.getStatus()).isEqualTo(200);
        OperationDescription operationDescription = response.getEntity(OperationDescription.class);
        System.out.println("operationDescription = " + operationDescription);
        assertThat(operationDescription.getDealResult()).isEqualTo(Constants.SUCCESS_FLAG);
    }
    
    @Test
    public void test_confirm_purchase() {
        resource = resource.path("/13146001000/cxCoin/confirmPurchase").queryParam("tradeNo", "123abc");
        CXCoinAccount cxCoinAccount = new CXCoinAccount();
        cxCoinAccount.setName("Account1");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, cxCoinAccount);
        String result = response.getEntity(String.class);
        System.out.println("result = " + result);
    }
}
