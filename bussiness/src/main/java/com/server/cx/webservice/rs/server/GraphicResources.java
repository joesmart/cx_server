package com.server.cx.webservice.rs.server;

import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User: ZouYanjian
 * Date: 8/30/12
 * Time: 2:46 PM
 * FileName:GraphicResources
 */
@Component
@Path("{imsi}/graphicResources")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class GraphicResources {
}
