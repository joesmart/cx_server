package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.MGraphicDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.constants.Constants;
import com.server.cx.exception.MoneyNotEnoughException;
import com.server.cx.exception.NotSubscribeTypeException;
import com.server.cx.model.ActionBuilder;
import com.server.cx.model.OperationResult;
import com.server.cx.service.cx.MGraphicService;
import com.server.cx.service.cx.QueryMGraphicService;
import com.server.cx.service.cx.UserCustomTypeService;
import com.server.cx.service.cx.UserSubscribeTypeService;
import com.server.cx.util.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("{imsi}/customMGraphics")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class CustomMGraphicResources extends OperationResources {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusMGraphicResources.class);

    @Autowired
    private MGraphicService customMGraphicService;

    @Autowired
    @Qualifier("customMGraphicService")
    private QueryMGraphicService queryMGraphicService;

    @Autowired
    private UserCustomTypeService userCustomTypeService;

    @Autowired
    private ActionBuilder actionBuilder;

    @Autowired
    private UserSubscribeTypeService userSubscribeTypeService;

    @POST
    public Response create(@PathParam("imsi") String imsi,
                           @DefaultValue("false") @QueryParam("subscribe") Boolean subscribe, MGraphicDTO mGraphicDTO) {
        operationDescription = new OperationDescription();
        try {
            OperationResult operationResult;
            operationResult = customMGraphicService.create(imsi, false, mGraphicDTO, subscribe);
            updateOperationDescription(operationResult);
        } catch (MoneyNotEnoughException e) {
            LOGGER.info("create MoneyNotEnoughException", e);
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                Response.Status.NOT_ACCEPTABLE.getStatusCode(), "create", "余额不足");
            return Response.ok(operationDescription).build();

        } catch (NotSubscribeTypeException e) {
            LOGGER.error("create NotSubscribeTypeException error", e);
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                HttpServletResponse.SC_OK, "create", Constants.SUCCESS_FLAG,
                actionBuilder.buildCustomSubscribeGraphicItemAction(imsi));
            return Response.ok(operationDescription).build();

        } catch (Exception ex) {
            errorMessage(ex);
            actionName = "createUserCommonMGraphic";
            operationDescription.setActionName(actionName);
            operationDescription.setErrorCode(403);
            return Response.ok(operationDescription).build();
        }
        return Response.ok(operationDescription).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("imsi") String imsi, @PathParam("id") String userCommonMGraphicId,
                           MGraphicDTO mGraphicDTO) {
        operationDescription = new OperationDescription();
        try {
            mGraphicDTO.setId(userCommonMGraphicId);
            OperationResult operationResult = customMGraphicService.edit(imsi, mGraphicDTO);
            updateOperationDescription(operationResult);
        } catch (NotSubscribeTypeException e) {
            LOGGER.error("update NotSubscribeTypeException error", e);
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                Response.Status.NOT_ACCEPTABLE.getStatusCode(), "create", "用户未订购");
            return Response.ok(operationDescription).build();

        } catch (Exception e) {
            errorMessage(e);
            actionName = "editUserCommonMGraphic";
            operationDescription.setActionName(actionName);
            operationDescription.setErrorCode(409);
            return Response.ok(operationDescription).build();
        }
        return Response.ok(operationDescription).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("imsi") String imsi, @PathParam("id") String userCommonMGraphicId) {
        operationDescription = new OperationDescription();
        try {
            OperationResult operationResult = customMGraphicService.disable(imsi, userCommonMGraphicId);
            updateOperationDescription(operationResult);
        } catch (Exception e) {
            errorMessage(e);
            actionName = "disableUserCommonMGraphic";
            operationDescription.setActionName(actionName);
            operationDescription.setErrorCode(403);
            return Response.ok(operationDescription).build();
        }
        return Response.ok(operationDescription).build();
    }

    @GET
    public Response getAll(@PathParam("imsi") String imsi) {
        try {
            DataPage dataPage = queryMGraphicService.queryUserMGraphic(imsi);
            return Response.ok(dataPage).build();
        } catch (NotSubscribeTypeException e) {
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                HttpServletResponse.SC_OK, "getAll", Constants.SUCCESS_FLAG,
                actionBuilder.buildSubscribeCustomAction(imsi));
            return Response.ok(operationDescription).build();
        }
    }
}
