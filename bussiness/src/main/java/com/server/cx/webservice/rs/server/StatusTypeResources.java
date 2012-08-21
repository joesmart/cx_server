package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.DataPage;
import com.server.cx.service.cx.GraphicInfoService;
import com.server.cx.service.cx.StatusTypeService;
import com.server.cx.util.business.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("/{imsi}/statusTypes")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class StatusTypeResources {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusTypeResources.class);

    @Autowired
    private StatusTypeService statusTypeService;

    @Autowired
    private GraphicInfoService graphicInfoService;

    @GET
    public DataPage queryAllStatusTypes(@PathParam("imsi") String imsi) {
        LOGGER.info("imsi:" + imsi);

        ValidationUtil.checkParametersNotNull(imsi);
        DataPage dataPage = statusTypeService.queryAllStatusTypes(imsi);
        return dataPage;
    }
}
