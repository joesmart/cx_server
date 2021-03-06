package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.ContactInfoDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.RegisterDTO;
import com.cl.cx.platform.dto.RegisterOperationDescription;
import com.google.common.base.Preconditions;
import com.server.cx.exception.InvalidParameterException;
import com.server.cx.service.cx.RegisterService;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.StringUtil;
import com.server.cx.util.business.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User资源的REST服务.
 *
 * @author calvin
 */
@Component
@Path("register")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class RegisterResources extends OperationResources {

    @Autowired
    private RegisterService registerService;

    @POST
    public Response register(RegisterDTO registerDTO) {
        try {
            ValidationUtil.checkParametersNotNull(registerDTO, registerDTO.getImsi());
            Preconditions.checkNotNull(registerDTO.getImsi());
            RegisterOperationDescription registerOperationDescription = registerService.register(registerDTO, null);
            return Response.ok(registerOperationDescription).build();
        } catch (InvalidParameterException e) {
            RegisterOperationDescription registerOperationDescription = ObjectFactory
                    .buildErrorRegisterOperationDescription(HttpServletResponse.SC_NOT_ACCEPTABLE, "register", "IMSI数据为空");
            return Response.ok(registerOperationDescription).build();
        }
    }

    @PUT
    @Path("/{imsi}")
    public Response update(@PathParam("imsi") String imsi, RegisterDTO registerDTO) {
        operationDescription = new OperationDescription();
        try {
            ValidationUtil.checkParametersNotNull(registerDTO, registerDTO.getPhoneNo());
            registerDTO.setImsi(imsi);
            OperationDescription operationDescription = registerService.update(registerDTO);
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            errorMessage(e);
            actionName = "update";
            operationDescription.setActionName(actionName);
            operationDescription.setErrorCode(403);
            return Response.ok(operationDescription).build();
        }
    }

    @PUT
    @Path("{imsi}/sms")
    public Response updateSMSFlag(@PathParam("imsi") String imsi, RegisterDTO registerDTO) {

        try {
            ValidationUtil.checkParametersNotNull(imsi, registerDTO);
            registerDTO.setImsi(imsi);
            OperationDescription operationDescription = registerService.updateSMSFlag(registerDTO);
            return Response.ok(operationDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "updateSMSFlag", e.getMessage());
            return Response.ok(operationDescription).build();
        }
    }

    private String getPhoneNoFromContactInfo(ContactInfoDTO contactInfoDTO) {
        String phoneNo = null;
        if (contactInfoDTO != null && StringUtil.notNull(contactInfoDTO.getPhoneNo())) {
            phoneNo = contactInfoDTO.getPhoneNo();
        }
        return phoneNo;
    }
}
