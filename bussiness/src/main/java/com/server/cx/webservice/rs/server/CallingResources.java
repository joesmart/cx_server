package com.server.cx.webservice.rs.server;

/**
 * User: yanjianzou
 * Date: 12-8-8
 * Time: 下午11:07
 * FileName:CallingResources
 */

import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.OperationDescription;
import com.google.common.base.Optional;
import com.server.cx.service.cx.CallingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("{imsi}/callings")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class CallingResources extends OperationResources {

    @Autowired
    private CallingService callingService;

    @GET
    public Response get(@PathParam("imsi") String imsi, @QueryParam("phoneNo") String callPhoneNo) {
        operationDescription = new OperationDescription();
        try {
            DataItem dataItem = callingService.getCallingMGraphic(Optional.of(imsi), Optional.of(callPhoneNo));
            return Response.ok(dataItem).build();
        } catch (Exception e) {
            errorMessage(e);
            actionName = "calling";
            operationDescription.setActionName(actionName);
            operationDescription.setErrorCode(403);
            return Response.ok(operationDescription).build();
        }
    }
}
