package com.server.cx.webservice.rs.server;

import com.server.cx.dto.GraphicCheckDTO;
import com.server.cx.service.cx.GraphicResourceService;
import com.server.cx.util.business.AuditStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User: ZouYanjian
 * Date: 8/30/12
 * Time: 2:46 PM
 * FileName:GraphicResources
 */
@Component
@Path("graphicResources")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class GraphicResources {
    @Autowired
    private GraphicResourceService graphicResourceService;

    public static final Logger LOGGER = LoggerFactory.getLogger(GraphicResources.class);
    @POST
    public void auditGraphicResource(GraphicCheckDTO graphicCheckDTO){
        LOGGER.info(graphicCheckDTO.toString());
        AuditStatus auditStatus = AuditStatus.CHECKING;
        String result =  graphicCheckDTO.getCheckResult();
        if("PASS".equalsIgnoreCase(result)){
            auditStatus = AuditStatus.PASSED;
        }
        if("UNPASS".equalsIgnoreCase(result)){
            auditStatus = AuditStatus.UNPASS;
        }
        graphicResourceService.updateGraphicResourcesAuditStatus(graphicCheckDTO.getGraphicIds(),auditStatus);
    }
}
