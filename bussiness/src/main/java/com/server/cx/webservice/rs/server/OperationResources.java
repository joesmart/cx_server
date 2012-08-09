package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.dto.OperationResult;

import javax.ws.rs.core.Response;

/**
 * User: yanjianzou
 * Date: 12-8-9
 * Time: 上午10:30
 * FileName:OperationResources
 */
public class OperationResources {
    protected OperationDescription operationDescription;
    protected String actionName;
    private String dealResult;

    protected void errorMessage(Exception ex) {
        operationDescription.setActionName(actionName);
        operationDescription.setErrorMessage(ex.getMessage());
        operationDescription.setErrorCode(401);
        operationDescription.setStatusCode(Response.Status.CONFLICT.getStatusCode());
    }

    protected void updateOperationDescription(OperationResult operationResult) {
        actionName = operationResult.getName();
        dealResult = operationResult.getDealResult();
        operationDescription.setActionName(actionName);
        operationDescription.setDealResult(dealResult);
    }
}
