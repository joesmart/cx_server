package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.VersionInfoDTO;
import com.server.cx.service.cx.VersionInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/upgrade")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class VersionInfoResources {
    public static final Logger LOGGER = LoggerFactory.getLogger(VersionInfoResources.class);

    private VersionInfoService versionInfoService;

    public VersionInfoService getVersionInfoService() {
        return versionInfoService;
    }

    @Autowired
    public void setVersionInfoService(VersionInfoService versionInfoService) {
        this.versionInfoService = versionInfoService;
    }

    @GET
    public Response checkIsTheLatestVersion(@DefaultValue("0") @QueryParam("version") String version) {
        LOGGER.info("version = " + version);
        VersionInfoDTO versionInfoDTO = versionInfoService.checkIsTheLatestVersion(version);
        return Response.ok(versionInfoDTO).build();
    }
}
