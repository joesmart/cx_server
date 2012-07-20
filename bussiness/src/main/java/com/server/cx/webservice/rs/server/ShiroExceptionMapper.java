package com.server.cx.webservice.rs.server;

import org.apache.shiro.authz.UnauthorizedException;
import org.springside.modules.rest.RsResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ShiroExceptionMapper implements ExceptionMapper<UnauthorizedException> {
	@Override
	public Response toResponse(UnauthorizedException ex) {
		return RsResponse.buildTextResponse(Status.UNAUTHORIZED, ex.getMessage());
	}

}
