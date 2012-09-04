package com.server.cx.webservice.rs.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
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
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserInfoResources {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserInfoResources.class);
    @PUT
    public Response updatePhoneNo(@PathParam("imsi")String imsi,String phoneNo){
        LOGGER.info(phoneNo);
        LOGGER.info(imsi);
        return Response.ok().build();
    }

    @POST
    public Response update(@PathParam("imsi")String imsi,String phoneNo){
        LOGGER.info(phoneNo);
        LOGGER.info(imsi);
        return Response.ok().build();
    }

}
