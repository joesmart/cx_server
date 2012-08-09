package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.ContactsDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.constants.Constants;
import com.server.cx.service.cx.ContactsServcie;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/{imsi}/contacts")
//@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ContactsResources {
    private final static Logger LOGGER = LoggerFactory.getLogger(ContactsResources.class);

    @Autowired
    private ContactsServcie contactsServcie;

    @SuppressWarnings("finally")
    @POST
    public Response uploadContacts(@PathParam("imsi") String imsi, ContactsDTO uploadContactDTO) {
        LOGGER.info("uploadContactDTO = " + uploadContactDTO);

        OperationDescription operationDescription = new OperationDescription();
        try {
            ValidationUtil.checkParametersNotNull(uploadContactDTO);
            contactsServcie.uploadContacts(uploadContactDTO.getContactInfos(), imsi);
            operationDescription = ObjectFactory.buildOperationDescription(HttpServletResponse.SC_CREATED,
                "uploadContacts", Constants.SUCCESS_FLAG);
        } catch (Exception e) {
            operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "uploadContacts", e.getMessage());
        } finally {
            return Response.ok(operationDescription).build();
        }
    }
}
