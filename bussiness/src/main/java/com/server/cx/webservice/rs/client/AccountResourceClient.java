package com.server.cx.webservice.rs.client;

import com.server.cx.webservice.dto.DepartmentDTO;
import com.server.cx.webservice.dto.UserDTO;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.ClientResponse.Status;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Required;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.List;

/**
 * 使用Jersey Client的AccountResourceService REST客户端.
 *
 * @author calvin
 */
public class AccountResourceClient {

    private WebResource client;

    private GenericType<List<UserDTO>> userListType = new GenericType<List<UserDTO>>() {
    };

    @Required
    public void setBaseUrl(String baseUrl) {
        client = Client.create().resource(baseUrl);
    }

    /**
     * 获取部门.
     */
    public DepartmentDTO getDepartmentDetail(Long id) {
        return client.path("/departments/" + id).accept(MediaType.APPLICATION_JSON).get(DepartmentDTO.class);
    }

    /**
     * 获取用户.
     */
    public UserDTO getUser(Long id) {
        return client.path("/users/" + id).accept(MediaType.APPLICATION_JSON).get(UserDTO.class);
    }

    /**
     * 查询用户列表, 使用URL参数发送查询条件, 返回用户列表.
     */
    public List<UserDTO> searchUser(String loginName, String name) {
        WebResource wr = client.path("/users/search");
        if (StringUtils.isNotBlank(loginName)) {
            wr = wr.queryParam("loginName", loginName);
        }
        if (StringUtils.isNotBlank(name)) {
            wr = wr.queryParam("name", name);
        }

        return wr.accept(MediaType.APPLICATION_JSON).get(userListType);
    }

    /**
     * 创建用户, 使用Post发送JSON编码的用户对象, 返回代表用户的url.
     */
    public URI createUser(UserDTO user) {
        ClientResponse response = client.path("/users").entity(user, MediaType.APPLICATION_JSON)
                .post(ClientResponse.class);
        if (Status.CREATED.getStatusCode() == response.getStatus()) {
            return response.getLocation();
        } else {
            throw new UniformInterfaceException(response);
        }
    }
}
