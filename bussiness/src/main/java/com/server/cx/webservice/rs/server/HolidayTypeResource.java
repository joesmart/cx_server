package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.DataPage;
import com.server.cx.service.cx.HolidayTypeService;
import com.server.cx.util.business.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("/{imsi}/holidayTypes")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class HolidayTypeResource {
    public static final Logger LOGGER = LoggerFactory.getLogger(HolidayTypeResource.class);

    @Autowired
    private HolidayTypeService holidayTypeService;

    @GET
    public DataPage queryAllHolidayTypes(@PathParam("imsi")String imsi){
        LOGGER.info("imsi:"+imsi);
        
        ValidationUtil.checkParametersNotNull(imsi);
        DataPage dataPage = holidayTypeService.queryAllHolidayTypes(imsi);
        return  dataPage;
    }
}
