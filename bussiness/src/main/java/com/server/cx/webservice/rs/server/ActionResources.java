package com.server.cx.webservice.rs.server;

import com.server.cx.model.ActionBuilder;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.service.cx.impl.UserCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/actions")
@Produces({MediaType.APPLICATION_JSON})
public class ActionResources {
    public static final Logger LOGGER = LoggerFactory.getLogger(CategoryResources.class);

    @Autowired
    private ActionBuilder actionBuilder;
    @Autowired
    private UserCheckService userCheckService;

    @GET
    public Response queryAllActions() {
        LOGGER.info("into queryAllActions");
        return Response.ok(actionBuilder.buildAnonymousActions()).build();
    }

    @GET
    @Path("{imsi}")
    public Response queryAllActions(@PathParam("imsi") String imsi) {
        try {
            userCheckService.checkAndSetUserInfoExists(imsi);
            return Response.ok(actionBuilder.buildUserOperableActions(imsi)).build();
        } catch (CXServerBusinessException e) {
            LOGGER.error("Error:",e);
            return Response.ok(actionBuilder.buildAnonymousActions()).build();
        }
    }
}
