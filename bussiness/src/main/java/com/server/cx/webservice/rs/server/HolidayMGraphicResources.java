package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.MGraphicDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.HolidayMGraphicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: yanjianzou
 * Date: 12-8-13
 * Time: 下午12:23
 * FileName:HolidayMGraphicURL
 */
@Component
@Path("/{imsi}/holidayMGraphics")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class HolidayMGraphicResources extends OperationResources{
    public static final Logger LOGGER = LoggerFactory.getLogger(MyCollectionsResources.class);
    @Autowired
    private HolidayMGraphicService holidayMGraphicService;

    @POST
    public Response create(@PathParam("imsi") String imsi, MGraphicDTO mGraphicDTO) {
        operationDescription = new OperationDescription();
        try {
            OperationResult operationResult;
            operationResult = holidayMGraphicService.create(imsi, mGraphicDTO);
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
            OperationResult operationResult = holidayMGraphicService.edit(imsi, mGraphicDTO);
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
            OperationResult operationResult = holidayMGraphicService.disable(imsi, userCommonMGraphicId);
            updateOperationDescription(operationResult);
        } catch (Exception e) {
            actionName = "disableUserCommonMGraphic";
            errorMessage(e);
        } finally {
            return  Response.ok(operationDescription).build();
        }
    }
}
