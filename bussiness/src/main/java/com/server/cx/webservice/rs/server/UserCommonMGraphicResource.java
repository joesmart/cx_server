package com.server.cx.webservice.rs.server;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.constants.Constants;
import com.server.cx.service.cx.UserCommonMGraphicService;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.ValidationUtil;

@Component
@Path("/{imsi}/userCommonMGraphic")
@Produces({MediaType.APPLICATION_JSON})
public class UserCommonMGraphicResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommonMGraphicResource.class);
    
    @Autowired
    private UserCommonMGraphicService userCommonMGraphicService;
    
    @Path("/upload")
    @POST
    public Response uploadImage(@PathParam("imsi") String imsi, @Context HttpServletRequest req, @Context HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Into uploadImage imsi = " + imsi);
        try {
            ValidationUtil.checkParametersNotNull(imsi);
            InputStream fileStream = req.getInputStream();
            userCommonMGraphicService.addFileStreamToResourceServer(imsi, fileStream);
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                HttpServletResponse.SC_CREATED, "uploadImage", Constants.SUCCESS_FLAG);
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "uploadImage", Constants.ERROR_FLAG);
            return Response.ok(operationDescription).build();
        }
        
    }
}
