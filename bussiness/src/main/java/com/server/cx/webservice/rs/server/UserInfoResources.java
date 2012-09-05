package com.server.cx.webservice.rs.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: ZouYanjian
 * Date: 9/3/12
 * Time: 10:40 AM
 * FileName:UserInfoResources
 */
@Component
@Path("userInfos/{imsi}")
@Consumes({MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML})
public class UserInfoResources {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserInfoResources.class);
    @PUT
    public Response updatePhoneNo(@PathParam("imsi")String imsi,String phoneNo){
        LOGGER.info(phoneNo);
        LOGGER.info(imsi);
        return Response.ok().build();
    }

    @POST
    public Response update(@PathParam("imsi")String imsi,String phoneNo,@Context HttpServletRequest request,@HeaderParam("imsi") String userAgent){
        LOGGER.info(phoneNo);
        LOGGER.info(imsi);
        while (request.getParameterNames().hasMoreElements()){
            LOGGER.info(String.valueOf(request.getParameterNames().nextElement()));
        }
        LOGGER.info(request.getHeader("imsi"));
        return Response.ok().build();
    }

}
