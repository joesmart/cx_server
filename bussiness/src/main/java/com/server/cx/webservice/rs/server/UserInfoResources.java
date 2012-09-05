package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.RegisterDTO;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.RegisterService;
import com.server.cx.util.business.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Consumes({MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_XML})
public class UserInfoResources {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoResources.class);
    @Autowired
    private RegisterService registerService;

    @PUT
    public Response updatePhoneNo(@PathParam("imsi")String imsi,String phoneNo){
        LOGGER.info(phoneNo);
        LOGGER.info(imsi);
        return Response.ok().build();
    }

    @POST
    public Response update(@PathParam("imsi")String imsi,@HeaderParam("phoneNo") String phoneNo){

        try {
            ValidationUtil.checkParametersNotNull(imsi,phoneNo);
            RegisterDTO registerDTO = new RegisterDTO();
            registerDTO.setImsi(imsi);
            registerDTO.setPhoneNo(phoneNo);

            registerService.update(registerDTO);
        } catch (SystemException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }

}
