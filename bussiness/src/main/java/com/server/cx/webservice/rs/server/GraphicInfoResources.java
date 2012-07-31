package com.server.cx.webservice.rs.server;

import com.server.cx.dto.DataPage;
import com.server.cx.service.cx.GraphicInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * User: yanjianzou
 * Date: 12-7-31
 * Time: 下午12:43
 * FileName:GraphicInfoResources
 */

@Component
@Path("/{imsi}/graphicInfos")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class GraphicInfoResources {
    public static final Logger LOGGER = LoggerFactory.getLogger(GraphicInfoResources.class);

    @Autowired
    private GraphicInfoService graphicInfoService;

    @GET
    public DataPage getGraphicInfosByCategoryAndPagination(@PathParam("imsi")String imsi,
                                                           @QueryParam("categoryId") Long categoryId,
                                                           @DefaultValue("0") @QueryParam("offset")Integer offset,
                                                           @DefaultValue("10") @QueryParam("limit")Integer limit
                                                           ){
        LOGGER.info("imsi:"+imsi);
        LOGGER.info("id:"+categoryId);
        LOGGER.info("offset:"+offset);
        LOGGER.info("limit:"+limit);

        DataPage dataPage = graphicInfoService.findGraphicInfoDataPageByCategoryId(imsi,categoryId,offset,limit);
        return  dataPage;
    }
}
