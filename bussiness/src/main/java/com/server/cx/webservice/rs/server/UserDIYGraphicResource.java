package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.UserDiyGraphicService;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;


@Component
@Path("{imsi}/diyGraphics")
@Produces({MediaType.APPLICATION_JSON})
public class UserDIYGraphicResource extends OperationResources {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDIYGraphicResource.class);

    @Autowired
    private UserDiyGraphicService userDiyGraphicService;

    @Path("/upload")
    @POST
    public Response uploadImage(@PathParam("imsi") String imsi, @Context HttpServletRequest req, @Context HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Into uploadImage imsi = " + imsi);
        try {
            ValidationUtil.checkParametersNotNull(imsi);
            InputStream fileStream = req.getInputStream();
            userDiyGraphicService.addFileStreamToResourceServer(imsi, fileStream);
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                    HttpServletResponse.SC_CREATED, "uploadImage");
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            LOGGER.error("Error:", e);
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "uploadImage");
            return Response.ok(operationDescription).build();
        }
    }
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("imsi")String imsi,@PathParam("id")String id){
        operationDescription = new OperationDescription();
        try{
            OperationResult operationResult =  userDiyGraphicService.delete(id) ;
            operationDescription.setActionName(operationResult.getName());
            operationDescription.setDealResult(operationResult.getDealResult());
            return Response.ok(operationDescription).build();
        }catch (Throwable e){
            operationDescription.setActionName("deleteUserDIYGraphic");
            operationDescription.setErrorCode(500);
            operationDescription.setErrorMessage(e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }
}
