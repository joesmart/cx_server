package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.DataPage;
import com.server.cx.service.cx.GraphicInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response getGraphicInfosByCategoryAndPagination(@PathParam("imsi") String imsi,
                                                           @QueryParam("categoryId") Long categoryId,
                                                           @QueryParam("hot") Boolean isHot,
                                                           @QueryParam("recommend")Boolean recommend,
                                                           @DefaultValue("0") @QueryParam("offset") Integer offset,
                                                           @DefaultValue("10") @QueryParam("limit") Integer limit
    ) {
        LOGGER.info("imsi:" + imsi);
        LOGGER.info("id:" + categoryId);
        LOGGER.info("isHot:" + isHot);
        LOGGER.info("recommend:" + recommend);
        LOGGER.info("offset:" + offset);
        LOGGER.info("limit:" + limit);

        if (isHot!=null && isHot) {
            DataPage dataPage = graphicInfoService.findHotGraphicInfoByDownloadNum(imsi, offset, limit);
            return Response.ok(dataPage).build();
        }
        if(recommend !=null && recommend){
            DataPage dataPage = graphicInfoService.findRecommendGraphicAndPagination(imsi, offset, limit);
            return Response.ok(dataPage).build();
        }
        if(categoryId != null){
            DataPage dataPage = graphicInfoService.findGraphicInfoDataPageByCategoryId(imsi, categoryId, offset, limit);
            return Response.ok(dataPage).build();
        }
        return Response.noContent().build();
    }

}
