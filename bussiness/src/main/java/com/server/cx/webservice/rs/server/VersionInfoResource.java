package com.server.cx.webservice.rs.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cl.cx.platform.dto.VersionInfoDTO;
import com.server.cx.service.cx.VersionInfoService;

@Component
@Path("/upgrade")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class VersionInfoResource {
    public static final Logger LOGGER = LoggerFactory.getLogger(VersionInfoResource.class);

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
