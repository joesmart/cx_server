package com.server.cx.webservice.rs.server;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import com.server.cx.service.cx.GraphicInfoService;
import com.server.cx.service.cx.StatusTypeService;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.ValidationUtil;

@Component
@Path("{imsi}/statusTypes")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class StatusTypeResources {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusTypeResources.class);

    @Autowired
    private StatusTypeService statusTypeService;

    @Autowired
    private GraphicInfoService graphicInfoService;

    @Autowired
    private ActionBuilder actionBuilder;
    
    @GET
    public Response queryAllStatusTypes(@PathParam("imsi") String imsi) {
        LOGGER.info("imsi:" + imsi);

        ValidationUtil.checkParametersNotNull(imsi);
        try {
            DataPage dataPage = statusTypeService.queryAllStatusTypes(imsi);
            return Response.ok(dataPage).build();

        } catch (NotSubscribeTypeException e) {
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                HttpServletResponse.SC_OK, "queryAllStatusTypes", Constants.SUCCESS_FLAG,
                actionBuilder.buildSubscribeStatusAction(imsi));
            return Response.ok(operationDescription).build();
        }
    }
    
    @PUT
    public Response subscribeStatusType(@PathParam("imsi") String imsi) {
        LOGGER.info("Into subscribeFunctionType imsi = " + imsi);

        try {
            ValidationUtil.checkParametersNotNull(imsi);
            DataPage dataPage = statusTypeService.subscribeAndQueryStatusTypes(imsi);
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
    
    @DELETE
    public Response cancelSubscribeStatusType(@PathParam("imsi") String imsi) {
        LOGGER.info("Into cancelSubscribeStatusType imsi = " + imsi);
        try {
            ValidationUtil.checkParametersNotNull(imsi);
            statusTypeService.cancelSubscribeStatusType(imsi);
            
        } catch(Exception e) {
            LOGGER.error("cancelSubscribeStatusType error", e);
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "cancelSubscribeStatusType", "服务器内部错误");
            return Response.ok(operationDescription).build();
        }
        OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
            Response.Status.NO_CONTENT.getStatusCode(), "cancelSubscribeStatusType", "成功取消订购");
        return Response.ok(operationDescription).build();
        
    }
}
