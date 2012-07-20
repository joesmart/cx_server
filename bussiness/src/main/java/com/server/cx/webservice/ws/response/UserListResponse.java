package com.server.cx.webservice.ws.response;

import com.server.cx.webservice.WsConstants;
import com.server.cx.webservice.dto.UserDTO;
import com.server.cx.webservice.ws.response.base.WSResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * 包含UserList的返回结果.
 * 
 * @author calvin
 * @author badqiu
 */
@XmlType(name = "UserListResponse", namespace = WsConstants.NS)
public class UserListResponse extends WSResponse {

	private List<UserDTO> userList;

	public UserListResponse() {
	}

	public UserListResponse(List<UserDTO> userList) {
		this.userList = userList;
	}

	@XmlElementWrapper(name = "userList")
	@XmlElement(name = "user")
	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}
}
