package com.server.cx.webservice.rs.server;

import com.cl.cx.platform.dto.DataPage;
import com.server.cx.service.cx.CategoryService;
import com.server.cx.service.cx.GraphicInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Path("/{imsi}/categories")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class CategoryResources {
    public static final Logger LOGGER = LoggerFactory.getLogger(CategoryResources.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GraphicInfoService graphicInfoService;
    @GET
    public DataPage categoriesList(@PathParam("imsi")String imsi){
        LOGGER.info("imsi:"+imsi);
        DataPage dataPage = categoryService.queryAllCategoryData(imsi);
        return  dataPage;
    }

    @GET
    @Path("/{id}")
    public DataPage categoriesItems(@PathParam("imsi")String imsi,
                                    @PathParam("id") Long categoryId
                                    ){
      return  null;
    }
}
