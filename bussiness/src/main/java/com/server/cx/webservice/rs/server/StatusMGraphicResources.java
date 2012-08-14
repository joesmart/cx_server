package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.MGraphicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: yanjianzou
 * Date: 12-8-14
 * Time: 下午1:23
 * FileName:StatusMGraphicResources
 */
@Component
@Path("/{imsi}/statusMGraphics")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class StatusMGraphicResources extends OperationResources {

    public static final Logger LOGGER = LoggerFactory.getLogger(StatusMGraphicResources.class);

    @Autowired
    private MGraphicService statusMGraphicService;

    @POST
    public Response create(@PathParam("imsi") String imsi, MGraphicDTO mGraphicDTO) {
        operationDescription = new OperationDescription();
        try {
            OperationResult operationResult;
            operationResult = statusMGraphicService.create(imsi, mGraphicDTO);
            updateOperationDescription(operationResult);
        } catch (Exception ex) {
            actionName = "createUserCommonMGraphic";
            errorMessage(ex);
        }finally {
            return Response.ok(operationDescription).build();
        }
    }


    @PUT
    @Path("/{id}")
    public Response update(@PathParam("imsi") String imsi,@PathParam("id")String userCommonMGraphicId, MGraphicDTO mGraphicDTO){
        operationDescription = new OperationDescription();
        try {
            mGraphicDTO.setId(userCommonMGraphicId);
            OperationResult operationResult = statusMGraphicService.edit(imsi, mGraphicDTO);
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
            OperationResult operationResult = statusMGraphicService.disable(imsi, userCommonMGraphicId);
            updateOperationDescription(operationResult);
        } catch (Exception e) {
            actionName = "disableUserCommonMGraphic";
            errorMessage(e);
        } finally {
            return  Response.ok(operationDescription).build();
        }
    }
}