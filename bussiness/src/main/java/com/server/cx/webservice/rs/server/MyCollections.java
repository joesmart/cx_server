package com.server.cx.webservice.rs.server;

import com.server.cx.dto.StringTypeIdDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User: yanjianzou
 * Date: 12-8-1
 * Time: 下午1:54
 * FileName:MyCollections
 */
@Component
@Path("/{imsi}/myCollections")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class MyCollections {
    public static final Logger LOGGER = LoggerFactory.getLogger(MyCollections.class);


    @POST
    public void create(StringTypeIdDTO stringTypeIdDTO){

    }
}
