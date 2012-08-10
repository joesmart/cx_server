package com.server.cx.webservice.rs.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cl.cx.platform.dto.DataPage;
import com.server.cx.service.cx.GraphicInfoService;
import com.server.cx.service.cx.StatusTypeService;
import com.server.cx.util.business.ValidationUtil;

@Component
@Path("/{imsi}/statusTypes")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class StatusTypeResources {
    public static final Logger LOGGER = LoggerFactory.getLogger(StatusTypeResources.class);

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

    @GET
    @Path("{id}")
    public DataPage queryStatusTypeById(@PathParam("imsi") String imsi, @PathParam("id") Long statusTypeId,
                                        @DefaultValue("0") @QueryParam("offset") Integer offset,
                                        @DefaultValue("5") @QueryParam("limit") Integer limit) {
        LOGGER.info("Into queryStatusTypeById");
        LOGGER.info("imsi = " + imsi);
        LOGGER.info("statusTypeId = " + statusTypeId);

        ValidationUtil.checkParametersNotNull(imsi);
        ValidationUtil.checkParametersNotNull(statusTypeId);
        DataPage dataPage = graphicInfoService.findStatusGraphicInfosByImsi(imsi, statusTypeId, offset, limit);
        return dataPage;
    }
}
