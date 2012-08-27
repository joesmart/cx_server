package com.server.cx.webservice.rs.server;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
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
import com.server.cx.service.cx.UserSubscribeGraphicItemService;
import com.server.cx.util.ObjectFactory;

/**
 * User: yanjianzou Date: 12-8-6 Time: 上午10:29 FileName:UserCommonMGraphicResources
 */
@Component
@Path("/{imsi}/mGraphics")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class MGraphicResources extends OperationResources {
    public static final Logger LOGGER = LoggerFactory.getLogger(MGraphicResources.class);

    @Autowired
    private MGraphicService mgraphicService;

    @Autowired
    @Qualifier("mgraphicService")
    private QueryMGraphicService queryMGraphicService;

    @Autowired
    private ActionBuilder actionBuilder;

    @Autowired
    private UserSubscribeGraphicItemService userSubscribeGraphicItemService;

    @POST
    public Response create(@PathParam("imsi") String imsi,
                           @DefaultValue("false") @QueryParam("subscribe") Boolean subscribe, MGraphicDTO mGraphicDTO) {

        operationDescription = new OperationDescription();
        try {
            OperationResult operationResult;
            operationResult = mgraphicService.create(imsi, false, mGraphicDTO, subscribe);
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
                actionBuilder.buildSubscribeGraphicItemAction(imsi));
            return Response.ok(operationDescription).build();

        } catch (Exception ex) {
            errorMessage(ex);
            actionName = "createCommonMGraphic";
            operationDescription.setActionName(actionName);
            operationDescription.setErrorCode(403);
            return Response.ok(operationDescription).build();
        }
        return Response.ok(operationDescription).status(Response.Status.ACCEPTED).build();
    }

    @PUT
    @Path("/{id}")
    public Response edit(@PathParam("imsi") String imsi, @PathParam("id") String userCommonMGraphicId,
                         MGraphicDTO mGraphicDTO) {

        operationDescription = new OperationDescription();
        try {
            mGraphicDTO.setId(userCommonMGraphicId);
            OperationResult operationResult = mgraphicService.edit(imsi, mGraphicDTO);
            updateOperationDescription(operationResult);
        } catch (NotSubscribeTypeException e) {
            LOGGER.error("create NotSubscribeTypeException error", e);
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                Response.Status.NOT_ACCEPTABLE.getStatusCode(), "create", "用户未订购");
            return Response.ok(operationDescription).build();

        } catch (Exception e) {
            errorMessage(e);
            actionName = "editCommonMGraphic";
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
            OperationResult operationResult = mgraphicService.disable(imsi, userCommonMGraphicId);
            updateOperationDescription(operationResult);
        } catch (Exception e) {
            errorMessage(e);
            actionName = "disableCommonMGraphic";
            operationDescription.setActionName(actionName);
            operationDescription.setErrorCode(403);
            return Response.ok(operationDescription).build();
        }
        return Response.ok(operationDescription).build();
    }

    @GET
    public Response getAll(@PathParam("imsi") String imsi) {
        DataPage dataPage = queryMGraphicService.queryUserMGraphic(imsi);
        return Response.ok(dataPage).build();
    }

    //test method, it just provide convient method to cancel subscribe graphic item
    @DELETE
    @Path("{graphicInfoId}")
    public Response cancelSubscribeGraphicItem(@PathParam("imsi") String imsi,
                                               @PathParam("graphicInfoId") String graphicInfoId) {
        try {
            userSubscribeGraphicItemService.deleteSubscribeItem(imsi, graphicInfoId);
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                HttpServletResponse.SC_NO_CONTENT, "cancelSubscribeGraphicItem");
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(500,
                "cancelSubscribeGraphicItem", "取消订购失败");
            return Response.ok(operationDescription).build();
        }

    }
}
