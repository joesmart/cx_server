package com.server.cx.resources;

import com.server.cx.dao.cx.GenericDaoHibernate;
import com.server.cx.entity.cx.CXInfo;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.CXInfoManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;
import java.util.List;

@Component
@Path("/cxinfos")
public class CXInfoResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Context
    ServletContext servletContext;
    @Autowired
    GenericDaoHibernate<CXInfo,Long> genericCXInfoDao;
    @Autowired
    CXInfoManagerService cxInfoManagerService;
    
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<CXInfo> getAllCXInfo(){ 
        List<CXInfo> cxInfos = genericCXInfoDao.getAll();
        return cxInfos;
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public Response addNewCXInfo(JAXBElement<CXInfo> jaxbElement){
        Response response =null;
        String serverPath = servletContext.getRealPath("/");
        System.out.println(serverPath);
        CXInfo cxinfo = jaxbElement.getValue();
        try {
            cxInfoManagerService.addNewCXInfo(serverPath, cxinfo);
        } catch (SystemException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Response.status(404);
            return Response.serverError().build();
        }
        response = Response.created(uriInfo.getAbsolutePath()).build();
        return response;
    }
}
