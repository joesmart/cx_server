package com.server.cx.webservice.rs.server;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cl.cx.platform.dto.BasicDataItem;
import com.cl.cx.platform.dto.OperationDescription;
import com.google.common.collect.Lists;
import com.server.cx.entity.cx.HolidayType;
import com.server.cx.entity.cx.StatusType;
import com.server.cx.entity.cx.SubscribeType;
import com.server.cx.service.cx.GraphicResourceService;
import com.server.cx.service.cx.impl.DataService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.ObjectFactory;

@Component
@Path("/data")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class DataResource {

    @Autowired
    private DataService dataService;

    @Autowired
    private GraphicResourceService graphicResourceService;

    @Autowired
    private BusinessFunctions businessFunctions;

    @Path("subscribeType")
    @POST
    public Response addSubscribeTypes(List<BasicDataItem> basicDataItems) {
        try {
            List<SubscribeType> subscribeTypes = Lists.transform(basicDataItems,
                businessFunctions.transferBasicDataItemToSubscribeType());
            dataService.batchSaveSubscribeType(subscribeTypes);
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                HttpServletResponse.SC_CREATED, "addSubscribeTypes");
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "addSubscribeTypes", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    @Path("status")
    @POST
    public Response addStatusItems(List<BasicDataItem> basicDataItems) {
        try {
            List<StatusType> statusTypes = Lists.transform(basicDataItems,
                businessFunctions.transferBasicDataItemToStatusType());
            dataService.batchSaveStatusType(statusTypes);
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                HttpServletResponse.SC_CREATED, "addStatusItems");
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "addStatusItems", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }
    
    @Path("holiday")
    @POST
    public Response addHolidayItems(List<BasicDataItem> basicDataItems) {
        try {
            List<HolidayType> holidayTypes = Lists.transform(basicDataItems,
                businessFunctions.transferBasicDataItemToHolidayType());
            dataService.batchSaveHolidayType(holidayTypes);
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                HttpServletResponse.SC_CREATED, "addStatusItems");
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "addStatusItems", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    @Path("statusGraphicData")
    @POST
    public Response addStatusGraphicInfos(List<BasicDataItem> basicDataItems) {
        try {
            System.out.println("basicDataItems = " + basicDataItems);
            dataService.batchSaveGraphicResources(basicDataItems);
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                HttpServletResponse.SC_CREATED, "addGraphicInfos");
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "addGraphicInfos", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }
}
