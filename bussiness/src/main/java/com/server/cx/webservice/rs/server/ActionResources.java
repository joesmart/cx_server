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
import com.cl.cx.platform.dto.Actions;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.model.ActionBuilder;
import com.server.cx.service.cx.impl.CXCoinBasicService;

@Component
@Path("actions")
@Produces({MediaType.APPLICATION_JSON})
public class ActionResources {
    public static final Logger LOGGER = LoggerFactory.getLogger(CategoryResources.class);

    @Autowired
    private ActionBuilder actionBuilder;
    
    @Autowired
    private CXCoinBasicService cxCoinBasicService;

    @GET
    public Response queryAllActions() {
        LOGGER.info("into queryAllActions");
        return Response.ok(actionBuilder.buildAnonymousActions()).build();
    }

    @GET
    @Path("{imsi}")
    public Response queryAllActions(@PathParam("imsi") String imsi) {
        try {
            //            userCheckService.checkAndSetUserInfoExists(imsi);
            Actions actions = actionBuilder.buildUserOperableActions(imsi);
            if (cxCoinBasicService.hasRegisteredCXCoinAccount(imsi)) {
                actionBuilder.hiddenCXCoinRegisterURL(actions);
            } else {
                actionBuilder.hiddenCXCoinOtherURL(actions);
            }
            return Response.ok(actions).build();
        } catch (CXServerBusinessException e) {
            LOGGER.error("Error:", e);
            return Response.ok(actionBuilder.buildAnonymousActions()).build();
        }
    }
}
