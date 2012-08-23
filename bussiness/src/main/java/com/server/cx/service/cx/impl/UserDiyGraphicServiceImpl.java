package com.server.cx.service.cx.impl;

import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.UserDiyGraphicDao;
import com.server.cx.entity.cx.FileMeta;
import com.server.cx.entity.cx.GraphicResource;
import com.server.cx.entity.cx.UserDiyGraphic;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.service.cx.UserDiyGraphicService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.business.JerseyResourceUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class UserDiyGraphicServiceImpl extends CheckAndHistoryMGraphicService implements UserDiyGraphicService {
    @Autowired
    private BusinessFunctions businessFunctions;

    @Autowired
    private UserDiyGraphicDao userDiyGraphicDao;
    @Autowired
    @Qualifier("fileUploadUrl")
    private String fileUploadUrl;

    @Transactional(readOnly=false)
    @Override
    public void addFileStreamToResourceServer(String imsi, InputStream fileStream) throws IOException {
        checkAndSetUserInfoExists(imsi);
        FileMeta fileMeta = uploadToResourceServer(imsi, fileStream);
        UserDiyGraphic userDiyGraphic = userDiyGraphicDao.findByUserInfo(userInfo);
        if(userDiyGraphic == null){
            userDiyGraphic = new UserDiyGraphic();
            userDiyGraphic.setName("自定义");
            userDiyGraphic.setSignature("自定义");
            userDiyGraphic.setUserInfo(userInfo);
            userDiyGraphic = userDiyGraphicDao.save(userDiyGraphic);
        }
        GraphicResource graphicResource = businessFunctions.fileMetaTransformToGraphicInfo(userDiyGraphic).apply(fileMeta);
        List<GraphicResource> graphicResources = userDiyGraphic.getGraphicResources();
        if(graphicResources == null){
            graphicResources = Lists.newArrayList();
        }
        graphicResources.add(graphicResource);
        userDiyGraphic.setGraphicResources(graphicResources);
        userDiyGraphicDao.save(userDiyGraphic);
    }


    private FileMeta uploadToResourceServer(String imsi, InputStream stream) throws IOException {
        FormDataMultiPart mp = new FormDataMultiPart();
        byte[] arrays = ByteStreams.toByteArray(stream);
        long fileSize = arrays.length;
        if(fileSize <=1){
            throw new CXServerBusinessException("上传到数据流为空!");
        }
        String fileName = imsi+ new Date().getTime();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(arrays);
        FormDataBodyPart inputStreamBody = new FormDataBodyPart(FormDataContentDisposition.name("file")
                .fileName(fileName).size(fileSize).build(),inputStream, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        mp.bodyPart(inputStreamBody);
        JerseyResourceUtil.getClient().addFilter(
                new HTTPBasicAuthFilter(Constants.RESOURCE_SERVER_USERNAME, Constants.RESOURCE_SERVER_PASSWORD));
        WebResource webResource = JerseyResourceUtil.getClient().resource(fileUploadUrl);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, mp);
        if(response.getStatus()!= Response.Status.OK.getStatusCode()){
            throw new CXServerBusinessException("图片保存失败!");
        }
        GenericType<List<FileMeta>> genericType = new GenericType<List<FileMeta>>() {};
        List<FileMeta> imageResourceList = response.getEntity(genericType);
        if(imageResourceList != null && !imageResourceList.isEmpty()) {
            return imageResourceList.get(0);
        }
        return null;
    }

}
