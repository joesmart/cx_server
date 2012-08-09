package com.server.cx.webservice.rs.server;

import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User: yanjianzou
 * Date: 12-8-9
 * Time: 上午9:29
 * FileName:SMSResources
 */
@Component
@Path("/{imsi}/sms")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class SMSResources {

}
