package com.server.cx.webservice.rs.server;

import com.server.cx.dto.Result;
import com.server.cx.service.cx.VersionInfoService;
import com.server.cx.util.business.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/upgrade")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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

	@POST
	public Response checkIsTheLatestVersion(Result result) {
	    LOGGER.info("imsi = " + result.getImsi());
	    LOGGER.info("version = " + result.getVersion());
	    ValidationUtil.checkParametersNotNull(result, result.getImsi());
		Result response = versionInfoService.checkIsTheLatestVersion(result.getImsi(), result.getVersion());
		return Response.ok(response).build();
	}
}
