package com.server.cx.webservice.rs.server;

import com.server.cx.constants.Constants;
import com.server.cx.dto.Result;
import com.server.cx.service.cx.ContactsServcie;
import com.server.cx.util.business.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/contacts")
public class ContactsResource {

  @Autowired
  ContactsServcie contactsServcie;

  @POST
  @Produces({MediaType.APPLICATION_XML})
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response uploadContacts(Result result) {
    Result xmlResult = new Result();
    try {
      ValidationUtil.checkParametersNotNull(result);
      contactsServcie.uploadContacts(result.getContactPeopleInfos(), result.getImsi());
      xmlResult.setFlag(Constants.SUCCESS_FLAG);
      xmlResult.setContent("操作成功");
    } catch (Exception e) {
      xmlResult.setFlag(Constants.ERROR_FLAG);
      xmlResult.setContent(e.getMessage());
    } finally {
      return Response.ok(xmlResult).build();
    }
  }

  @GET
  @Path("query")
  @Produces({MediaType.APPLICATION_XML})
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
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
  @Produces({MediaType.APPLICATION_XML})
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
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
