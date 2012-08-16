package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.MGraphicDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.MGraphicService;
import com.server.cx.service.cx.QueryMGraphicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public static final Logger LOGGER = LoggerFactory.getLogger(MGraphicResources.class);

    @Autowired
    private MGraphicService mgraphicService;

    @Autowired
    @Qualifier("mgraphicService")
    private QueryMGraphicService queryMGraphicService;

    @POST
    public Response create(@PathParam("imsi") String imsi, MGraphicDTO mGraphicDTO) {
        log(imsi, mGraphicDTO,"create");
        operationDescription = new OperationDescription();
        try {
            OperationResult operationResult;
            operationResult = mgraphicService.create(imsi, mGraphicDTO);
            updateOperationDescription(operationResult);
        } catch (Exception ex) {
            actionName = "createCommonMGraphic";
            errorMessage(ex);
        }finally {
            return Response.ok(operationDescription).build();
        }
    }

    private void log(String imsi, MGraphicDTO mGraphicDTO,String method) {
        String[] strings = new String[]{this.getClass().getName(),method,imsi,mGraphicDTO.toString()};
        LOGGER.info("Resource:{},method:{},parameters: imsi:{},MGraphicDTO:{}",strings);
    }

    private void log(String imsi, String id,String method) {
        String[] strings = new String[]{this.getClass().getName(),method,imsi,id};
        LOGGER.info("Resource:{},method:{},parameters: imsi:{},id:{}",strings);
    }

    @PUT
    @Path("/{id}")
    public Response edit(@PathParam("imsi") String imsi, @PathParam("id") String userCommonMGraphicId, MGraphicDTO mGraphicDTO){
        log(imsi, mGraphicDTO,"edit");
        operationDescription = new OperationDescription();
        try {
            mGraphicDTO.setId(userCommonMGraphicId);
            OperationResult operationResult = mgraphicService.edit(imsi, mGraphicDTO);
            updateOperationDescription(operationResult);
        } catch (Exception e) {
            actionName = "editCommonMGraphic";
            errorMessage(e);
        } finally {
            return  Response.ok(operationDescription).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("imsi")String imsi,@PathParam("id")String userCommonMGraphicId){
        log(imsi, userCommonMGraphicId ,"delete");
        operationDescription = new OperationDescription();
        try {
            OperationResult operationResult = mgraphicService.disable(imsi, userCommonMGraphicId);
            updateOperationDescription(operationResult);
        } catch (Exception e) {
            actionName = "disableCommonMGraphic";
            errorMessage(e);
        } finally {
            return  Response.ok(operationDescription).build();
        }
    }
    @GET
    public Response getAll(@PathParam("imsi")String imsi){
        log(imsi, "" ,"getAll");
        DataPage dataPage = queryMGraphicService.queryUserMGraphic(imsi);
        return Response.ok(dataPage).build();
    }
}
