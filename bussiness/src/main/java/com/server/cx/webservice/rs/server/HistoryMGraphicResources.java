package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.HistoryMGraphicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Path("/{imsi}/historyMGraphics")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class HistoryMGraphicResources {
    public static final Logger LOGGER = LoggerFactory.getLogger(MyCollectionsResources.class);

    @Autowired
    private HistoryMGraphicService historyMGraphicService;

    private OperationDescription operationDescription;
    private String actionName;
    private String dealResult;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("imsi")String imsi,@PathParam("id")String historyMGraphicId){
        operationDescription = new OperationDescription();
        try {
            OperationResult operationResult = historyMGraphicService.deleteHistoryMGraphic(imsi,historyMGraphicId);
            updateOperationDescription(operationResult);
        } catch (Exception e) {
            errorMessage(e);
            actionName = "disableUserCommonMGraphic";
            operationDescription.setActionName(actionName);
            operationDescription.setErrorCode(403);
            return Response.ok(operationDescription).status(Response.Status.FORBIDDEN).build();
        }
        return  Response.ok(operationDescription).build();
    }

    private void updateOperationDescription(OperationResult operationResult) {
        actionName = operationResult.getName();
        dealResult = operationResult.getDealResult();
        operationDescription.setActionName(actionName);
        operationDescription.setDealResult(dealResult);
    }

    private void errorMessage(Exception ex) {
        operationDescription.setActionName(actionName);
        operationDescription.setErrorMessage(ex.getMessage());
        operationDescription.setErrorCode(401);
        operationDescription.setStatusCode(Response.Status.CONFLICT.getStatusCode());
    }
}
