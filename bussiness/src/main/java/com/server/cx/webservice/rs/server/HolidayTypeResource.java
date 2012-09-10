package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.constants.Constants;
import com.server.cx.exception.NotSubscribeTypeException;
import com.server.cx.model.ActionBuilder;
import com.server.cx.service.cx.HolidayTypeService;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("{imsi}/holidayTypes")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class HolidayTypeResource {

    @Autowired
    private HolidayTypeService holidayTypeService;

    @Autowired
    private ActionBuilder actionBuilder;
    
    @GET
    public Response queryAllHolidayTypes(@PathParam("imsi") String imsi) {
        ValidationUtil.checkParametersNotNull(imsi);
        try {
            DataPage dataPage = holidayTypeService.queryAllHolidayTypes(imsi);
            return Response.ok(dataPage).build();

        } catch (NotSubscribeTypeException e) {
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                HttpServletResponse.SC_OK, "queryAllHolidayTypes", Constants.SUCCESS_FLAG,
                actionBuilder.buildSubscribeHolidayAction(imsi));
            return Response.ok(operationDescription).build();
        }
    }

    
    

}
