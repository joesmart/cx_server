package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.MGraphicDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.dto.OperationResult;
import com.server.cx.service.cx.UserCommonMGraphicService;
import com.server.cx.service.cx.UserSpecialMGraphicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * User: yanjianzou
 * Date: 12-8-6
 * Time: 上午10:29
 * FileName:UserCommonMGraphicResources
 */
@Component
@Path("/{imsi}/mGraphics")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserCommonMGraphicResources {
    public static final Logger LOGGER = LoggerFactory.getLogger(MyCollectionsResource.class);

    @Autowired
    private UserCommonMGraphicService userCommonMGraphicService;

    @Autowired
    private UserSpecialMGraphicService userSpecialMGraphicService;

    private OperationDescription operationDescription;
    private String actionName;
    private String dealResult;

    @POST
    public Response create(@PathParam("imsi") String imsi, MGraphicDTO mGraphicDTO) {
        operationDescription = new OperationDescription();
        try {
            OperationResult operationResult;
            if(mGraphicDTO.getPhoneNos() == null || mGraphicDTO.getPhoneNos().size() == 0){
                operationResult = userCommonMGraphicService.createUserCommonMGraphic(imsi, mGraphicDTO);
            }else{
                operationResult = userSpecialMGraphicService.createUserSpecialMGraphic(imsi,mGraphicDTO);
            }
            updateOperationDescription(operationResult);
        } catch (Exception ex) {
            actionName = "createUserCommonMGraphic";
            errorMessage(ex);
        }finally {
            return Response.ok(operationDescription).build();
        }
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

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("imsi") String imsi,@PathParam("id")String userCommonMGraphicId, MGraphicDTO mGraphicDTO){
        operationDescription = new OperationDescription();
        try {
            mGraphicDTO.setId(userCommonMGraphicId);
            OperationResult operationResult = userCommonMGraphicService.editUserCommonMGraphic(imsi,mGraphicDTO);
            updateOperationDescription(operationResult);
        } catch (Exception e) {
            actionName = "editUserCommonMGraphic";
            errorMessage(e);
        } finally {
            return  Response.ok(operationDescription).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("imsi")String imsi,@PathParam("id")String userCommonMGraphicId){
        operationDescription = new OperationDescription();
        try {
            OperationResult operationResult = userCommonMGraphicService.disableUserCommonMGraphic(imsi,userCommonMGraphicId);
            updateOperationDescription(operationResult);
        } catch (Exception e) {
            actionName = "disableUserCommonMGraphic";
            errorMessage(e);
        } finally {
            return  Response.ok(operationDescription).build();
        }
    }
    @GET
    public Response getAll(@PathParam("imsi")String imsi){
        List<DataItem> dataItemList = userCommonMGraphicService.getUserMGraphic(imsi);
        return Response.ok(dataItemList).build();
    }
}
