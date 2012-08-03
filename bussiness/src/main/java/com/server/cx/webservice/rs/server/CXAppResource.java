package com.server.cx.webservice.rs.server;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cl.cx.platform.dto.DataPage;
import com.server.cx.entity.cx.Contacts;
import com.server.cx.service.cx.CXAppService;
import com.server.cx.util.ObjectFactory;

@Component
@Path("/{imsi}/cxapp")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class CXAppResource {
    public static final Logger LOGGER = LoggerFactory.getLogger(CXAppResource.class);
    
    @Autowired
    private CXAppService cxAppService;

    @GET
    public DataPage getCXAppUsersByImsi(@PathParam("imsi") String imsi) {
        LOGGER.info("imsi:" + imsi);
        List<Contacts> contacts = cxAppService.queryCXAppUserByImsi(imsi);
        DataPage dataPage = ObjectFactory.buildDataPageByContacts(contacts);
        return dataPage;
    }
}
