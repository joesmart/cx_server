package com.server.cx.webservice.ws.response.base;

import com.server.cx.webservice.WsConstants;

import javax.xml.bind.annotation.XmlType;

/**
 * 创建某个对象返回的 通用IdResult
 *
 * @author badqiu
 */
@XmlType(name = "IdResponse", namespace = WsConstants.NS)
public class IdResponse extends WSResponse {
    private Long id;

    public IdResponse() {
    }

    public IdResponse(Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
