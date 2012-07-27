package com.server.cx.webservice.ws.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.server.cx.service.account.AccountManager;
import com.server.cx.webservice.WsConstants;
import com.server.cx.webservice.dto.UserDTO;
import com.server.cx.webservice.ws.AccountWebService;
import com.server.cx.webservice.ws.response.DepartmentResponse;
import com.server.cx.webservice.ws.response.UserListResponse;
import com.server.cx.webservice.ws.response.base.IdResponse;

import javax.jws.WebService;

/**
 * WebService服务端实现类.
 * <p/>
 * 客户端实现见功能测试用例.
 *
 * @author calvin
 */
//serviceName与portName属性指明WSDL中的名称, endpointInterface属性指向Interface定义类.
@WebService(serviceName = "AccountService", portName = "AccountServicePort", endpointInterface = "com.server.cx.webservice.ws.AccountWebService", targetNamespace = WsConstants.NS)
public class AccountWebServiceImpl implements AccountWebService {

    private static Logger logger = LoggerFactory.getLogger(AccountWebServiceImpl.class);

    private AccountManager accountManager;

    /**
     */
    @Override
    public DepartmentResponse getDepartmentDetail(Long id) {
        return null;
    }

    /**
     *
     */
    @Override
    public UserListResponse searchUser(String loginName, String name) {
        return null;
    }

    /**
     * @see
     */
    @Override
    public IdResponse createUser(UserDTO user) {
        return null;
    }

    @Autowired
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }
}
