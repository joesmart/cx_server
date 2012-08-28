package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.IdDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.service.cx.UserCollectionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: yanjianzou
 * Date: 12-8-1
 * Time: 下午1:54
 * FileName:MyCollections
 */
//TODO 需要增加返回处理结果处理 方法 by Zou YanJian.
@Component
@Path("{imsi}/myCollections")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class MyCollectionsResources {
    public static final Logger LOGGER = LoggerFactory.getLogger(MyCollectionsResources.class);

    @Autowired
    private UserCollectionsService userCollectionsService;

    @POST
    public Response create(@PathParam("imsi")String imsi,IdDTO idDTO){
        OperationDescription operationDescription ;
        try{
            operationDescription = userCollectionsService.addNewUserFavorites(imsi, idDTO);
            operationDescription.setStatusCode(Response.Status.CREATED.getStatusCode());
            return Response.ok(operationDescription).status(Response.Status.OK).build();
        }catch (Exception e){
            //TODO return errors
            operationDescription = new OperationDescription();
            operationDescription.setActionName("addNewUserFavorite");
            operationDescription.setStatusCode(Response.Status.CONFLICT.getStatusCode());
            operationDescription.setErrorMessage(e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("imsi")String imsi,@PathParam("id")String userFavoriteId){
        OperationDescription operationDescription ;
        try{
            operationDescription = userCollectionsService.deleteUserFavoritesById(imsi, userFavoriteId);
            return Response.ok(operationDescription).status(Response.Status.ACCEPTED).build();
        }catch (Exception e){
           LOGGER.error("delete UserFavorite Error:",e);
            operationDescription = new OperationDescription();
            operationDescription.setActionName("addNewUserFavorite");
            operationDescription.setStatusCode(Response.Status.CONFLICT.getStatusCode());
            operationDescription.setErrorMessage(e.getMessage());
            return Response.ok(operationDescription).status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GET
    public Response findByPage(@PathParam("imsi") String imsi,
                               @DefaultValue("0") @QueryParam("offset") Integer offset,
                               @DefaultValue("10") @QueryParam("limit") Integer limit){
        try{
            DataPage dataPage = userCollectionsService.getAllUserFavorites(imsi,offset,limit);
            return  Response.ok(dataPage).build();
        }catch (Exception ex){
            OperationDescription operationDescription = new OperationDescription();
            operationDescription.setErrorCode(400);
            operationDescription.setErrorMessage(ex.getMessage());
            operationDescription.setStatusCode(Response.Status.FORBIDDEN.getStatusCode());
            return  Response.ok(operationDescription).build();
        }
    }
}
