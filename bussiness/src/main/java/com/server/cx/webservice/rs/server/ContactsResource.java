package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.constants.Constants;
import com.server.cx.dto.Result;
import com.cl.cx.platform.dto.UploadContactDTO;
import com.server.cx.service.cx.ContactsServcie;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/{imsi}/contacts")
//@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ContactsResource {
    private final static Logger LOGGER = LoggerFactory.getLogger(ContactsResource.class);

    @Autowired
    private ContactsServcie contactsServcie;

    @SuppressWarnings("finally")
    @POST
    public Response uploadContacts(@PathParam("imsi") String imsi, UploadContactDTO uploadContactDTO) {
        LOGGER.info("uploadContactDTO = " + uploadContactDTO);

        OperationDescription operationDescription = new OperationDescription();
        try {
            ValidationUtil.checkParametersNotNull(uploadContactDTO);
            contactsServcie.uploadContacts(uploadContactDTO.getContactPeopleInfos(), imsi);
            operationDescription = ObjectFactory.buildOperationDescription(HttpServletResponse.SC_CREATED,
                "uploadContacts", Constants.SUCCESS_FLAG);
        } catch (Exception e) {
            operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "uploadContacts", e.getMessage());
        } finally {
            return Response.ok(operationDescription).build();
        }
    }

    @GET
    @Path("query")
    public Response getCatactUserCXInfos(@QueryParam("imsi") String imsi) {
        Result xmlResult = new Result();
        try {
            ValidationUtil.checkParametersNotNull(imsi);
            String xml = contactsServcie.retrieveContactUserCXInfo(imsi);
            return Response.ok(xml).build();
        } catch (Exception e) {
            xmlResult.setFlag(Constants.ERROR_FLAG);
            xmlResult.setContent(e.getMessage());
            return Response.ok(xmlResult).build();
        }
    }

    @POST
    @Path("copy")
    public Response copy(@QueryParam("imsi") String imsi, @QueryParam("cxInfoId") String cxInfoId) {
        Result xmlResult = new Result();
        try {
            ValidationUtil.checkParametersNotNull(imsi);
            String xml = contactsServcie.copyContactUserCXInfo(imsi, cxInfoId);
            return Response.ok(xml).build();
        } catch (Exception e) {
            xmlResult.setFlag(Constants.ERROR_FLAG);
            xmlResult.setContent(e.getMessage());
            return Response.ok(xmlResult).build();
        }
    }
}
