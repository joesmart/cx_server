package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.HistoryMGraphicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: yanjianzou
 * Date: 12-8-7
 * Time: 下午5:09
 * FileName:HistoryMGraphicResources
 */
@Component
@Path("{imsi}/historyMGraphics")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class HistoryMGraphicResources {

    @Autowired
    private HistoryMGraphicService historyMGraphicService;

    private OperationDescription operationDescription;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("imsi")String imsi,@PathParam("id")String historyMGraphicId){
        String actionName = "disableUserCommonMGraphic";
        operationDescription = new OperationDescription();
        try {
            OperationResult operationResult = historyMGraphicService.deleteHistoryMGraphic(imsi, historyMGraphicId);
            updateOperationDescription(operationResult,actionName);
        } catch (Exception e) {
            errorMessage(e, actionName);
            operationDescription.setActionName(actionName);
            operationDescription.setErrorCode(403);
            return Response.ok(operationDescription).status(Response.Status.FORBIDDEN).build();
        }
        return  Response.ok(operationDescription).build();
    }

    private void updateOperationDescription(OperationResult operationResult,String actionName) {
        operationDescription.setActionName(actionName);
        operationDescription.setDealResult(operationResult.getDealResult());
    }

    private void errorMessage(Exception ex,String actionName) {
        operationDescription.setActionName(actionName);
        operationDescription.setErrorMessage(ex.getMessage());
        operationDescription.setErrorCode(401);
        operationDescription.setStatusCode(Response.Status.CONFLICT.getStatusCode());
    }
}
