package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.ContactInfoDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.RegisterDTO;
import com.google.common.base.Preconditions;
import com.server.cx.exception.InvalidParameterException;
import com.server.cx.service.cx.RegisterService;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.StringUtil;
import com.server.cx.util.business.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * User资源的REST服务.
 * 
 * @author calvin
 */
@Component
@Path("/register")
public class RegisterResources {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionInfoResources.class);
    
    @Autowired
    private RegisterService registerService;

    @POST
    public Response register(RegisterDTO registerDTO) {

        LOGGER.info("Into register registerDTO = " + registerDTO);
        try {
            ValidationUtil.checkParametersNotNull(registerDTO, registerDTO.getImsi());
            Preconditions.checkNotNull(registerDTO.getImsi());
            OperationDescription operationDescription = registerService.register(registerDTO, null);
            return Response.ok(operationDescription).build();
        } catch (InvalidParameterException e) {
            OperationDescription operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_NOT_ACCEPTABLE, "register", "IMSI数据为空");
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
