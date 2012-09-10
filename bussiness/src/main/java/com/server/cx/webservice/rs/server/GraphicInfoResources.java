package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.service.cx.GraphicInfoService;
import com.server.cx.service.cx.UserSubscribeGraphicItemService;
import com.server.cx.util.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

/**
 * User: yanjianzou
 * Date: 12-7-31
 * Time: 下午12:43
 * FileName:GraphicInfoResources
 */

@Component
@Path("{imsi}/graphicInfos")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class GraphicInfoResources {

    @Autowired
    private GraphicInfoService graphicInfoService;
    
    @Autowired
    private UserSubscribeGraphicItemService userSubscribeGraphicItemService;

    @GET
    public Response getGraphicInfosByCategoryAndPagination(@PathParam("imsi") String imsi,
                                                           @QueryParam("categoryId") Long categoryId,
                                                           @QueryParam("hot") Boolean isHot,
                                                           @QueryParam("recommend")Boolean recommend,
                                                           @QueryParam("holidayTypeId")Long holidayTypeId,
                                                           @QueryParam("statusTypeId") Long statusTypeId,
                                                           @DefaultValue("0") @QueryParam("offset") Integer offset,
                                                           @DefaultValue("8") @QueryParam("limit") Integer limit
    ) {


        try {
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
            if(holidayTypeId != null){
                DataPage dataPage = graphicInfoService.findHolidayGraphicInfosByImsi(imsi, holidayTypeId, offset, limit);
                return Response.ok(dataPage).build();
            }
            if(statusTypeId != null) {
                DataPage dataPage = graphicInfoService.findStatusGraphicInfosByImsi(imsi, statusTypeId, offset, limit);
                return Response.ok(dataPage).build();
            }
        } catch (ExecutionException e) {
            OperationDescription operationDescription = new OperationDescription();
            operationDescription.setActionName("getGraphicInfosByCategoryAndPagination");
            operationDescription.setErrorCode(500);
            operationDescription.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            operationDescription.setErrorMessage("服务器内部错误");
            return Response.ok(operationDescription).build();
        }
        return Response.noContent().build();
    }
    

    @POST
    public Response addGraphicInfo(DataItem dataItem) {

        try {
            GraphicInfo graphicInfo = ObjectFactory.buildGraphicInfoFromDataItem(dataItem);
            graphicInfoService.addGraphicInfo(graphicInfo);
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                HttpServletResponse.SC_CREATED, "addGraphicInfo");
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "addGraphicInfo", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }
}
