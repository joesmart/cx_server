package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.service.cx.CategoryService;
import com.server.cx.service.cx.GraphicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * User: yanjianzou
 * Date: 12-7-31
 * Time: 上午10:46
 * FileName:CategoryResources
 */
@Component
@Path("{imsi}/categories")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class CategoryResources extends OperationResources {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GraphicInfoService graphicInfoService;

    @GET
    public DataPage categoriesList(@PathParam("imsi") String imsi) {
        operationDescription = new OperationDescription();
        try {
            return categoryService.queryAllCategoryData(imsi);
        } catch (Exception e) {
            errorMessage(e);
            actionName = "calling";
            operationDescription.setActionName(actionName);
            operationDescription.setErrorCode(403);
            return null;
        }
    }

    @GET
    @Path("/{id}")
    public DataPage categoriesItems(@PathParam("imsi") String imsi,
                                    @PathParam("id") Long categoryId
    ) {
        return null;
    }
}
