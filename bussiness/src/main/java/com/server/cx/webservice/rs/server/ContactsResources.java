package com.server.cx.webservice.rs.server;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cl.cx.platform.dto.ContactInfoDTO;
import com.cl.cx.platform.dto.ContactsDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.google.common.collect.Lists;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.service.cx.ContactsService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.ObjectFactory;
import com.server.cx.util.business.ValidationUtil;

@Component
@Path("{imsi}/contacts")
//@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ContactsResources extends OperationResources {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactsResources.class);

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private BusinessFunctions businessFunctions;

    @SuppressWarnings("finally")
    @POST
    public Response uploadContacts(@PathParam("imsi") String imsi, ContactsDTO uploadContactDTO) {
        LOGGER.info("Into uploadContacts imsi = " + imsi);
        LOGGER.info("uploadContactDTO = " + uploadContactDTO);
        OperationDescription operationDescription = new OperationDescription();
        try {
            ValidationUtil.checkParametersNotNull(uploadContactDTO);
            contactsService.uploadContacts(uploadContactDTO.getContactInfos(), imsi);
            operationDescription = ObjectFactory.buildOperationDescription(HttpServletResponse.SC_CREATED,
                "uploadContacts");
        } catch (Exception e) {
            operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "uploadContacts", e.getMessage());
            return Response.ok(operationDescription).build();
        }
        return Response.ok(operationDescription).build();
    }

    @GET
    public ContactsDTO getContactsByImsi(@PathParam("imsi") String imsi) {
        LOGGER.info("Into uploadContacts imsi = " + imsi);
        operationDescription = new OperationDescription();
        try {
            List<Contacts> contacts = contactsService.queryCXAppContactsByImsi(imsi);
            ContactsDTO contactDTO = new ContactsDTO();
            List<ContactInfoDTO> contactInfoDTOList = Lists.transform(contacts,
                businessFunctions.contactsTransformToContactInfoDTO());
            contactDTO.setContactInfos(contactInfoDTOList);
            return contactDTO;
        } catch (Exception e) {
            errorMessage(e);
            actionName = "calling";
            operationDescription.setActionName(actionName);
            operationDescription.setErrorCode(403);
            return null;
        }
    }
}
