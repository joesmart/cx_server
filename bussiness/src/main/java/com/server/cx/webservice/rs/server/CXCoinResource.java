package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.CXCoinAccountDTO;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.entity.cx.CXCoinAccount;
import com.server.cx.model.ActionBuilder;
import com.server.cx.service.cx.CXCoinService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/{imsi}/cxCoin")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class CXCoinResource {

    @Autowired
    private CXCoinService cxCoinService;

    @Autowired
    private BusinessFunctions businessFunctions;
    
    @Autowired
    private ActionBuilder actionBuilder;
    
    @Path("account")
    @GET
    public Response getCXCoinAccount(@PathParam("imsi") String imsi) {

        try {
            ValidationUtil.checkParametersNotNull(imsi);
            CXCoinAccount cxCoinAccount = cxCoinService.getCXCoinAccount(imsi);
            CXCoinAccountDTO cxCoinAccountDTO = businessFunctions.cxCoinAccountToCXCoinAccountDTO().apply(cxCoinAccount);
            return Response.ok(cxCoinAccountDTO).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "getCXCoinAccount", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    @Path("register")
    @POST
    public Response register(@PathParam("imsi") String imsi, CXCoinAccountDTO coinAccountDTO) {

        try {
            ValidationUtil.checkParametersNotNull(imsi, coinAccountDTO.getName(), coinAccountDTO.getPassword());
            OperationDescription operationDescription = cxCoinService.register(imsi, coinAccountDTO);
            operationDescription.setActions(actionBuilder.buildCXCoinRegisteredURL(imsi));
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "register", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    @Path("login")
    @POST
    public Response login(@PathParam("imsi") String imsi, CXCoinAccountDTO coinAccountDTO) {

        try {
            ValidationUtil.checkParametersNotNull(imsi, coinAccountDTO.getName(), coinAccountDTO.getPassword());
            OperationDescription operationDescription = cxCoinService.login(imsi, coinAccountDTO);
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "register", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    @Path("records")
    @GET
    public Response getUserCXCoinRecords(@PathParam("imsi") String imsi,
                                         @DefaultValue("0") @QueryParam("offset") Integer offset,
                                         @DefaultValue("20") @QueryParam("limit") Integer limit) {

        try {
            DataPage dataPage = cxCoinService.getUserCXCoinRecords(imsi, offset, limit);
            return Response.ok(dataPage).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "getUserSubscribeRecords", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    @Path("consume")
    @PUT
    public Response consumeCXCoin(@PathParam("imsi") String imsi, CXCoinAccountDTO coinAccountDTO) {
        try {
            ValidationUtil.checkParametersNotNull(imsi, coinAccountDTO.getName(), coinAccountDTO.getPassword());
            OperationDescription operationDescription = cxCoinService.consumeCXCoin(imsi, coinAccountDTO);
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "register", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

}
