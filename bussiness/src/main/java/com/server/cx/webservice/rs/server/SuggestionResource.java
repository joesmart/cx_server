package com.server.cx.webservice.rs.server;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.constants.Constants;
import com.server.cx.entity.cx.Suggestion;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.SuggestionService;
import com.server.cx.util.ObjectFactory;

@Component
@Path("/suggestion")
@Produces({MediaType.APPLICATION_JSON})
public class SuggestionResource {
    private final static Logger LOGGER = LoggerFactory.getLogger(SuggestionResource.class);
    @Autowired
    private SuggestionService suggestionService;

    @POST
    @Path("{imsi}")
    public Response addSuggestion(@PathParam("imsi") String imsi, String content) {
        LOGGER.info("Into addSuggestion content = " + content);
        OperationDescription operationDescription = new OperationDescription();
        try {
            suggestionService.addSuggestion(imsi, content);
            operationDescription = ObjectFactory.buildOperationDescription(HttpServletResponse.SC_CREATED,
                "addSuggestion", Constants.SUCCESS_FLAG);
        } catch (SystemException e) {
            operationDescription = ObjectFactory.buildErrorOperationDescription(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "addSuggestion", Constants.ERROR_FLAG);
            e.printStackTrace();
        }
        return Response.ok(operationDescription).build();
    }

    @GET
    public Response getAllSuggestion() {
        LOGGER.info("Into getAllSuggestion");
        List<Suggestion> suggestions = suggestionService.getAllSuggestion();
        if (suggestions == null || suggestions.isEmpty()) {
            return Response.noContent().build();
        }
        hideSuggestionUserInfo(suggestions);
        return Response.ok(suggestions).build();
    }

    private void hideSuggestionUserInfo(List<Suggestion> suggestions) {
        for(Suggestion suggestion : suggestions) {
            suggestion.setUserInfo(null);
        }
    }
}
