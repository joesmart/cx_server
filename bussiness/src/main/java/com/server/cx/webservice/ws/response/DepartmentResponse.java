package com.server.cx.webservice.ws.response;

import com.server.cx.webservice.WsConstants;
import com.server.cx.webservice.dto.DepartmentDTO;
import com.server.cx.webservice.ws.response.base.WSResponse;

import javax.xml.bind.annotation.XmlType;

/**
 * 包含Department的返回结果.
 *
 * @author calvin
 * @author badqiu
 */
@XmlType(name = "DepartmentResponse", namespace = WsConstants.NS)
public class DepartmentResponse extends WSResponse {

    private DepartmentDTO department;

    public DepartmentResponse() {
    }

    public DepartmentResponse(DepartmentDTO department) {
        this.department = department;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }
}
