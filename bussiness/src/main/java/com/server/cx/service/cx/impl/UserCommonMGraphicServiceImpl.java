package com.server.cx.service.cx.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.UserCommonMGraphicDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.FileMeta;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.UserCommonMGraphic;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.service.cx.UserCommonMGraphicService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.business.JerseyResourceUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

@Component
@Transactional
public class UserCommonMGraphicServiceImpl implements UserCommonMGraphicService {
    @Autowired
    private BusinessFunctions businessFunctions;
    
    @Autowired
    private UserInfoDao userInfoDao;
    
    @Autowired
    private UserCommonMGraphicDao userCommonMGraphicDao;
    
    @Autowired
    @Qualifier("fileUploadUrl")
    private String fileUploadUrl;

    @Transactional(readOnly=false)
    @Override
    public void addFileStreamToResourceServer(String imsi, InputStream fileStream) throws IOException {
        FileMeta fileMeta = uploadToResourceServer(fileStream);
        GraphicInfo graphicInfo = businessFunctions.fileMetaTransformToGraphicInfo().apply(fileMeta);
        UserInfo userInfo = userInfoDao.findByImsi(imsi);
        graphicInfo.setOwner(userInfo.getId());
        
        UserCommonMGraphic userCommonMGraphic = new UserCommonMGraphic();
        userCommonMGraphic.setUserInfo(userInfo);
        userCommonMGraphic.setGraphicInfo(graphicInfo);
        userCommonMGraphic.setCommon(true); //没有设置使用对象
        userCommonMGraphicDao.save(userCommonMGraphic);
    }
    
    private FileMeta uploadToResourceServer(InputStream fileStream) throws IOException {
        return uploadToResourceServer("", fileStream);
    }

    private FileMeta uploadToResourceServer(String name, InputStream stream) throws IOException {
        FormDataMultiPart mp = new FormDataMultiPart();
        FormDataBodyPart p = new FormDataBodyPart(FormDataContentDisposition.name("part").build(), "CONTENT");
        FormDataBodyPart inputStreamBody = new FormDataBodyPart(FormDataContentDisposition.name("file")
                .fileName(name).build(), stream, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        mp.bodyPart(p);
        mp.bodyPart(inputStreamBody);
        JerseyResourceUtil.getClient().addFilter(
                new HTTPBasicAuthFilter(Constants.RESOURCE_SERVER_USERNAME, Constants.RESOURCE_SERVER_PASSWORD));
        WebResource webResource = JerseyResourceUtil.getClient().resource(fileUploadUrl);
        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, mp);
        GenericType<List<FileMeta>> genericType = new GenericType<List<FileMeta>>() {};
        List<FileMeta> imageResourceList = response.getEntity(genericType);
        if(imageResourceList != null && !imageResourceList.isEmpty()) {
            return imageResourceList.get(0);
        }
        return null;
    }

}
