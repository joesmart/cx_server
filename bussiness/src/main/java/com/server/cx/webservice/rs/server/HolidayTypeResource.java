package com.server.cx.webservice.rs.server;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.constants.Constants;
import com.server.cx.exception.InvalidParameterException;
import com.server.cx.exception.MoneyNotEnoughException;
import com.server.cx.exception.NotSubscribeTypeException;
import com.server.cx.exception.UserHasSubscribedException;
import com.server.cx.model.ActionBuilder;
import com.server.cx.service.cx.HolidayTypeService;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.ValidationUtil;

@Component
@Path("/{imsi}/holidayTypes")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class HolidayTypeResource {
    public static final Logger LOGGER = LoggerFactory.getLogger(HolidayTypeResource.class);

    @Autowired
    private HolidayTypeService holidayTypeService;

    @Autowired
    private ActionBuilder actionBuilder;
    
    @GET
    public Response queryAllHolidayTypes(@PathParam("imsi") String imsi) {
        LOGGER.info("imsi:" + imsi);

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

    @PUT
    public Response subscribeHolidayType(@PathParam("imsi") String imsi) {
        LOGGER.info("Into subscribeFunctionType imsi = " + imsi);

        try {
            ValidationUtil.checkParametersNotNull(imsi);
            DataPage dataPage = holidayTypeService.subscribeAndQueryHoliayTypes(imsi);
            return Response.ok(dataPage).build();

        } catch (InvalidParameterException e) {
            LOGGER.error("subscribeFunctionType InvalidParameterException error", e);
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                Response.Status.BAD_REQUEST.getStatusCode(), "subscribeFunctionType", "请求参数异常");
            return Response.ok(operationDescription).build();

        } catch (MoneyNotEnoughException e) {
            LOGGER.info("subscribeFunctionType MoneyNotEnoughException", e);
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                Response.Status.NOT_ACCEPTABLE.getStatusCode(), "subscribeFunctionType", "余额不足");
            return Response.ok(operationDescription).build();

        } catch (UserHasSubscribedException e) {
            LOGGER.error("subscribeFunctionType error", e);
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                Response.Status.NOT_ACCEPTABLE.getStatusCode(), "subscribeFunctionType", "用户已经订购");
            return Response.ok(operationDescription).build();

        } catch (Exception e) {
            LOGGER.error("subscribeFunctionType error", e);
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "subscribeFunctionType", "服务器内部错误");
            return Response.ok(operationDescription).build();
        }

    }

}
