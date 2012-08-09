package com.server.cx.webservice.rs.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.server.cx.dto.ActionBuilder;

@Component
@Path("/actions")
@Produces({MediaType.APPLICATION_JSON})
public class ActionResources {
    public static final Logger LOGGER = LoggerFactory.getLogger(CategoryResources.class);

    @Autowired
    private ActionBuilder actionBuilder;

    @GET
    public Response queryAllActions() {
        LOGGER.info("into queryAllActions");
        return Response.ok(actionBuilder.buildUrlActions()).build();
    }

    @GET
    @Path("{imsi}")
    public Response queryAllActions(@PathParam("imsi") String imsi) {
        LOGGER.info("into queryAllActions imsi = " + imsi);
        return Response.ok(actionBuilder.buildUrlActions(imsi)).build();
    }

}
