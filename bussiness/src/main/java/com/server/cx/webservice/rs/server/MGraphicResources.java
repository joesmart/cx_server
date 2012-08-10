package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.DataPage;
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
 * Date: 12-8-6
 * Time: 上午10:29
 * FileName:UserCommonMGraphicResources
 */
@Component
@Path("/{imsi}/mGraphics")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class MGraphicResources extends OperationResources {
    public static final Logger LOGGER = LoggerFactory.getLogger(MyCollectionsResources.class);

    @Autowired
    private MGraphicService mGraphicService;




    @POST
    public Response create(@PathParam("imsi") String imsi, MGraphicDTO mGraphicDTO) {
        operationDescription = new OperationDescription();
        try {
            OperationResult operationResult;
            operationResult = mGraphicService.create(imsi, mGraphicDTO);
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
            OperationResult operationResult = mGraphicService.edit(imsi, mGraphicDTO);
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
            OperationResult operationResult = mGraphicService.disable(imsi, userCommonMGraphicId);
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
        DataPage dataPage = mGraphicService.queryUserMGraphic(imsi);
        return Response.ok(dataPage).build();
    }
}
